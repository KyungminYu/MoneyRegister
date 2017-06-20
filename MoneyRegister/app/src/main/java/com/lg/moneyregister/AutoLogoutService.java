package com.lg.moneyregister;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class AutoLogoutService extends Service {
    private CountDownTimer countDownTimer;
    public AutoLogoutService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("logoutService", "onCreate");
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("logoutService", "onTick"+millisUntilFinished);
            }

            @Override
            public void onFinish() {
                SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin", false);
                editor.commit();
                Log.i("logoutService", "onFinish");

                //여기에서 MainActivity Refresh 시키면 될듯 BroadCast로 하면 될거 같은데 방법을 모르겠음 - 경민
            }
        };

        countDownTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("logoutSevice", "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
