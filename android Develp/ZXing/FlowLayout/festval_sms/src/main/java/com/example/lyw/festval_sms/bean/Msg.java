package com.example.lyw.festval_sms.bean;

/**
 * Created by LYW on 2016/11/2.
 */

public class Msg {
    private int id;
    private int festival_id;
    private String content;

    public Msg(int id, int festival_id, String content) {
        this.id = id;
        this.festival_id = festival_id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFestival_id() {
        return festival_id;
    }

    public void setFestival_id(int festival_id) {
        this.festival_id = festival_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
