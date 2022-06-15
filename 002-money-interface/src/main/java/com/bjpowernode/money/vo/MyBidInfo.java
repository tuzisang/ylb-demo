package com.bjpowernode.money.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzsang
 * @create 2022-06-14 20:23
 */
public class MyBidInfo implements Serializable {

    private Date date;
    private String name;
    private String status;
    private Double money;

    public MyBidInfo() {
    }

    public MyBidInfo(Date date, String name, String status, Double money) {
        this.date = date;
        this.name = name;
        this.status = status;
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
