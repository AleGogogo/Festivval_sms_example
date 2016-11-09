package com.example.lyw.festval_sms.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.lyw.festval_sms.R;
import com.example.lyw.festval_sms.bean.SendMsg;
import com.example.lyw.festval_sms.db.SmsProvider;
import com.example.lyw.festval_sms.view.FlowLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LYW on 2016/11/8.
 */

public class SmsHistoryFragment extends ListFragment {

    private LayoutInflater mLayoutInflater;
    private CursorAdapter mAdapter;

    private static final int LOADER_ID = 1;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mLayoutInflater = LayoutInflater.from(getActivity());
        initLoader();
        setupCursorAdapter();
    }

    private void setupCursorAdapter() {
      new CursorAdapter(getActivity(), null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup
                    parent) {
                View view = mLayoutInflater.inflate(R.layout.item_message,
                        parent, false);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView message = (TextView) view.findViewById(R.id
                        .id_item_history_sms);
                TextView date = (TextView) view.findViewById(R.id
                        .id_item_history_sms_date);
                TextView fest = (TextView) view.findViewById(R.id
                        .id_item_history_sms_festival);
                FlowLayout f = (FlowLayout) view.findViewById(R.id
                        .id_item_history_sms_flowlayout);

                message.setText(cursor.getString(cursor.getColumnIndex
                        (SendMsg.COLUNMS_MSG)));
                fest.setText(cursor.getString(cursor.getColumnIndex(SendMsg
                        .COLUNMS_FESTVIAL)));
                long dateTime = cursor.getLong(cursor.getColumnIndex(SendMsg
                        .COLUNMS_DATE));
                date.setText(parseDate(dateTime));
                String names = cursor.getString(cursor.getColumnIndex(SendMsg
                        .COLUNMS_NAME));
                if (TextUtils.isEmpty(names)){
                    return;
                }
                    f.removeAllViews();
                for (String name : names.split(":")){
                    addTag(name,f);
                }
            }
        };
        setListAdapter(mAdapter);
    }
private DateFormat df =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String parseDate(long dateTime) {

      return   df.format(dateTime);
    }

    private void addTag(String name, FlowLayout f) {
        TextView t = (TextView) mLayoutInflater.inflate(R.layout.tag,f,false);
        t.setText(name);
        f.addView(t);
    }


    private void initLoader() {
          getLoaderManager().initLoader(LOADER_ID, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {
                    @Override
                    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                        CursorLoader cursorLoader = new CursorLoader
                                (getActivity(),
                                        SmsProvider.URI_SMS_ALL, null, null,
                                        null,
                                        null);

                        return cursorLoader;
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> loader, Cursor
                            data) {
                        if (loader.getId() == LOADER_ID) {
                            //传入一个旧游标返回一个新游标
                            mAdapter.swapCursor(data);
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> loader) {
                        mAdapter.swapCursor(null);
                    }
                });
    }
}
