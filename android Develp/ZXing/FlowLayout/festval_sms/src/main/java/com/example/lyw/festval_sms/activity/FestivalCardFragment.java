package com.example.lyw.festval_sms.activity;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.lyw.festval_sms.FestivalLab;
import com.example.lyw.festval_sms.R;
import com.example.lyw.festval_sms.activity.ChoseMessageActivity;

import com.example.lyw.festval_sms.bean.Festival_Bean;

/**
 * Created by LYW on 2016/11/2.
 */

public class FestivalCardFragment extends Fragment {

    private GridView mGridView;
    private LayoutInflater mInflater;
    private ArrayAdapter<Festival_Bean> mAdapter;
    public static final String ID_FESTIVAL = "festival_id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_festival_category,
                container, false);
    }

    //被上一个方法触发得到的
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());
        mGridView = (GridView) view.findViewById(R.id.id_gv);
        mAdapter = new ArrayAdapter<Festival_Bean>(getActivity(), -1,
                FestivalLab.getInstance().getFestivals()) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup
                    parent) {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_festival, parent, false);
                }
                TextView tv = (TextView) convertView.findViewById(R.id.id_item_tv);
                tv.setText(getItem(position).getName());
                return convertView;
            }


        };
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long id) {
                Intent intent = new Intent(getActivity(), ChoseMessageActivity.class);
                intent.putExtra(ID_FESTIVAL, mAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
    }
}
