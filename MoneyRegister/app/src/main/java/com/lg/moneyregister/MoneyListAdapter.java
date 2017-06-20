package com.lg.moneyregister;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by LG on 2016-12-15.
 */

public class MoneyListAdapter extends RecyclerView.Adapter<MoneyDataHolder>  {
    private List<MoneyData> dataList;

    public MoneyListAdapter(List<MoneyData> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MoneyDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MoneyDataHolder(view);
    }

    @Override
    public void onBindViewHolder(MoneyDataHolder holder, int position) {
        if(dataList.get(position).isInput() == 1) {
            holder.isInputTv.setText("입금");
            holder.isInputTv.setTextColor(Color.BLUE);
        }
        else{
            holder.isInputTv.setText("출금");
            holder.isInputTv.setTextColor(Color.RED);
        }
        holder.amountTv.setText(String.valueOf((int)dataList.get(position).getAmount())+"원");
        holder.categoryTv.setText(dataList.get(position).getCategory());
        holder.dateTv.setText(dataList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return (dataList.size() > 0) ? dataList.size() : 0;
    }
}
