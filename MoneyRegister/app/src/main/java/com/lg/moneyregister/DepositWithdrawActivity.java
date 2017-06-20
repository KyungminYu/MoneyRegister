package com.lg.moneyregister;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DepositWithdrawActivity extends AppCompatActivity implements View.OnClickListener{
    private Button depositBtn, withdrawBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_withdraw);
        depositBtn = (Button)findViewById(R.id.depositBtn);
        withdrawBtn = (Button)findViewById(R.id.withdrawBtn);

        depositBtn.setOnClickListener(this);
        withdrawBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.depositBtn:
                intent = new Intent(getApplicationContext(), DepositActivity.class);
                startActivity(intent);
                break;
            case R.id.withdrawBtn:
                intent = new Intent(getApplicationContext(), WithdrawActivity.class);
                startActivity(intent);
                break;
        }
        Intent serviceIntent = new Intent(getApplicationContext(), AutoLogoutService.class);
        stopService(serviceIntent);
        startService(serviceIntent);
    }
}
