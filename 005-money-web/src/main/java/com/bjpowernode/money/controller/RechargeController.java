package com.bjpowernode.money.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tzsang
 * @create 2022-06-14 20:00
 */
@Controller
@RequestMapping("/loan/page")
public class RechargeController {

    @GetMapping("/toRecharge")
    public String toRecharge(){

        return "toRecharge";
    }
}
