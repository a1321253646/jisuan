package com.jackzheng.jizhang.sqlHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jackzheng.jizhang.data.RecordData;

import static com.jackzheng.jizhang.sqlHelper.TypeDb.KEY_TYPENAME;

/**
 * Created by jackzheng on 2017/12/31.
 */

public class DataDb  {
    public static final String TBL_DATA = "Data";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_TYPEID = "typeid";
    public static final String KEY_TYPECOUNT = "typecount";
    public static final String KEY_TYPEUNITPRICE = "typeunitprice";
    public static final String KEY_RECORDTYPE = "recordtype";
    public static final String KEY_RECORDIMAGE = "recordimage";
    public static final String KEY_RECORDDETAIL = "recorddetail";

    public static final String CREATE_DATA = "CREATE TABLE if not exists "+TBL_DATA+" ("
            + "id integer primary key autoincrement, "
            + KEY_DATE+" integer, "
            + KEY_TIME+" integer, "
            + KEY_TYPEID+" integer,"
            + KEY_TYPECOUNT+" integer,"
            + KEY_TYPEUNITPRICE+" integer,"
            + KEY_RECORDTYPE+" integer,"
            + KEY_RECORDIMAGE+" text,"
            + KEY_RECORDDETAIL+" text)";
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Context mContext;
    private static DataDb mIntance = null;
    public static synchronized DataDb getIntance(Context context){
        if(mIntance == null){
            mIntance = new DataDb(context);
        }
        return mIntance;
    }
    private DataDb(Context context) {
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
    public DataDb open() throws SQLException{
        this.mDb = this.mDBHelper.getWritableDatabase();
        return this;
    }
    public void  close(){
        this.mDBHelper.close();
    }
    public long insert(RecordData data) {
        long createResult = 0;
        ContentValues initialValues  = data.toContentValues();
        try {
            createResult = mDb.insert(TBL_DATA, null, initialValues);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return createResult;
    }
    public boolean deleteByDateAndTime(RecordData data){
        int isDelete = -1;
        isDelete = mDb.delete(TBL_DATA, KEY_TYPENAME + " = ? && "+
                KEY_TIME+" = ?",new String[]{data.getDate()+"",data.getTime()+""});
        return isDelete > 0;
    }
    public int updata(RecordData data){
        int value = -1;
        value = mDb.update(TBL_DATA,data.toContentValues(),
                KEY_TYPENAME + " = ? && "+KEY_TIME+" = ?",
                new String[]{data.getDate()+"",data.getTime()+""});
        return value;
    }
    public Cursor queryByDate(int date){
        Cursor cursor = null;
        cursor = mDb.query(TBL_DATA,null,KEY_DATE+" = ?",new String[]{date+""}
                ,null,null,null);
        return cursor;
    }
    public Cursor queryAll(){
        Cursor cursor = null;
        cursor = mDb.query(TBL_DATA,null,null,null,null,null,null);
        return cursor;
    }
    public Cursor queryByDates(int startDate,int endDates){
        Cursor cursor = null;
        cursor = mDb.query(TBL_DATA,null,"date >= ? && date <= ?",
                new String[]{startDate+"",endDates+""},null,null,null);
        return cursor;
    }
}
