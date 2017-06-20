package com.lg.moneyregister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by LG on 2016-11-23.
 */

public class SettingFragment extends Fragment{
    private Button modifyBtn, logoutBtn;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LOGIN", MODE_PRIVATE);
        modifyBtn = (Button)view.findViewById(R.id.modifyBtn);
        logoutBtn = (Button)view.findViewById(R.id.logoutBtn);

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(getContext(), AutoLogoutService.class);
                getContext().stopService(serviceIntent);
                getContext().startService(serviceIntent);
                Intent intent = new Intent(getContext(), ModifyInfoActivity.class);
                startActivity(intent);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(getContext(), AutoLogoutService.class);
                getContext().stopService(serviceIntent);
                getContext().startService(serviceIntent);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin", false);
                editor.commit();
                getActivity().finishAffinity();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
