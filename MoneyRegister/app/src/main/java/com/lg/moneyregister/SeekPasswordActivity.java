package com.lg.moneyregister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SeekPasswordActivity extends AppCompatActivity {
    private EditText phoneNumEt;
    private Button submitBtn;
    private DataBaseHandler dbHandler;
    private PersonData person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_password);
        dbHandler = MainActivity.getDBHandler();

        phoneNumEt = (EditText)findViewById(R.id.phoneNumEt);

        submitBtn = (Button)findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = phoneNumEt.getText().toString();
                person = dbHandler.getPersonData();
                if(person.getPhoneNum().equals(phoneNum)){
                    Toast.makeText(getApplicationContext(), "비밀번호는 '"+person.getPW()+"'입니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "전화번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
