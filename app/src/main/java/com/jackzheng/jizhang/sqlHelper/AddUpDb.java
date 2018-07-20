package com.jackzheng.jizhang.sqlHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jackzheng.jizhang.data.AddUpData;

/**
 * Created by jackzheng on 2017/12/31.
 */

public class AddUpDb{
    public static final String TBL_ADDUP = "AddUp";
    public static final String KEY_DATE = "date";
    public static final String KEY_INCOME = "income";
    public static final String KEY_OUTCOME = "outcome";
    public static final String KEY_STOCK = "stock";
    public static final String KEY_BALANCE = "blance";
    public static final String KEY_STOCKBALANCE = "stock_balance";

    public static final String CREATE_ADDUP = "CREATE TABLE if not exists "+TBL_ADDUP+" ("
            + "id integer primary key autoincrement, "
            + KEY_DATE+" integer, "
            + KEY_INCOME+" integer, "
            + KEY_OUTCOME+" integer, "
            + KEY_STOCK+" integer,"
            + KEY_BALANCE+" integer,"
            +KEY_STOCKBALANCE +" stock_balance)";
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Context mContext;
    private static AddUpDb mIntance = null;
    public static synchronized AddUpDb getIntance(Context context){
        if(mIntance == null){
            mIntance = new AddUpDb(context);
        }
        return mIntance;
    }
    private AddUpDb(Context context) {
        this.mContext = context;
        this.mDBHelper = new DataBaseHelper(mContext);
    }
    private static class DataBaseHelper extends SQLiteOpenHelper{
        DataBaseHelper(Context context){
            super(context,CommDb.DB_NAME,null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    public AddUpDb open() throws SQLException{
        this.mDb = this.mDBHelper.getWritableDatabase();
        return this;
    }
    public void  close(){
        this.mDBHelper.close();
    }
    public long insert(AddUpData data) {
        long createResult = 0;
        ContentValues initialValues  = data.toContentValues();
        try {
            createResult = mDb.insert(TBL_ADDUP, null, initialValues);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return createResult;
    }
    public boolean deleteByDate(String[] date){
        int isDelete = -1;
        isDelete = mDb.delete(TBL_ADDUP, KEY_DATE + "=?", date);
        return isDelete > 0;
    }
    public int updata(AddUpData data){
        int value = -1;
        value = mDb.update(TBL_ADDUP,data.toContentValues(),
                "date = ?",new String[]{data.getDate()+""});
        return value;
    }
    public Cursor queryDays(int startDay,int endDay){
        Cursor cursor = null;
        cursor = mDb.query(TBL_ADDUP,null,"date >= ? && date <= ?",new String[]{startDay+"",endDay+""}
                ,null,null,null);
        return cursor;
    }
    public Cursor queryDay(int date){
        Cursor cursor = null;
        cursor = mDb.query(TBL_ADDUP,null,"date = ?",new String[]{date+""}
                ,null,null,null);
        return cursor;
    }
    public Cursor queryAll(){
        Cursor cursor = null;
        cursor = mDb.query(TBL_ADDUP,null,null,null
                ,null,null,null);
        return cursor;
    }
}
