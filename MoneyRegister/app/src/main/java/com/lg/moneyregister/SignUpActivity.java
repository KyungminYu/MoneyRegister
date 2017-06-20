package com.lg.moneyregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.sql.Types.DOUBLE;

public class SignUpActivity extends AppCompatActivity {
    private PersonData person;
    private EditText PWEt, phoneNumEt, depositDateEt, depositAmountEt;
    private Button submitBtn;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHandler = MainActivity.getDBHandler();

        PWEt = (EditText)findViewById(R.id.passwordEt);
        phoneNumEt = (EditText)findViewById(R.id.phoneNumEt);
        depositDateEt = (EditText)findViewById(R.id.depositDateEt);
        depositAmountEt = (EditText)findViewById(R.id.depositAmountEt);

        TelephonyManager telManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        phoneNumEt.setText(telManager.getLine1Number());

        submitBtn = (Button)findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(getApplicationContext(), AutoLogoutService.class);
                getApplicationContext().stopService(serviceIntent);
                getApplicationContext().startService(serviceIntent);
                String PW, depositDate, depositAmount, phoneNum;
                PW = PWEt.getText().toString();
                depositDate = depositDateEt.getText().toString();
                depositAmount = depositAmountEt.getText().toString();
                phoneNum = phoneNumEt.getText().toString();
                if(!(PW == "" || depositDate == "" || depositAmount == ""|| phoneNum == "")) {
                    person = new PersonData(PW, 0, depositDate, DateUtil.getToday(), Double.parseDouble(depositAmount), phoneNum);
                    insert(person);
                    finish();
                }
            }
        });
    }

    private void insert(PersonData data){
        dbHandler.insertPersonData(data);
    }
}
