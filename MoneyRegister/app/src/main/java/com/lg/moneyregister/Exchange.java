package com.lg.moneyregister;

import android.support.v7.app.AppCompatActivity;

public class Exchange extends AppCompatActivity {
    private double inputMoney, outputMoney;
    private String country;

    public Exchange(double inputMoney, String country) {
        this.inputMoney = inputMoney;
        this.country = country;
    }
    public double getExchangedMoney(){
        //환율 계산 파트 집어넣기
        if(country.equals("USD") == true)
        {
            outputMoney = inputMoney*1000;
        }
        else if(country.equals("EUR") == true)
        {
            outputMoney = inputMoney*1600;
        }
        else if(country.equals("KRW") == true)
        {
            outputMoney = inputMoney;
        }

        return outputMoney;
    }
}
