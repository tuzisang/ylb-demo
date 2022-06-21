package com.bjpowernode.money.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.money.model.RechargeRecord;
import com.bjpowernode.money.model.User;
import com.bjpowernode.money.service.BidInfoService;
import com.bjpowernode.money.service.IncomeRecordService;
import com.bjpowernode.money.service.RechargeRecordService;
import com.bjpowernode.money.vo.MyBidInfo;
import com.bjpowernode.money.vo.MyIncomeRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-15 8:28
 */
@Controller
@RequestMapping("/loan")
public class AllRecordController {
    @Reference(interfaceClass = IncomeRecordService.class, version = "1.0.0", check = false)
    private IncomeRecordService incomeRecordService;
    @Reference(interfaceClass = BidInfoService.class, version = "1.0.0", check = false)
    private BidInfoService bidInfoService;
    @Reference(interfaceClass = RechargeRecordService.class, version = "1.0.0", check = false)
    private RechargeRecordService rechargeRecordService;

    private Integer pageSize;

    {
        pageSize = 6;
    }

    //充值记录
    @GetMapping("/myRecharge")
    public String toMyRecharge(Integer pageNum, HttpSession session, Model model) {

        pageNum = pageNum == null ? 1 : pageNum;

        User user = (User) session.getAttribute("loginUser");
        List<RechargeRecord> rechargeRecords = rechargeRecordService.queryById(user.getId(), pageNum, pageSize);
        long total = rechargeRecordService.queryById(user.getId(), 1, 1000000000).size();
        long pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        model.addAttribute("rechargeRecords", rechargeRecords);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("total", total);
        model.addAttribute("pages", pages);

        return "myRecharge";
    }

    //投资记录
    @GetMapping("/myInvest")
    public String toMyInvest(Integer pageNum, HttpSession session, Model model) {
        pageNum = pageNum == null ? 1 : pageNum;

        User user = (User) session.getAttribute("loginUser");
        List<MyBidInfo> myBidInfos = bidInfoService.queryDescByUid(user.getId(), pageNum, pageSize);
        long total = bidInfoService.queryDescByUid(user.getId(), 1, 10000000).size();
        long pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        model.addAttribute("myBidInfos", myBidInfos);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("total", total);
        model.addAttribute("pages", pages);

        return "myInvest";
    }


    //收益记录
    @GetMapping("/myIncome")
    public String toMyIncome(Integer pageNum, HttpSession session, Model model) {
        pageNum = pageNum == null ? 1 : pageNum;

        User user = (User) session.getAttribute("loginUser");
        List<MyIncomeRecord> myIncomeRecords = incomeRecordService.queryDescByUid(user.getId(), pageNum, pageSize);
        long total = incomeRecordService.queryDescByUid(user.getId(), 1, 1000000).size();
        long pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        model.addAttribute("myIncomeRecords", myIncomeRecords);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("total", total);
        model.addAttribute("pages", pages);

        return "myIncome";
    }
}
