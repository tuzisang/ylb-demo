package com.bjpowernode.money.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzsang
 * @create 2022-06-14 13:14
 */
public class MyIncomeRecord implements Serializable {
    private Date date;
    private Double incomeMoney;
    private String name;
    private Double bidMoney;
    private String status;

    public MyIncomeRecord() {
    }

    public MyIncomeRecord(Date date, Double incomeMoney, String name, Double bidMoney, String status) {
        this.date = date;
        this.incomeMoney = incomeMoney;
        this.name = name;
        this.bidMoney = bidMoney;
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(Double incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(Double bidMoney) {
        this.bidMoney = bidMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
