package com.lg.moneyregister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewDayDataActivity extends AppCompatActivity {
    private TextView thisDayTv, depositAmountTv, withdrawAmountTv;
    private String thisDay;
    private double depositAmount, withdrawAmount;
    private List<MoneyData> dataList = new ArrayList<>();
    private DataBaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_day_data);

        dbHandler = MainActivity.getDBHandler();

        thisDay = getIntent().getStringExtra("DATE");
        Log.i("today string viewday", thisDay);
        dataList = dbHandler.getSpecificDataList(thisDay);


        depositAmount = withdrawAmount =  0;



            for(MoneyData data :dataList){

                if (data.isInput() == 0) {
                    withdrawAmount += data.getAmount();
                } else {
                    depositAmount += data.getAmount();
                }
        }



        thisDayTv = (TextView)findViewById(R.id.thisDayTv);
        depositAmountTv = (TextView)findViewById(R.id.depositAmountTv);
        withdrawAmountTv = (TextView)findViewById(R.id.withdrawAmountTv);

        thisDayTv.setText(thisDay);
        depositAmountTv.setText((int)depositAmount+"원");
        withdrawAmountTv.setText((int)withdrawAmount+"원");


    }
}
