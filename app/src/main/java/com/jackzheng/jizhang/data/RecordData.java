package com.jackzheng.jizhang.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.jackzheng.jizhang.sqlHelper.DataDb;

import java.util.ArrayList;

/**
 * Created by jackzheng on 2017/12/31.
 */

public class RecordData {

    private int date = 0;
    private int time = 0;
    private int typeCount = 0;
    private int typeId = 0;
    private int price = 0;
    private int recordType = 0;
    private String recordImage = null;
    private String recordText = null;

    public RecordData(int date, int time,int typeId, int typeCount, int price,
                      int recordType, String recordImage, String recordText) {
        this.date = date;
        this.time = time;
        this.typeCount = typeCount;
        this.price = price;
        this.recordType = recordType;
        this.recordImage = recordImage;
        this.recordText = recordText;
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(int typeCount) {
        this.typeCount = typeCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public String getRecordImage() {
        return recordImage;
    }

    public void setRecordImage(String recordImage) {
        this.recordImage = recordImage;
    }

    public String getRecordText() {
        return recordText;
    }

    public void setRecordText(String recordText) {
        this.recordText = recordText;
    }
    public ContentValues toContentValues(){
        ContentValues value = new ContentValues();
        value.put(DataDb.KEY_DATE,date);
        value.put(DataDb.KEY_RECORDDETAIL,recordText);
        value.put(DataDb.KEY_RECORDIMAGE,recordImage);
        value.put(DataDb.KEY_RECORDTYPE,recordType);
        value.put(DataDb.KEY_TIME,time);
        value.put(DataDb.KEY_TYPECOUNT,typeCount);
        value.put(DataDb.KEY_TYPEID,typeId);
        value.put(DataDb.KEY_TYPEUNITPRICE,price);
        return value;
    }
    public static ArrayList<RecordData> cursorToArray(Cursor cursor){
        ArrayList<RecordData> list = new ArrayList<RecordData>();
        cursor.moveToFirst();
        int date = -1;
        int time = -1;
        int typeId = -1;
        int typeCount = -1;
        int price = -1;
        int recordtype = -1;
        String recordImage = null;
        String recordText = null;
        while(!cursor.isAfterLast()){
            date = cursor.getInt(cursor.getColumnIndex(DataDb.KEY_DATE));
            time = cursor.getInt(cursor.getColumnIndex(DataDb.KEY_TIME));
            typeId = cursor.getInt(cursor.getColumnIndex(DataDb.KEY_TYPEID));
            typeCount = cursor.getInt(cursor.getColumnIndex(DataDb.KEY_TYPECOUNT));
            price = cursor.getInt(cursor.getColumnIndex(DataDb.KEY_TYPEUNITPRICE));
            recordtype = cursor.getInt(cursor.getColumnIndex(DataDb.KEY_RECORDTYPE));
            recordImage = cursor.getString(cursor.getColumnIndex(DataDb.KEY_RECORDIMAGE));
            recordText = cursor.getString(cursor.getColumnIndex(DataDb.KEY_RECORDDETAIL));
            RecordData data = new RecordData(date,time,typeId,typeCount,price,recordtype
                ,recordImage,recordText);
            list.add(data);
        }
        return list;
    }
}
