package com.example.lyw.festval_sms;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import com.example.lyw.festval_sms.bean.SendMsg;
import com.example.lyw.festval_sms.db.SmsProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by LYW on 2016/11/5.
 */

public class SmsBiz {
    private Context context;
    public SmsBiz(Context context){
        this.context = context;
    }

    public int sendMsg(String number, SendMsg msg, PendingIntent sendPi,
                       PendingIntent deliverPi) {

        SmsManager smsManager = SmsManager.getDefault();
        //短信有可能很长，需要切分
        ArrayList<String> divideMessage = smsManager.divideMessage(msg.getMsg());
        for (String content :
                divideMessage) {
            smsManager.sendTextMessage(number, null, content, sendPi,
                    deliverPi);
        }
        return divideMessage.size();
    }

    public int sendMsg(Set<String> numbers, SendMsg msg, PendingIntent
            sendPi, PendingIntent deliverPi) {
        save(msg);
        int resultCount = 0;
        for (String number :
                numbers) {
            int count = sendMsg(number, msg, sendPi, deliverPi);
            resultCount += count;
        }
        return resultCount;
    }

    public void save(SendMsg message){
           message.setmDate(new Date());
        ContentValues values = new ContentValues();
        values.put(SendMsg.COLUNMS_DATE,message.getDatestr());
        values.put(SendMsg.COLUNMS_FESTVIAL,message.getFestvialName());
        values.put(SendMsg.COLUNMS_NUMBERS,message.getmNumber());
        values.put(SendMsg.COLUNMS_NAME,message.getNames());
        values.put(SendMsg.COLUNMS_MSG,message.getMsg());
        context.getContentResolver().insert(SmsProvider.URI_SMS_ALL,values);
    }
}
