package com.example.lyw.festval_sms.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LYW on 2016/11/7.
 */

public class SendMsg {
    private int id;
    private Date mDate;
    private String names;
    private String mNumber;
    private String festvialName;
    private String datestr;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final String COLUNMS_DATE = "date";
    public static final String COLUNMS_NAME = "names";
    public static final String COLUNMS_NUMBERS = "numbers";
    public static final String COLUNMS_FESTVIAL = "festival_name";
    public static final String COLUNMS_ID = "id";
    public static final String TABLE_NAME = "table_sms";
    public static final String COLUNMS_MSG = "id";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getFestvialName() {
        return festvialName;
    }

    public void setFestvialName(String festvialName) {
        this.festvialName = festvialName;
    }

    public String getDatestr() {
        datestr = df.format(mDate);
        return datestr;
    }


}
