package com.bjpowernode.money.model;

import java.io.Serializable;
import java.util.Date;

public class IncomeRecord implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer loanId;

    private Integer bidId;

    private Double bidMoney;

    private Date incomeDate;

    private Double incomeMoney;

    private Integer incomeStatus;

    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
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

    public Double getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(Double incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public Integer getIncomeStatus() {
        return incomeStatus;
    }

    public void setIncomeStatus(Integer incomeStatus) {
        this.incomeStatus = incomeStatus;
    }

    @Override
    public String toString() {
        return "IncomeRecord{" +
                "id=" + id +
                ", uid=" + uid +
                ", loanId=" + loanId +
                ", bidId=" + bidId +
                ", bidMoney=" + bidMoney +
                ", incomeDate=" + incomeDate +
                ", incomeMoney=" + incomeMoney +
                ", incomeStatus=" + incomeStatus +
                ", user=" + user +
                '}';
    }
}