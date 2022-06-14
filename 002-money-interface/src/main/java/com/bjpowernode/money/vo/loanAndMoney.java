package com.bjpowernode.money.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzsang
 * @create 2022-06-14 13:14
 */
public class loanAndMoney implements Serializable {
    private String loanName;
    private Double money;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

    public loanAndMoney() {
    }

    public loanAndMoney(String loanName, Double money, Date date) {
        this.loanName = loanName;
        this.money = money;
        this.date = date;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
