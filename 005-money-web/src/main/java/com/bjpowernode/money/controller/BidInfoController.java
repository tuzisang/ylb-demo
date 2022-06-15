package com.bjpowernode.money.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.money.service.BidInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author tzsang
 * @create 2022-06-15 11:17
 */
@Controller
public class BidInfoController {
    @Reference(interfaceClass = BidInfoService.class, version = "1.0.0", check = false)
    protected  BidInfoService bidInfoService;

    @GetMapping("/loan/initRanking")
    @ResponseBody
    public String initRanking(){
        bidInfoService.initRanking();
        bidInfoService.queryRanking(10);
        return "排行榜初始化";
    }
}
