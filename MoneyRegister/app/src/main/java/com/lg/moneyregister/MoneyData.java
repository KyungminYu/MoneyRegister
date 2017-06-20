package com.lg.moneyregister;

import java.util.Date;

/**
 * Created by LG on 2016-11-24.
 */

public class MoneyData {
    private int id;
    private double amount;
    private int isInput;
    private String category;
    private String date;
    private int installmentMonth;

    public MoneyData() {
    }

    public MoneyData(double amount, int isInput, String category, String date, int installmentMonth) {
        this.amount = amount;
        this.isInput = isInput;
        this.category = category;
        this.date = date;
        this.installmentMonth = installmentMonth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int isInput() {
        return isInput;
    }

    public void setInput(int input) {
        isInput = input;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getInstallmentMonth() {
        return installmentMonth;
    }

    public void setInstallmentMonth(int installmentMonth) {
        this.installmentMonth = installmentMonth;
    }
}
