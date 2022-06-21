package com.bjpowernode.money.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.money.model.RechargeRecord;
import com.bjpowernode.money.model.User;
import com.bjpowernode.money.service.FinanceAccountService;
import com.bjpowernode.money.service.RechargeRecordService;
import com.bjpowernode.money.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @author tzsang
 * @create 2022-06-14 20:00
 */
@Controller
@RequestMapping("/loan/page")
public class RechargeController {
    @Reference(interfaceClass = RechargeRecordService.class, version = "1.0.0", check = false)
    private RechargeRecordService rechargeRecordService;
    @Reference(interfaceClass = FinanceAccountService.class, version =  "1.0.0", check = false)
    private FinanceAccountService financeAccountService;
    @Reference(interfaceClass = UserService.class, version = "1.0.0", check = false)
    private UserService userService;

    @GetMapping("/toRecharge")
    public String toPageRecharge(){

        return "toRecharge";
    }

    @PostMapping("/toRecharge")
    public String  toRecharge(Double rechargeMoney ,String payType, HttpSession session){

        long out_trade_no = System.currentTimeMillis();

        //添加一条充值记录，充值状态是充值中
        RechargeRecord record = new RechargeRecord();
        User loginUser = (User) session.getAttribute("loginUser");
        record.setUid(loginUser.getId());
        record.setRechargeNo(String.valueOf(out_trade_no));
        record.setRechargeStatus("0");
        record.setRechargeMoney(rechargeMoney);
        record.setRechargeTime(new Date());


        System.out.println("订单号："+ out_trade_no);
        System.out.println("充值金额："+ rechargeMoney);

        session.setAttribute("out_trade_no", out_trade_no);
        session.setAttribute("total_amount", rechargeMoney);

        if ("alipay".equals(payType)){
            //支付宝
            record.setRechargeDesc("支付宝");
            rechargeRecordService.addRecharge(record);
            return "redirect:http://localhost:8083/tradePay?out_trade_no=" +
                    out_trade_no +
                    "&total_amount=" + rechargeMoney +
                    "&subject=测试";
        }else if ("weixinpay".equals(payType)){
            //微信没有
            record.setRechargeDesc("微信");
            return "weixinpayResult";
        }else {
            //快钱支付
            record.setRechargeDesc("快钱");
            rechargeRecordService.addRecharge(record);
            String time = new SimpleDateFormat( "yyyyMMddHHmmss").format(new Date());
            return "redirect:http://localhost:8084/toPay?orderTime=" + time
                    + "&orderId=" + out_trade_no
                    + "&orderAmount=" + rechargeMoney ;

        }


    }



    @GetMapping("/payResult")
    public String toPayResult(String trade_msg, HttpSession session) throws UnsupportedEncodingException {
        trade_msg = new String(trade_msg.getBytes("iso8859-1"),"utf-8");
        long out_trade_no = (long) session.getAttribute("out_trade_no");
        RechargeRecord record = rechargeRecordService.queryByNo(String.valueOf(out_trade_no));
        if (trade_msg.equals("1")){
            trade_msg = "验签失败，请稍后再检查交易结果";
        }else if(trade_msg.equals("2")){
            trade_msg = "支付结果查询失败，请稍后再检查交易结果";
        }else if(trade_msg.equals("3")){
            //更新充值状态
            record.setRechargeStatus("1");
            //添加账户余额
            Double money = (Double) session.getAttribute("total_amount");
            User user = (User) session.getAttribute("loginUser");
            financeAccountService.addAvailableMoneyByUid(money, user.getId());
            //更新session
            user = userService.queryById(user.getId());
            session.setAttribute("loginUser", user);

            trade_msg = "支付成功";
        }else if(trade_msg.equals("4")){
            //充值状态失败
            record.setRechargeStatus("2");

            trade_msg = "支付失败，订单作废";
        }else if(trade_msg.equals("5")){
            trade_msg = "尚未支付，请及时付款";
        }


        System.out.println("支付结果:" + trade_msg);
        rechargeRecordService.edit(record);

        return "redirect:/loan/myRecharge";
    }
}
