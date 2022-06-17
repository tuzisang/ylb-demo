package com.bjpowernode.money.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author tzsang
 * @create 2022-06-14 20:00
 */
@Controller
@RequestMapping("/loan/page")
public class RechargeController {

    @GetMapping("/toRecharge")
    public String toPageRecharge(){

        return "toRecharge";
    }

    @PostMapping("/toRecharge")
    public String  toRecharge(Double rechargeMoney , HttpSession session){
        session.setAttribute("alipay", "111111");

        return "redirect:http://localhost:8083/tradePay?out_trade_no=" +
                System.currentTimeMillis() + "&total_amount=" + rechargeMoney +
                "&subject=测试";
    }
}
