package com.lg.moneyregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

public class DepositActivity extends AppCompatActivity {
    private EditText amountEt;
    private Spinner exchangeSp;
    private Button submitBtn;
    private DataBaseHandler dbHandler;
    private MoneyData data;
    private PersonData person;
    private double amount;
    private String thisDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        dbHandler = MainActivity.getDBHandler();
        thisDay = getIntent().getStringExtra("DATE");


        amountEt = (EditText)findViewById(R.id.amountEt);
        exchangeSp = (Spinner) findViewById(R.id.exchangeSp);
        submitBtn = (Button)findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), amountEt.getText().toString(), Toast.LENGTH_SHORT).show();
                Exchange exchange = new Exchange(Double.parseDouble(amountEt.getText().toString()), exchangeSp.getSelectedItem().toString());
                amount = exchange.getExchangedMoney();

                //data = new MoneyData(amount, 1, "-", thisDay, -1);
                data = new MoneyData(amount, 1, "-", DateUtil.getToday(), -1);
                person = dbHandler.getPersonData();
                person.setBalance(person.getBalance()+amount);
                dbHandler.insertData(data);
                dbHandler.updatePersonData(person);


                finishAffinity();
                Intent serviceIntent = new Intent(getApplicationContext(), AutoLogoutService.class);
                stopService(serviceIntent);
                startService(serviceIntent);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
