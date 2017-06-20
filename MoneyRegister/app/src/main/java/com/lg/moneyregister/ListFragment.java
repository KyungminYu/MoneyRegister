package com.lg.moneyregister;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-11-23.
 */

public class ListFragment extends Fragment {
    private List<MoneyData> moneyDataList = new ArrayList<>();
    private RecyclerView dataListView;
    private RecyclerView.LayoutManager moneyListLayoutManager;
    private MoneyListAdapter listAdapter;
    private DataBaseHandler dbHandler;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        dbHandler = MainActivity.getDBHandler();
        dataListView = (RecyclerView)view.findViewById(R.id.List);

        initializationData();
        initializationListView();

        return view;
    }


    private void initializationData() {
        List<MoneyData> moneyDatas = dbHandler.getDataList();
        this.moneyDataList = moneyDatas;
    }
    private void initializationListView() {
        moneyListLayoutManager = new LinearLayoutManager(getContext());
        dataListView.setLayoutManager(moneyListLayoutManager);
        if(moneyDataList.size() != 0){
            listAdapter = new MoneyListAdapter(moneyDataList);
            dataListView.setAdapter(listAdapter);
        }
    }
}
