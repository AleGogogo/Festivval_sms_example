package com.example.lyw.festval_sms;

import java.util.ArrayList;
import java.util.List;

import com.example.lyw.festval_sms.bean.Festival_Bean;
import com.example.lyw.festval_sms.bean.Msg;

/**
 * Created by LYW on 2016/11/2.
 */

public class FestivalLab {
    private static FestivalLab mInstance;
    private List<Festival_Bean> mDatas = new ArrayList<>();
    private List<Msg> msgs = new ArrayList<>();

    private FestivalLab(){
        mDatas.add(new Festival_Bean(1,"国庆节"));
        mDatas.add(new Festival_Bean(2,"中秋节"));
        mDatas.add(new Festival_Bean(3,"清明节"));
        mDatas.add(new Festival_Bean(4,"端午节"));
        mDatas.add(new Festival_Bean(5,"劳动节"));
        mDatas.add(new Festival_Bean(6,"重阳节"));
        mDatas.add(new Festival_Bean(7,"青年节"));
        mDatas.add(new Festival_Bean(8,"春 节"));
        mDatas.add(new Festival_Bean(9,"情人节"));

        msgs.add(new Msg(1,1,"你问我爱你有多深，我爱你有几分，你去想一想你去看一看,月亮代表我的心"));
        msgs.add(new Msg(2,1,"居然在分不清打底裤和秋裤的情况下，还能买到打底裤这我也是醉了，哈哈哈哈"));
        msgs.add(new Msg(1,1,"男生也是穿秋裤的，在最冷的时候也是会穿，哈哈哈，和你想象当中的不一样"));
        msgs.add(new Msg(4,1,"你问我爱你有多深，我爱你有几分，你去想一想你去看一看,月亮代表我的心"));
        msgs.add(new Msg(7,1,"你问我爱你有多深，我爱你有几分，你去想一想你去看一看,月亮代表我的心"));
        msgs.add(new Msg(1,1,"你问我爱你有多深，我爱你有几分，你去想一想你去看一看,月亮代表我的心"));
        msgs.add(new Msg(2,1,"你问我爱你有多深，我爱你有几分，你去想一想你去看一看,月亮代表我的心"));

    }
    public List<Festival_Bean> getFestivals(){
        return new ArrayList<Festival_Bean>(mDatas);
    }

    public List<Msg> getMsgs(){
        return new ArrayList<Msg>(msgs);
    }

    public List<Msg> findMsgByFestId(int id){
        List<Msg> data = new ArrayList<>();
        for (Msg message :
                msgs) {
            if (message.getFestival_id() == id)
                data.add(message);
        }
        return  data;
    }

    public Msg findMsgById(int id){
        for (Msg message :
                msgs) {
            if (message.getId() == id)
                return message;
        }
        return null;
    }
    public Festival_Bean findFestivalById(int id){
        for (Festival_Bean bean :
                mDatas) {
            if (bean.getId() == id){
                return  bean;
            }
        }
        return null;
    }
    public static FestivalLab getInstance(){
        if (mInstance == null){
            synchronized (FestivalLab.class){
                if (mInstance == null){
                    mInstance = new FestivalLab();
                }
            }
        }
        return mInstance;
    }

}
