package com.bjpowernode.money.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.money.model.FinanceAccount;
import com.bjpowernode.money.model.User;
import com.bjpowernode.money.service.FinanceAccountService;
import com.bjpowernode.money.service.UserService;
import com.bjpowernode.money.vo.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author tzsang
 * @create 2022-06-11 21:00
 */
@RequestMapping("/loan")
@Controller
@Slf4j
public class UserController {

    @Reference(interfaceClass = UserService.class, version = "1.0.0", check = false)
    private UserService userService;

    @Reference(interfaceClass = FinanceAccountService.class, version = "1.0.0", check = false)
    private FinanceAccountService financeAccountService;

    @GetMapping("/page/login")
    public String toLogin() {

        return "login";
    }

    @PostMapping("/page/login")
    @ResponseBody
    public ApiResponse login(String captcha, String phone, String loginPassword, HttpSession session) {

        String code = (String) session.getAttribute("loginCode");
        if (!code.equalsIgnoreCase(captcha)) {
            return ApiResponse.error("验证码错误！");
        }

        User user = userService.queryByPhoneAndPassword(phone, loginPassword);
        if (user == null) {
            return ApiResponse.error("账号或密码错误!");
        }

        FinanceAccount finance = financeAccountService.queryByUserId(user.getId());
        log.info(finance.toString());

        session.setAttribute("loginUser", user);
        session.setAttribute("finance", finance);
        return ApiResponse.success("登录成功!");
    }

    //退出登录
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/index";
    }

    //图形验证码
    @GetMapping("login-captcha")
    public void loginCaptcha(HttpServletResponse resp, HttpSession session) throws IOException {
        //创建图片
        BufferedImage image = new BufferedImage(78, 36, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.createGraphics();

        graphics.setColor(Color.white);
        graphics.fillRect(1, 1, 76, 34);

        //绘制随机文字
        String code = RandomStringUtils.randomAlphabetic(4);
        graphics.setColor(Color.pink);
        Font font = new Font("Arial", Font.ITALIC, 24);
        graphics.setFont(font);

        graphics.drawString(code, 5, 30);

        session.setAttribute("loginCode", code);
        //输出图片
        ImageIO.write(image, "jpg", resp.getOutputStream());
    }

    //跳到注册页面
    @GetMapping("/page/register")
    public String toRegister() {

        return "register";
    }

    //检查手机号码是否重复
    @PostMapping("/checkPhone")
    @ResponseBody
    public ApiResponse checkPhone(String phone) {
        if(!userService.checkPhone(phone)){
            return ApiResponse.error("该号码已存在!");
        }
        return ApiResponse.success("可以注册");
    }

    //验证码是否发送成功
    @PostMapping("/code")
    @ResponseBody
    public ApiResponse getCode(String phone){
        boolean ok = userService.sendSmsCode(phone);

        if (ok){
            return ApiResponse.success("验证码发送成功");
        }else {
            return ApiResponse.error("短信平台异常");
        }
    }

    //注册
    @PostMapping("/register")
    @ResponseBody
    public ApiResponse register(String phone, String password, String code, HttpSession session){
        if(!userService.checkCode(code, phone)){
            return ApiResponse.error("验证码错误或已过期");
        }

        User user = userService.register(phone, password);
        FinanceAccount finance = financeAccountService.queryByUserId(user.getId());
        //完成登录
        session.setAttribute("loginUser", user);
        session.setAttribute("finance", finance);

        return ApiResponse.success("注册成功");
    }

    //跳转到实名认证
    @GetMapping("/page/realName")
    public String toRealName(){
        return "realName";
    }

    //实名认证
    @ResponseBody
    @PostMapping("/verifyRealName")
    public ApiResponse verifyRealName(HttpSession session, String code, String realName, String idCard){

        //检查图形验证码
        String loginCode = (String) session.getAttribute("loginCode");
        System.out.println("loginCode=" + loginCode);
        if (!loginCode.equalsIgnoreCase(code)){
            return ApiResponse.error("图形验证码错误!");
        }

        //检查姓名和身份证
        if(!userService.checkRealName(realName, idCard)){
            return ApiResponse.error("身份证和姓名不匹配!");
        }

        //匹配,更新用户信息
        User user = (User) session.getAttribute("loginUser");
        user.setIdCard(idCard);
        user.setName(realName);
        userService.edit(user);

        session.setAttribute("loginUser", user);
        return ApiResponse.success("身份匹配");
    }

    //进入小金库
    @GetMapping("/myCenter")
    public String toMyCenter(Integer id, Model model){

        User user = userService.queryById(id);
        model.addAttribute("user", user);
        return "myCenter";
    }
}
