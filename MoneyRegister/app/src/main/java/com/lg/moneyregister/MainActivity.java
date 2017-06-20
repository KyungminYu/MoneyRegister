package com.lg.moneyregister;

import android.app.usage.UsageEvents;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Button> buttons = new ArrayList<>();
    private Button listBtn, calenderBtn, statisticBtn, settingBtn;
    private FloatingActionButton addBtn;
    private ViewPager viewPager;

    private CardView cardView;
    private PersonData person;
    private TextView balanceTv, depositDateTv, leftDaysTv, planPerDateTv;

    private static DataBaseHandler dbHandler;

    private static Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceIntent = new Intent(getApplicationContext(), AutoLogoutService.class);
        dbHandler = DataBaseHandler.getInstance(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("isLogin", false)) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        else{
            initButtons();
            initViewPager();
            initPlanner();
            stopService(serviceIntent);
            startService(serviceIntent);
        }
    }

    private void initButtons() {
        listBtn = (Button) findViewById(R.id.listBtn);
        calenderBtn = (Button) findViewById(R.id.calendarBtn);
        statisticBtn = (Button) findViewById(R.id.statisticBtn);
        settingBtn = (Button) findViewById(R.id.settingBtn);
        addBtn = (FloatingActionButton) findViewById(R.id.addBtn);

        listBtn.setOnClickListener(this);
        calenderBtn.setOnClickListener(this);
        statisticBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

        buttons.add(listBtn);
        buttons.add(calenderBtn);
        buttons.add(statisticBtn);
        buttons.add(settingBtn);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.container);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListFragment());
        adapter.addFragment(new CalenderFragment());
        adapter.addFragment(new StatisticFragment());
        adapter.addFragment(new SettingFragment());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                layoutStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    private void initPlanner() {
        cardView = (CardView) findViewById(R.id.curStatus);
        depositDateTv = (TextView)findViewById(R.id.depositDateTv);
        leftDaysTv = (TextView)findViewById(R.id.leftDaysTv);
        balanceTv = (TextView)findViewById(R.id.balanceTv);
        planPerDateTv = (TextView)findViewById(R.id.planPerDateTv);

        person = dbHandler.getPersonData();

        long recentDeposit = DateUtil.getDate(person.getRecentDepositDate());
        long depositDate = Long.parseLong(person.getDepositDate())*86400000;
        long destDate = recentDeposit+depositDate;
        int leftDay = calLeftDays(destDate);
        double balance = person.getBalance();
        int planPerDate = calPlanPerDate(leftDay, balance);

        //이 부분에 Planner 계산해서 집어넣어야 함
        depositDateTv.setText(DateUtil.getDate(destDate));
        leftDaysTv.setText(String.valueOf(leftDay)+"일");
        balanceTv.setText(String.valueOf((int)balance)+"원");
        if(planPerDate<5000)
            planPerDateTv.setTextColor(Color.RED);
        planPerDateTv.setText(String.valueOf(planPerDate)+"원");
    }
    private void layoutStatus(int curPos) {
        if (curPos < 3) {
            cardView.setVisibility(View.VISIBLE);
            addBtn.setVisibility(View.VISIBLE);
        }
        else {
            cardView.setVisibility(View.GONE);
            addBtn.setVisibility(View.GONE);
        }
        for(int i=0;i<4;i++) {
            if(curPos == i)
                buttons.get(i).setBackgroundColor(Color.argb(255, 32, 32, 32));
            else
                buttons.get(i).setBackgroundColor(Color.argb(255, 0, 0, 0));
        }
    }

    @Override
    public void onClick(View v) {
        int btnId = v.getId();
        if (btnId == R.id.listBtn){
            viewPager.setCurrentItem(0);
            layoutStatus(0);
        }
        else if (btnId == R.id.calendarBtn){
            viewPager.setCurrentItem(1);
            layoutStatus(1);
        }
        else if (btnId == R.id.statisticBtn){
            viewPager.setCurrentItem(2);
            layoutStatus(2);
        }
        else if (btnId == R.id.settingBtn){
            viewPager.setCurrentItem(3);
            layoutStatus(3);
        }
        else if (btnId == R.id.addBtn){
            Intent intent = new Intent(getApplicationContext(), DepositWithdrawActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "수입/지출 추가 버튼이 선택되었습니다.", Toast.LENGTH_SHORT).show();
        }
        stopService(serviceIntent);
        startService(serviceIntent);
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", false);
        editor.commit();
        super.onBackPressed();
    }

    private int calPlanPerDate(int leftDay, double balance){
        double planPerDate = balance/leftDay;
        return (int)planPerDate;
    }

    private int calLeftDays(long destDate){
        return (int)(destDate - DateUtil.getDate(DateUtil.getToday()))/86400000;
    }
    
    public static DataBaseHandler getDBHandler(){
        return dbHandler;
    }


}
