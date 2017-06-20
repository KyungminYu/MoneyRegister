package com.lg.moneyregister;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText PasswordEt;
    private Button loginBtn, signUpBtn, seekPasswordBtn;
    private DataBaseHandler dbHandler;
    private PersonData person;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verifyStoragePermissions(LoginActivity.this);

        Intent serviceIntent = new Intent(getApplicationContext(), AutoLogoutService.class);
        getApplicationContext().stopService(serviceIntent);
        dbHandler = MainActivity.getDBHandler();

        PasswordEt = (EditText)findViewById(R.id.passwordEt);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        signUpBtn = (Button)findViewById(R.id.signUpBtn);
        seekPasswordBtn = (Button)findViewById(R.id.seekPasswordBtn);

        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
        seekPasswordBtn.setOnClickListener(this);
    }
    private boolean CheckPassword(){
        person = dbHandler.getPersonData();
        if(person==null){
            Toast.makeText(getApplicationContext(), "회원가입 해야 이용이 가능합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(person.getPW().equals(PasswordEt.getText().toString()))
            return true;
        Toast.makeText(getApplicationContext(), "올바른 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
        return false;
    }
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.loginBtn:
                if(CheckPassword()) {
                    SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLogin", true);
                    editor.commit();
                    finish();
                }
                break;
            case R.id.signUpBtn:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.seekPasswordBtn:
                intent = new Intent(this, SeekPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     * 안들이드 5.0이상부터는 매니페스트 퍼미션 외에 코드 내에 퍼비션을 삽입해야 한다.
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE, 1);
        }
    }
}
