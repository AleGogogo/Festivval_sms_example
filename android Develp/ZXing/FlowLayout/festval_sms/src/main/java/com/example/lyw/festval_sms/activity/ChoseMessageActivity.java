package com.example.lyw.festval_sms.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lyw.festval_sms.FestivalLab;
import com.example.lyw.festval_sms.R;

import com.example.lyw.festval_sms.bean.Msg;

import static com.example.lyw.festval_sms.activity.FestivalCardFragment.ID_FESTIVAL;

public class ChoseMessageActivity extends AppCompatActivity {

    private ArrayAdapter<Msg> mAdapter;
    private ListView mListView;
    private FloatingActionButton mFabButon;
    private int mFestivaId;
    private LayoutInflater mLayoutInflater;
    private static final String TAG = "ChoseMessageActivity";
    public static final String ID_MSg = "id_msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_message);
        mFestivaId = getIntent().getIntExtra(ID_FESTIVAL, -1);
        initView();
        initEvent();
    }

    private void initEvent() {
        mFabButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessageActivity.toActivity(-1,mFestivaId,ChoseMessageActivity.this);
            }
        });
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.id_choosemessage_lv);
        mFabButon = (FloatingActionButton) findViewById(R.id
                .id_choosemessage_tosend_button);
        mLayoutInflater = LayoutInflater.from(this);
        mAdapter = new ArrayAdapter<Msg>(this, -1, FestivalLab.getInstance()
                .findMsgByFestId(mFestivaId)) {
            @NonNull
            @Override
            public View getView(final int position, View convertView, ViewGroup
                    parent) {
                if (convertView == null) {
                    Log.d(TAG, "getView: 到这里了么？");
                    convertView = mLayoutInflater.inflate(R.layout.item_message,
                            parent, false);
                    TextView tv = (TextView) convertView.findViewById(R.id
                            .id_item_sendmessage_tv);
                    Button button = (Button) convertView.findViewById(R.id
                            .id_sendmessage_button);
                    tv.setText(getItem(position).getContent());
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SendMessageActivity.toActivity(getItem(position)
                                    .getId(),mFestivaId,ChoseMessageActivity.this);

                        }
                    });
                }
                return convertView;
            }
        };
        mListView.setAdapter(mAdapter);
    }
}
