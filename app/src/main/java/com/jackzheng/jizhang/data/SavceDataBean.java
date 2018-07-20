package com.jackzheng.jizhang.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.jackzheng.jizhang.sqlHelper.SaveDateDb;

import java.util.ArrayList;

/**
 * Created by jackzheng on 2018/2/17.
 */

public class SavceDataBean {
    public static int TYPE_GOOD = 1;
    public static int TYPE_BAD = 2;
    public static int TYPE_OTHER = 3;
    public static int TYPE_IN= 4;
    public static int TYPE_OTHERR2= 5;

    public int dataType =0;
    public int level = 0;
    public int count = 0;
    public float price = 0;
    public float all = 0;
    public long time = 0;
    public SavceDataBean(){

    }
    public SavceDataBean(int dataType, int level, int count, float price, float all, long time) {
        this.dataType = dataType;
        this.level = level;
        this.count = count;
        this.price = price;
        this.all = all;
        this.time = time;
    }

    public ContentValues toContentValues(){
        ContentValues value = new ContentValues();
        value.put(SaveDateDb.KEY_TYPE,dataType);
        value.put(SaveDateDb.KEY_LEVEL,level);
        value.put(SaveDateDb.KEY_PRICE,price);
        value.put(SaveDateDb.KEY_ALL,all);
        value.put(SaveDateDb.KEY_TIME,time);
        value.put(SaveDateDb.KEY_COUNT,count);
        Log.d("zsbin",value.toString());
        return value;
    }
    public static ArrayList<SavceDataBean> cursorToArray(Cursor cursor){
        ArrayList<SavceDataBean> list = new ArrayList<SavceDataBean>();
        cursor.moveToFirst();
        int dataType =0;
        int level = 0;
        int count = 0;
        float price = 0;
        float all = 0;
        long time = 0;
        while (cursor.moveToNext()){
            dataType = cursor.getInt(cursor.getColumnIndex(SaveDateDb.KEY_TYPE));
            level = cursor.getInt(cursor.getColumnIndex(SaveDateDb.KEY_LEVEL));
            count = cursor.getInt(cursor.getColumnIndex(SaveDateDb.KEY_COUNT));
            price = cursor.getFloat(cursor.getColumnIndex(SaveDateDb.KEY_PRICE));
            all = cursor.getFloat(cursor.getColumnIndex(SaveDateDb.KEY_ALL));
            time = cursor.getLong(cursor.getColumnIndex(SaveDateDb.KEY_TIME));
            SavceDataBean data = new SavceDataBean(dataType,level,count,price,all,time);
            list.add(data);
        }
        return list;
    }
}
