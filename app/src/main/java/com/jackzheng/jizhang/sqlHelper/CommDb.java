package com.jackzheng.jizhang.sqlHelper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by jackzheng on 2017/12/31.
 */

public class CommDb {
    public static final String DB_NAME = "coll.db";

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Context mContext;
    private static CommDb mIntance = null;
    public static synchronized CommDb getIntance(Context context){
        if(mIntance == null){
            mIntance = new CommDb(context);
        }
        return mIntance;
    }
    private CommDb(Context context) {
        this.mContext = context;
        this.mDBHelper = new DataBaseHelper(mContext);
    }


    private static class DataBaseHelper extends SQLiteOpenHelper{
        DataBaseHelper(Context context){
            super(context,DB_NAME,null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
//            db.execSQL(AddUpDb.CREATE_ADDUP);
//            db.execSQL(DataDb.CREATE_DATA);
//            db.execSQL(TypeDb.CREATE_TYPE);
            db.execSQL(SaveDateDb.CREATE_TYPE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public CommDb open() throws SQLException{
        this.mDb = this.mDBHelper.getWritableDatabase();
        return this;
    }
    public void  close(){
        this.mDBHelper.close();
    }
}
