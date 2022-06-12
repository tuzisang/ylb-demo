package com.bjpowernode.money.controller;


import  com.bjpowernode.money.common.Constants;
import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.service.BidInfoService;
import com.bjpowernode.money.service.LoanInfoService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.money.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-10 19:47
 */
@Controller
public class IndexController {
    @Reference(interfaceClass = LoanInfoService.class, version = "1.0.0", check= false)
    private LoanInfoService loanInfoService;

    @Reference(interfaceClass = UserService.class, version = "1.0.0", check = false)
    private UserService userService;

    @Reference(interfaceClass = BidInfoService.class, version = "1.0.0", check = false)
    private BidInfoService bidInfoService;

    @GetMapping(value = {"/index", "/"})
    public String index(Model model){

        double historyAverageRate = loanInfoService.queryHistoryAverageRate();
        long userCount = userService.countAll();
        long sumBid = bidInfoService.sumBid();
        List<LoanInfo> noobs = loanInfoService.queryLoanInfo(Constants.PRODUCT_TYPE_X, 1, 1);
        List<LoanInfo> excellencies = loanInfoService.queryLoanInfo(Constants.PRODUCT_TYPE_U, 1, 4);
        List<LoanInfo> disperses = loanInfoService.queryLoanInfo(Constants.PRODUCT_TYPE_S, 1, 8);

        model.addAttribute(Constants.HISTORY_AVERAGE_RATE, historyAverageRate);
        model.addAttribute(Constants.ALL_USER_COUNT, userCount);
        model.addAttribute(Constants.ALL_BID_MONEY, sumBid);
        model.addAttribute("noobs", noobs);
        model.addAttribute("excellencies", excellencies);
        model.addAttribute("disperses", disperses);

        return "index";
    }
}
