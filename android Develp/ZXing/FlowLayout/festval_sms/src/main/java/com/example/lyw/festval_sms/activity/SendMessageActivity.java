package com.example.lyw.festval_sms.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyw.festval_sms.FestivalLab;
import com.example.lyw.festval_sms.R;

import java.util.Date;
import java.util.HashSet;

import com.example.lyw.festval_sms.SmsBiz;
import com.example.lyw.festval_sms.bean.Festival_Bean;
import com.example.lyw.festval_sms.bean.Msg;
import com.example.lyw.festval_sms.bean.SendMsg;
import com.example.lyw.festval_sms.view.FlowLayout;

import static com.example.lyw.festval_sms.activity.FestivalCardFragment.ID_FESTIVAL;

public class SendMessageActivity extends AppCompatActivity {
    private static final int CODE_REQUEST = 1;
    private int festivalId;
    private int msgId;
    private Msg mMsg;
    private Festival_Bean bean;
    public static final String ID_MSg = "id_msg";
    private EditText mEditText;
    private Button mBtnAdd;
    private FloatingActionButton mFloatButton;
    private FlowLayout mFlowContacts;
    private View mLoading;
    private static final String TAG = "SendMessageActivity";
    private HashSet<String> mContactNames = new HashSet<>();
    private HashSet<String> mNumber = new HashSet<>();
    public static final String ACTION_SEND = "ACTION_SEND_MSG";
    public static final String ACTION_DELIVER = "ACTION_DELIVER_MSG";
    private PendingIntent sendPi;
    private PendingIntent deliverPi;
    private BroadcastReceiver sendBroadcast;
    private BroadcastReceiver deliverBroadcast;
    private LayoutInflater mInflater;
    private SmsBiz smsBiz ;
    private int msgcout = 0;
    private int cCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        smsBiz = new SmsBiz(this);
        initData();
        initView();
        initEvent();
        initReciver();
    }

    private void initReciver() {
        //通过intent构造pending
        Intent sendIntent = new Intent(ACTION_SEND);
        sendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
        Intent deliverIntent = new Intent(ACTION_DELIVER);
        deliverPi = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);
        //注册广播
        registerReceiver(sendBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == RESULT_OK) {
                    Log.d(TAG, "短信发送成功！耶！");
                    msgcout++;
                } else {
                    Log.d(TAG, "短信发送失败！");
                }
                if (msgcout == cCount){
                    Log.d(TAG, "短信发送完毕");
                    finish();
                }
            }
        }, new IntentFilter(ACTION_SEND));

        registerReceiver(deliverBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == RESULT_OK) {
                    Log.d(TAG, "联系人短信接收成功！耶！");
                } else {
                    Log.d(TAG, "短信接收失败！");
                }
            }
        }, new IntentFilter(ACTION_DELIVER));
    }

    private void initEvent() {
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用intent的方式启动系统通讯录App
                Intent intent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_REQUEST);

            }
        });
        mFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNumber.size() == 0) {
                    Toast.makeText(SendMessageActivity.this, "请选择联系人", Toast
                            .LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mEditText.getText().toString())) {
                    Toast.makeText(SendMessageActivity.this, "短信不能为空", Toast
                            .LENGTH_SHORT).show();
                    return;
                }
                mLoading.setVisibility(View.VISIBLE);
                String mMsg = mEditText.getText().toString();
                smsBiz.sendMsg(mNumber, buildSendMsg(mMsg),
                        sendPi, deliverPi);
            }
        });
    }

    private SendMsg buildSendMsg(String mMsg) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setMsg(mMsg);
        sendMsg.setmDate(new Date());
        sendMsg.setFestvialName(bean.getName());
        String names = "";
        for (String name :mContactNames){
            names +=name + ":";
        }
        String numbers = "";
        for (String number :mNumber) {
            numbers += number + ":";
        }
        sendMsg.setmNumber(numbers.substring(0,numbers.length()-1));
        sendMsg.setNames(names.substring(0,names.length()-1));
        return sendMsg;
    }


    private void initData() {
        mInflater = LayoutInflater.from(this);
        msgId = getIntent().getIntExtra(ID_MSg, -1);
        festivalId = getIntent().getIntExtra(ID_FESTIVAL, -1);
        bean = FestivalLab.getInstance().findFestivalById(festivalId);
        setTitle(bean.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUEST) {

            if (resultCode == RESULT_OK) {
                //1、取出从系统通讯录访问得到的uri
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null,
                        null, null, null);
                cursor.moveToFirst();
                //2.得到所有联系人的名字
                String contactName = cursor.getString(cursor.getColumnIndex
                        (ContactsContract.Contacts.DISPLAY_NAME));
                mContactNames.add(contactName);
                //3.得到联系人的电话号码
                String number = getContacNumber(cursor);
                if (!TextUtils.isEmpty(number)) {
                    mContactNames.add(contactName);
                    mNumber.add(number);
                    addTag(contactName);
                }
            }
        }

    }

    private void addTag(String contactName) {
        TextView textview = (TextView) mInflater.inflate(R.layout.tag,
                mFlowContacts, false);
        textview.setText(contactName);
        mFlowContacts.addView(textview);
    }

    private String getContacNumber(Cursor cursor) {
        //HAS_PHONE_NUMBER  An indicator of whether this contact has at least
        // one phone number.
        // "1" if there is
        //* at least one phone number, "0" otherwise.
        int numberCount = cursor.getInt(cursor.getColumnIndex
                (ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String phoneNumber = null;
        if (numberCount > 0) {
            //_ID stands for The unique ID for a row.
            int cursorId = cursor.getInt(cursor.getColumnIndex
                    (ContactsContract.Contacts._ID));
            Cursor query = getContentResolver().query(ContactsContract
                    .CommonDataKinds.Phone
                    .CONTENT_URI, null, ContactsContract.CommonDataKinds
                    .Phone.CONTACT_ID + " =" + cursorId, null, null);
            query.moveToFirst();
            phoneNumber = query.getString(query.getColumnIndex
                    (ContactsContract.CommonDataKinds.Phone.NUMBER));
            query.close();
        }
        return phoneNumber;
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.id_send_edt);
        mBtnAdd = (Button) findViewById(R.id.id_send_but);
        mFlowContacts = (FlowLayout) findViewById(R.id.id_send_flowlayout);
        mFloatButton = (FloatingActionButton) findViewById(R.id
                .id_floatingactionbar);
        mLoading = findViewById(R.id.id_layout_loading_framelayout);
        mLoading.setVisibility(View.INVISIBLE);
        if (msgId != -1) {
            mMsg = FestivalLab.getInstance().findMsgById(msgId);
            mEditText.setText(mMsg.getContent());
        }
    }

    /**
     * 为啥要这么写
     *
     * @param id
     * @param festivalId
     * @param context
     */
    public static void toActivity(int id, int festivalId, Context context) {
        Log.d(TAG, "toActivity: I,m Comimg~");
        Intent intent = new Intent(context, SendMessageActivity.class);
        intent.putExtra(ID_MSg, id);
        intent.putExtra(ID_FESTIVAL, festivalId);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sendBroadcast);
        unregisterReceiver(deliverBroadcast);
    }
}
