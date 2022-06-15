package com.bjpowernode.money.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.money.common.Constants;
import com.bjpowernode.money.model.FinanceAccount;
import com.bjpowernode.money.model.RechargeRecord;
import com.bjpowernode.money.model.User;
import com.bjpowernode.money.service.*;
import com.bjpowernode.money.vo.ApiResponse;
import com.bjpowernode.money.vo.MyBidInfo;
import com.bjpowernode.money.vo.MyIncomeRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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

    @Reference(interfaceClass = BidInfoService.class, version = "1.0.0", check = false)
    private BidInfoService bidInfoService;

    @Reference(interfaceClass = LoanInfoService.class, version = "1.0.0", check= false)
    private LoanInfoService loanInfoService;

    @Reference(interfaceClass = RechargeRecordService.class, version="1.0.0", check = false)
    private RechargeRecordService rechargeRecordService;

    @Reference(interfaceClass = IncomeRecordService.class, version = "1.0.0", check = false)
    private IncomeRecordService incomeRecordService;

    //跳到登录页面
    @GetMapping("/page/login")
    public String toLogin(Model model) {
        long count = userService.countAll();
        long sumBid = bidInfoService.sumBid();
        double rate = loanInfoService.queryHistoryAverageRate();

        model.addAttribute("count", count);
        model.addAttribute("sumBid", sumBid);
        model.addAttribute(Constants.HISTORY_AVERAGE_RATE, rate);
        return "login";
    }

    //登录
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

        user.setLastLoginTime(new Date(System.currentTimeMillis() + 60 * 60 * 8));
        userService.edit(user);

        session.setAttribute("loginUser", user);
        session.setAttribute("account", finance);
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
        session.setAttribute("account", finance);

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
        Integer pageNum = 1;
        Integer pageSize = 5;
        User user = userService.queryById(id);
        FinanceAccount account = financeAccountService.queryByUserId(id);
        List<MyIncomeRecord> myIncomeRecords = incomeRecordService.queryDescByUid(id, pageNum, pageSize);

        List<RechargeRecord> rechargeRecords = rechargeRecordService.queryById(id, pageNum, pageSize);
        List<MyBidInfo> myBidInfos = bidInfoService.queryDescByUid(id, pageNum, pageSize);


        model.addAttribute("user", user);
        model.addAttribute("account", account);
        model.addAttribute("myIncomeRecords", myIncomeRecords);
        model.addAttribute("rechargeRecords", rechargeRecords);
        model.addAttribute("myBidInfos", myBidInfos);

        return "myCenter";
    }



    //跳到我的账户
    @GetMapping("/myAccount")
    public String toMyAccount(HttpSession session, Model model){
        User user = (User) session.getAttribute("loginUser");
        FinanceAccount account = financeAccountService.queryByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("account", account);
        return "myAccount";
    }


}
