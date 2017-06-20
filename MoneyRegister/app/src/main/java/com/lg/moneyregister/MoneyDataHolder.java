package com.lg.moneyregister;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by LG on 2016-12-15.
 */

public class MoneyDataHolder  extends RecyclerView.ViewHolder{
    public TextView isInputTv, amountTv, categoryTv, dateTv;
    public MoneyDataHolder(View itemView) {
        super(itemView);
        isInputTv = (TextView) itemView.findViewById(R.id.isInputTv);
        amountTv = (TextView) itemView.findViewById(R.id.amountTv);
        categoryTv = (TextView) itemView.findViewById(R.id.categoryTv);
        dateTv = (TextView) itemView.findViewById(R.id.dateTv);
    }
}
