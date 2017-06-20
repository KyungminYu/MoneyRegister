package com.lg.moneyregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyInfoActivity extends AppCompatActivity {
    private PersonData person;
    private TextView curDepositDateTv, curDepositAmountTv;
    private EditText depositDateEt, depositAmountEt;
    private Button submitBtn;
    private DataBaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);

        dbHandler = MainActivity.getDBHandler();
        person = dbHandler.getPersonData();
        curDepositDateTv = (TextView)findViewById(R.id.curDepositDateTv);
        curDepositAmountTv = (TextView)findViewById(R.id.curDepositAmountTv);
        depositDateEt = (EditText)findViewById(R.id.depositDateEt);
        depositAmountEt = (EditText)findViewById(R.id.depositAmountEt);
        submitBtn = (Button)findViewById(R.id.submitBtn);

        curDepositDateTv.setText(person.getDepositDate()+"일 간격");
        curDepositAmountTv.setText(String.valueOf((int)person.getDepositAmount())+"원");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String depositDate = depositDateEt.getText().toString();
                String depositAmount = depositAmountEt.getText().toString();
                if(depositAmount.equals("") || depositDate.equals("")){
                    Toast.makeText(getApplicationContext(), "숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    person.setDepositDate(depositDate);
                    person.setDepositAmount(Double.parseDouble(depositAmount));

                    dbHandler.updatePersonData(person);
                    finishAffinity();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
