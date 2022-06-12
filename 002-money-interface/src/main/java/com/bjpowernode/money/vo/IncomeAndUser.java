package com.bjpowernode.money.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.xml.internal.ws.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzsang
 * @create 2022-06-11 20:26
 */

public class IncomeAndUser implements Serializable {
    private String phone;
    private Double bidMoney;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date incomeDate;

    public IncomeAndUser() {
    }

    public IncomeAndUser(String phone, Double bidMoney, Date incomeDate) {
        this.phone = phone;
        this.bidMoney = bidMoney;
        this.incomeDate = incomeDate;
    }

    public String getPhone() {
        return phone;
    }
    public String getSecretPhone(){
        return this.phone.substring(0,3) + "******" + this.phone.substring(9);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(Double bidMoney) {
        this.bidMoney = bidMoney;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }
}
