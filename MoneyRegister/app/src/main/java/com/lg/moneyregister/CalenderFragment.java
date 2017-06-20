package com.lg.moneyregister;

import android.content.Intent;
//import android.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import static com.lg.moneyregister.DateUtil.getDate;

/**
 * Created by LG on 2016-11-23.
 */

public class CalenderFragment extends Fragment {
    private CalendarView checkCalendar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        checkCalendar = (CalendarView)view.findViewById(R.id.Calendar);

        checkCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Intent serviceIntent = new Intent(getContext(), AutoLogoutService.class);
                getContext().stopService(serviceIntent);
                getContext().startService(serviceIntent);
                Intent intent = new Intent(getContext(), ViewDayDataActivity.class);
                intent.putExtra("DATE", getDate(view.getDate()));

                Intent intent2 = new Intent(getContext(),DepositActivity.class);
                intent2.putExtra("DATE",getDate(view.getDate()));
                Log.i("calener date ", getDate(view.getDate()));

                Intent intent3 = new Intent(getContext(),WithdrawActivity.class);
                intent3.putExtra("DATE",getDate(view.getDate()));
                startActivity(intent);
                startActivity(intent2);
                //startActivity(intent3);
            }
        });

        return view;
    }
}