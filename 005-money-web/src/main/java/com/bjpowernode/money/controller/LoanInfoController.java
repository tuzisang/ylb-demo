package com.bjpowernode.money.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.money.common.Constants;
import com.bjpowernode.money.model.IncomeRecord;
import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.service.IncomeRecordService;
import com.bjpowernode.money.service.LoanInfoService;
import com.bjpowernode.money.vo.IncomeAndUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-11 11:32
 */
@Controller
@RequestMapping("/loan")
public class LoanInfoController {

    @Reference(interfaceClass = LoanInfoService.class, version = "1.0.0" , check = false)
    private LoanInfoService loanInfoService;

    @Reference(interfaceClass = IncomeRecordService.class, version = "1.0.0", check = false)
    private IncomeRecordService incomeRecordService;

    @GetMapping("/loan")
    public String loan(Integer pageNum, Model model, Integer ptype){
        Integer pageSize = 6;
        ptype = ptype == null ? 0 : ptype;
        pageNum = pageNum == null ? 1: pageNum;
        List<LoanInfo> loanInfos = loanInfoService.queryLoanInfo(ptype, pageNum , pageSize);
        long total = loanInfoService.queryCount(ptype);
        long pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        List<IncomeRecord> ranking = incomeRecordService.queryRanking(5);

        model.addAttribute("loanInfos", loanInfos);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("total", total);
        model.addAttribute("pages", pages);
        model.addAttribute("ptype", ptype);
        model.addAttribute(Constants.INVEST_TOP, ranking);
        return "loan";
    }

    @GetMapping("/loanInfo")
    public String loanInfo(Integer id , Model model){

        LoanInfo info = loanInfoService.queryById(id);
        List<IncomeAndUser> incomeAndUser = incomeRecordService.queryByLoanId(id);


        model.addAttribute("info", info);
        model.addAttribute("incomeAndUser", incomeAndUser);

        return "loanInfo";
    }
}
