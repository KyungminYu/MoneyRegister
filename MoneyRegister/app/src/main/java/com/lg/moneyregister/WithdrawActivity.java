package com.lg.moneyregister;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class WithdrawActivity extends AppCompatActivity {
    private EditText amountEt;
    private Spinner categorySp, installMonthSp, exchangeSp;
    private Button submitBtn;
    private DataBaseHandler dbHandler;
    private MoneyData data;
    private PersonData person;
    private double amount, curBalance;
    private String thisDay;
    private int installMonth;
    private String category;
    private String exchange;

    Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        thisDay = getIntent().getStringExtra("DATE");
        dbHandler = MainActivity.getDBHandler();

        amountEt = (EditText)findViewById(R.id.amountEt);
        categorySp = (Spinner) findViewById(R.id.categorySp);
        exchangeSp = (Spinner) findViewById(R.id.exchangeSp);
        installMonthSp = (Spinner) findViewById(R.id.installMonthSp);
        submitBtn = (Button)findViewById(R.id.nextBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBalance()) {
                    Toast.makeText(getApplicationContext(), "충분" + (String) categorySp.getSelectedItem() + amountEt.getText().toString(), Toast.LENGTH_SHORT).show();
                    amount = Double.parseDouble(amountEt.getText().toString());
                   // data = new MoneyData(amount, 0, categorySp.getSelectedItem().toString(), thisDay, installMonthSp.getSelectedItemPosition()+1);


                    installMonth = Integer.parseInt(installMonthSp.getSelectedItem().toString());
                    category = categorySp.getSelectedItem().toString();
                    exchange = exchangeSp.getSelectedItem().toString();
                    data = new MoneyData(amount/installMonth, 0, categorySp.getSelectedItem().toString(), DateUtil.getToday(), installMonthSp.getSelectedItemPosition()+1);

                    if(installMonth==1){
                        person.setBalance(curBalance - amount);
                        dbHandler.insertData(data);
                        dbHandler.updatePersonData(person);
                    }else{
                        monthlyWithdraw(installMonth,amount / installMonth);
                    }





                    Intent serviceIntent = new Intent(getApplicationContext(), AutoLogoutService.class);
                    getApplicationContext().stopService(serviceIntent);
                    getApplicationContext().startService(serviceIntent);
                    finishAffinity();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "부족"+(String)categorySp.getSelectedItem() + amountEt.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void monthlyWithdraw(int num,double amount){
        Intent intent = new Intent(getApplicationContext(), AlarmService_Service.class);

        intent.putExtra("num",num);
        intent.putExtra("amount",amount);
        intent.putExtra("date",thisDay);
        intent.putExtra("category",category);
        intent.putExtra("exchange",exchange);

        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(),
                0, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                firstTime,1000*60*60*24*30, sender);//한달에 한번씩 출금하는 리씨버 불러준다.

        mToast = Toast.makeText(getApplicationContext(),"금액이 "+installMonth+"개월간  할부출금 됩니다.",
                Toast.LENGTH_LONG);
        mToast.show();

    }
    private boolean checkBalance(){
        person = dbHandler.getPersonData();
        curBalance = person.getBalance();
        Exchange exchange = new Exchange(Double.parseDouble(amountEt.getText().toString()), exchangeSp.getSelectedItem().toString());
        amount = exchange.getExchangedMoney();
        if(curBalance >= amount)
            return true;
        else
            return false;
    }
}
