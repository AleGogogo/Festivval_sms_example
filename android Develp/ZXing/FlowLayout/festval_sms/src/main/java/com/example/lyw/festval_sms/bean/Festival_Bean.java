package com.example.lyw.festval_sms.bean;

import java.util.Date;

/**
 * Created by LYW on 2016/11/2.
 */

public class Festival_Bean {
    private int id;
    private String  name;
    private Date date;
    private String des_name;

    public Festival_Bean(int id,String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDes_name() {
        return des_name;
    }

    public void setDes_name(String des_name) {
        this.des_name = des_name;
    }
}
