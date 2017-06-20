package com.lg.moneyregister;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LG on 2016-11-23.
 */

public class StatisticFragment extends Fragment implements View.OnClickListener{
    private LineChart lineChart;
    private PieChart pieChart;
    private List<Button> buttons = new ArrayList<>();
    private Button dayBtn, monthBtn, categoryBtn;
    private DataBaseHandler dbHandler;
    private List<MoneyData> dataList;
    private static int page = 0;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        dbHandler = MainActivity.getDBHandler();

        dayBtn = (Button)view.findViewById(R.id.dayBtn);
        monthBtn = (Button)view.findViewById(R.id.monthBtn);
        categoryBtn = (Button)view.findViewById(R.id.categoryBtn);
        dayBtn.setOnClickListener(this);
        monthBtn.setOnClickListener(this);
        categoryBtn.setOnClickListener(this);
        buttons.add(dayBtn);
        buttons.add(monthBtn);
        buttons.add(categoryBtn);

        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        pieChart = (PieChart)view.findViewById(R.id.pieChart);

        layoutStatus(page);
        if(page ==0)
            drawDayStatistic();
        else if(page ==1)
            drawMonthStatistic();
        else
            drawCategoryStatistic();
        return view;
    }


    @Override
    public void onClick(View v) {
        int btnId = v.getId();
        if (btnId == R.id.dayBtn){
            layoutStatus(0);
            drawDayStatistic();
            page = 0;
        }
        else if (btnId == R.id.monthBtn){
            layoutStatus(1);
            drawMonthStatistic();
            page = 1;
        }
        else if (btnId == R.id.categoryBtn){
            layoutStatus(2);
            drawCategoryStatistic();
            page = 2;
        }
        Intent serviceIntent = new Intent(getContext(), AutoLogoutService.class);
        getContext().stopService(serviceIntent);
        getContext().startService(serviceIntent);
    }
    private void layoutStatus(int curPos) {
        if (curPos < 2) {
            lineChart.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.GONE);
        }
        else {
            lineChart.setVisibility(View.GONE);
            pieChart.setVisibility(View.VISIBLE);
        }
        for(int i=0;i<3;i++) {
            if(curPos == i)
                buttons.get(i).setBackgroundColor(Color.argb(255, 32, 32, 32));
            else
                buttons.get(i).setBackgroundColor(Color.argb(255, 0, 0, 0));
        }
    }

    private void drawDayStatistic(){
        String during = DateUtil.getDate(DateUtil.getDate(DateUtil.getToday())-86400000*6);//86400000밀리초가 하루

        ArrayList<Entry> deposit = new ArrayList<>();
        ArrayList<Entry> withdraw = new ArrayList<>();
        final HashMap<Integer, String> labels = new HashMap<>();
        for(int i=0;i<7;i++){
            labels.put(i, DateUtil.getDate(DateUtil.getDate(during)+86400000*i).substring(2));
        }


        for(int i=0;i<7;i++) {
            dataList = dbHandler.getSpecificDataList(String.valueOf("20"+DateUtil.getDate(DateUtil.getDate(during)+86400000*i).substring(2)));
            //Log.i("today string statistic", String.valueOf("20"+DateUtil.getDate(DateUtil.getDate(during)+86400000*i).substring(2)));
            //Log.i("today string statistic2", DateUtil.getToday());
            int totaldeposit = 0;
            int totalwitdraw = 0;
            for (MoneyData data : dataList) {

                if (data.isInput() == 0) {

                    totalwitdraw += data.getAmount();
                } else {

                    totaldeposit +=  data.getAmount();
                }
            }
            deposit.add(new Entry(i, totaldeposit));
            withdraw.add(new Entry(i, totalwitdraw));
        }


        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        LineDataSet depositDataSet = new LineDataSet(deposit, "입금");
        LineDataSet withdrawDataSet = new LineDataSet(withdraw, "출금");

        depositDataSet.setDrawCircles(true);
        depositDataSet.setColors(Color.BLUE);
        withdrawDataSet.setDrawCircles(true);
        withdrawDataSet.setColors(Color.RED);

        lineDataSets.add(depositDataSet);
        lineDataSets.add(withdrawDataSet);

        lineChart.setData(new LineData(lineDataSets));
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int)value);
            }
        });
        lineChart.animateY(1000);
        lineChart.invalidate();
    }


    private void drawMonthStatistic(){
        String current = DateUtil.getToday();
        StringBuilder during = new StringBuilder(current);
        int year, mon;
        year = Integer.parseInt(during.substring(2,4))-1;
        mon = Integer.parseInt(during.substring(5,7));

        during.setCharAt(2, Character.forDigit(year/10, 10));
        during.setCharAt(3, Character.forDigit(year%10, 10));

        ArrayList<Entry> deposit = new ArrayList<>();
        ArrayList<Entry> withdraw = new ArrayList<>();
        final HashMap<Integer, String> labels = new HashMap<>();
        for(int i=0;i<12;i++){
            String labelValue;
            if(mon == 12){
                mon=1;
                year++;
            }
            else
                mon++;
            if(mon <10)
                labelValue = year+".0"+mon+".";
            else
                labelValue = year+"."+mon+".";
            Log.i("", labelValue);
            labels.put(i, labelValue);
        }
        //database에서 날짜별로, 입출금 여부에 따라 합쳐서 엔트리에 집어넣으면 될거로 추정
        //dataList = dbHandler.getSpecificDataList(labels.get(0));//라벨에 들어간 값에 해당하는 월 다 꺼내오면 될듯

        for(int i=0;i<12;i++) {
            dataList = dbHandler.getSpecificDataList("20" + String.valueOf(labels.get(i)));
            Log.i("today string statistic2", labels.get(i));
            int totaldeposit = 0;
            int totalwitdraw = 0;
            for (MoneyData data : dataList) {

                if (data.isInput() == 0) {

                    totalwitdraw += data.getAmount();
                }
                else {

                    totaldeposit += data.getAmount();
                }
            }
            deposit.add(new Entry(i,totaldeposit));
            withdraw.add(new Entry(i,totalwitdraw));
        }


        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        LineDataSet depositDataSet = new LineDataSet(deposit, "입금");
        LineDataSet withdrawDataSet = new LineDataSet(withdraw, "출금");

        depositDataSet.setDrawCircles(true);
        depositDataSet.setColors(Color.BLUE);
        withdrawDataSet.setDrawCircles(true);
        withdrawDataSet.setColors(Color.RED);

        lineDataSets.add(depositDataSet);
        lineDataSets.add(withdrawDataSet);

        lineChart.setData(new LineData(lineDataSets));
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int)value);
            }
        });
        lineChart.animateY(1000);
        lineChart.invalidate();
    }
    private void drawCategoryStatistic() {
        String currentMonth = DateUtil.getToday().substring(0,8);
        Toast.makeText(getContext(), currentMonth, Toast.LENGTH_SHORT).show();
        dataList = dbHandler.getSpecificDataList(currentMonth);//라벨에 들어간 값에 해당하는 월 다 꺼내오면 될듯
        Log.i("current month ", String.valueOf(currentMonth));
        int sik =0;
        int cha = 0;
        int ki = 0;



        for(MoneyData data :dataList){
            Log.i("cat 여기 ", String.valueOf(dataList.size()));
            if(data.isInput() == 0) {
                if (data.getCategory().equals("식비")) {
                    sik += data.getAmount();
                    Log.i("cat sik ", String.valueOf(sik));
                } else if (data.getCategory().equals("차비")) {
                    cha += data.getAmount();
                    Log.i("cat cha ", String.valueOf(cha));
                } else if (data.getCategory().equals("기타")) {
                    ki += data.getAmount();
                    Log.i("cat ki ", String.valueOf(ki));
                }
            }
        }


        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(sik, "식비"));

        entries.add(new PieEntry(cha, "차비"));

        entries.add(new PieEntry(ki, "기타"));


        PieDataSet dataSet = new PieDataSet(entries, "이번달 카테고리별 사용 내역");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }
}
