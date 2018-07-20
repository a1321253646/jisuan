package com.jackzheng.jizhang.data;

import android.util.ArrayMap;

import java.util.ArrayList;

/**
 * Created by jackzheng on 2018/2/17.
 */

public class RecordDataBean {
    public ArrayList<SavceDataBean> badRecord;
    public ArrayList<SavceDataBean> goodRecord;
    public SavceDataBean inRecord;
    public int otherCost1 = 0;
    public int otherCost2 = 0;
    public float otherCost3 = 0;
    public float otherCost4 = 0;
    public float cha = 0;
    public int bili = 0;
    public long time =0;
    public static void addRecordDataBean(RecordDataBean bean,
                                         ArrayMap<Long,RecordDataBean>data){
        data.put(bean.time,bean);
    }
    public static void addRecordDataBean(ArrayList<SavceDataBean> list,
                                         ArrayMap<Long,RecordDataBean>data){
        for (SavceDataBean tmp:list){
            Long time =tmp.time;
            RecordDataBean recordDataBean = data.get(time);
            if(recordDataBean == null){
                recordDataBean = new RecordDataBean();
                recordDataBean.badRecord =new   ArrayList<SavceDataBean>();
                recordDataBean.goodRecord =new   ArrayList<SavceDataBean>();
                recordDataBean.inRecord = new SavceDataBean();
                data.put(time,recordDataBean);
            }
            if(tmp.dataType ==SavceDataBean.TYPE_GOOD){
                recordDataBean.goodRecord.add(tmp);
            }else if(tmp.dataType ==SavceDataBean.TYPE_BAD){
                recordDataBean.badRecord.add(tmp);
            }else if(tmp.dataType ==SavceDataBean.TYPE_IN){
                recordDataBean.inRecord = tmp;
            }else if(tmp.dataType ==SavceDataBean.TYPE_OTHER){
                recordDataBean.otherCost1 = tmp.level;
                recordDataBean.otherCost2 = tmp.count;
                recordDataBean.otherCost3 = tmp.price;
            }else if(tmp.dataType ==SavceDataBean.TYPE_OTHERR2){
                recordDataBean.otherCost4 = tmp.price;
                recordDataBean.bili = tmp.count;
                recordDataBean.cha = tmp.all;
            }
        }
    }
    public static ArrayMap<Long,RecordDataBean> getRecordDataBean(ArrayList<SavceDataBean> list){
        ArrayMap<Long,RecordDataBean> data = new ArrayMap<Long,RecordDataBean>();
        addRecordDataBean(list,data);
        return  data;
    }
}
