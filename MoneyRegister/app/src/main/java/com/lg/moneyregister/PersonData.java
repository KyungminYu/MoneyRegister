package com.lg.moneyregister;

import java.util.Date;

/**
 * Created by LG on 2016-12-09.
 */
public class PersonData {
    private int id;
    private String PW;
    private double balance;
    private String depositDate;
    private String recentDepositDate;
    private double depositAmount;
    private String phoneNum;

    public PersonData() {
        this.PW = "";
        this.balance = 0;
        this.depositDate = "";
        this.recentDepositDate = "";
        this.depositAmount = 0;
        this.phoneNum = "";
    }

    public PersonData(String PW, double balance, String depositDate, String recentDepositDate, double depositAmount, String phoneNum) {
        this.PW = PW;
        this.balance = balance;
        this.depositDate = depositDate;
        this.recentDepositDate = recentDepositDate;
        this.depositAmount = depositAmount;
        this.phoneNum = phoneNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPW() {
        return PW;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(String depositDate) {
        this.depositDate = depositDate;
    }

    public String getRecentDepositDate() {
        return recentDepositDate;
    }

    public void setRecentDepositDate(String recentDepositDate) {
        this.recentDepositDate = recentDepositDate;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
