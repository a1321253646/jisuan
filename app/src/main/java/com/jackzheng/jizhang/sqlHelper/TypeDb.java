package com.jackzheng.jizhang.sqlHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jackzheng.jizhang.data.TypeData;

/**
 * Created by jackzheng on 2017/12/31.
 */

public class TypeDb  {
    public static final String TBL_TYPE = "Type";
    public static final String KEY_TYPEID = "typeid";
    public static final String KEY_TYPENAME = "typename";
    public static final String KEY_UNITPRICE = "unitpirce";
    public static final String KEY_RECORDTYPE = "recordtype";
    public static final String CREATE_TYPE = "CREATE TABLE if not exists "+TBL_TYPE+" ("
            + "id integer primary key autoincrement, "
            + KEY_TYPEID+" integer, "
            + KEY_TYPENAME+" text, "
            + KEY_RECORDTYPE+" integer, "
            + KEY_UNITPRICE+" integer)";

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Context mContext;
    private static TypeDb mIntance = null;
    public static synchronized TypeDb getIntance(Context context){
        if(mIntance == null){
            mIntance = new TypeDb(context);
        }
        return mIntance;
    }
    private TypeDb(Context context) {
        this.mContext = context;
        this.mDBHelper = new DataBaseHelper(mContext);
    }
    private static class DataBaseHelper extends SQLiteOpenHelper {
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
    public TypeDb open() throws SQLException{
        this.mDb = this.mDBHelper.getWritableDatabase();
        return this;
    }
    public void  close(){
        this.mDBHelper.close();
    }
    public long insert(TypeData data) {
        long createResult = 0;
        ContentValues initialValues  = data.toContentValues();
        try {
            createResult = mDb.insert(TBL_TYPE, null, initialValues);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return createResult;
    }
    public boolean deleteByDate(String[] name){
        int isDelete = -1;
        isDelete = mDb.delete(TBL_TYPE, KEY_TYPENAME + "=?", name);
        return isDelete > 0;
    }
    public int updata(TypeData data){
        int value = -1;
        value = mDb.update(TBL_TYPE,data.toContentValues(),
                KEY_TYPEID+" = ?",new String[]{data.getTypeId()+""});
        return value;
    }
    public Cursor query(int recordType){
        Cursor cursor = null;
        cursor = mDb.query(TBL_TYPE,null,KEY_RECORDTYPE+" = ?",new String[]{recordType+""}
                ,null,null,null);
        return cursor;
    }
    public Cursor queryAll(){
        Cursor cursor = null;
        cursor = mDb.query(TBL_TYPE,null,null,null
                ,null,null,null);
        return cursor;
    }
}
