package com.bjpowernode.money.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzsang
 * @create 2022-06-15 12:00
 */
public class BidInfoOfLoanInfo implements Serializable {
    private String name;
    private Double money;
    private Date date;

    public BidInfoOfLoanInfo() {
    }

    public BidInfoOfLoanInfo(String name, Double money, Date date) {
        this.name = name;
        this.money = money;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
