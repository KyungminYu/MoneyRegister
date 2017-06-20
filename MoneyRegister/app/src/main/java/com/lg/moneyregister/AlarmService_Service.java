package com.lg.moneyregister;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by kang on 2016-12-26.
 */
public class AlarmService_Service extends BroadcastReceiver {
    private DataBaseHandler dbHandler;
    private MoneyData data;
    private PersonData person;

    private int num;
    private Double amount;
    private Double curBalance;
    private String category;
    private String exchange;


    @Override
    public void onReceive(Context context, Intent intent) //30일간격으로 들어옴
    {
        if(num==0){
            return;
        }
        dbHandler = new DataBaseHandler(context);
        num =intent.getIntExtra("num", 0);
        amount = intent.getDoubleExtra("amount", 0);
        exchange = intent.getStringExtra("exchange");
        category = intent.getStringExtra("category");

        data = new MoneyData(amount, 0, category, DateUtil.getToday(), num);

        person = dbHandler.getPersonData();
        curBalance = person.getBalance();

        Exchange ex = new Exchange(amount, exchange);
        amount = ex.getExchangedMoney();
        person.setBalance(curBalance - amount);

        dbHandler.insertData(data);
        dbHandler.updatePersonData(person);

        Toast.makeText(context,"할부 "+amount+"출금 됌 "+(num-1) +"회 남음", Toast.LENGTH_SHORT).show();

        num = num-1;

    }
}