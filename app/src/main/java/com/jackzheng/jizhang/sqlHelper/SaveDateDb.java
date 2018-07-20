package com.jackzheng.jizhang.sqlHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jackzheng.jizhang.data.RecordDataBean;
import com.jackzheng.jizhang.data.SavceDataBean;
import com.jackzheng.jizhang.data.TypeData;

/**
 * Created by jackzheng on 2018/2/17.
 */

public class SaveDateDb {
    public static final String TBL_TYPE = "Type";
    public static final String KEY_TYPE = "type";
    public static final String KEY_LEVEL = "leve";
    public static final String KEY_COUNT= "count";
    public static final String KEY_PRICE = "price";
    public static final String KEY_ALL = "allcost";
    public static final String KEY_TIME = "time";
    public static final String CREATE_TYPE = "CREATE TABLE if not exists "+TBL_TYPE+" ("
            + "id integer primary key autoincrement, "
            + KEY_TYPE+" integer, "
            + KEY_LEVEL+" integer, "
            + KEY_PRICE+" real, "
            + KEY_ALL+" real, "
            +KEY_TIME+" integer, "
            + KEY_COUNT+" integer)";

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Context mContext;
    private static SaveDateDb mIntance = null;
    public static synchronized SaveDateDb getIntance(Context context){
        if(mIntance == null){
            mIntance = new SaveDateDb(context);
        }
        return mIntance;
    }
    private SaveDateDb(Context context) {
        this.mContext = context;
        this.mDBHelper = new DataBaseHelper(mContext);
    }
    private static class DataBaseHelper extends SQLiteOpenHelper {
        DataBaseHelper(Context context){
            super(context,CommDb.DB_NAME,null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TYPE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    public SaveDateDb open() throws SQLException {
        this.mDb = this.mDBHelper.getWritableDatabase();
        return this;
    }
    public void  close(){
        this.mDBHelper.close();
    }
    public long insert(RecordDataBean data) {
        long createResult = 0;
        try {
            ContentValues initialValues;
            Log.w("zsbin","data.goodRecord.size= "+data.goodRecord.size());
            for (SavceDataBean tmp: data.goodRecord){
                initialValues = tmp.toContentValues();
                createResult = mDb.insert(TBL_TYPE, null,initialValues);
            }
            Log.w("zsbin","data.badRecord.size= "+data.badRecord.size());
            for (SavceDataBean tmp: data.badRecord){
                initialValues = tmp.toContentValues();
                createResult = mDb.insert(TBL_TYPE, null,initialValues);
            }
            initialValues = data.inRecord.toContentValues();
            createResult = mDb.insert(TBL_TYPE, null,initialValues);
            SavceDataBean other = new SavceDataBean();
            other.dataType = SavceDataBean.TYPE_OTHER;
            other.level = data.otherCost1;
            other.price = data.otherCost3;
            other.count = data.otherCost2;
            initialValues =other.toContentValues();
            createResult = mDb.insert(TBL_TYPE, null,initialValues);
            SavceDataBean other2 = new SavceDataBean();
            other2.dataType = SavceDataBean.TYPE_OTHERR2;
            other2.price = data.otherCost4;
            other2.count = data.bili;
            other2.all = data.cha;
            initialValues =other2.toContentValues();
            createResult = mDb.insert(TBL_TYPE, null,initialValues);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return createResult;
    }
    public boolean deleteByDate(int time){
        int isDelete = -1;
        isDelete = mDb.delete(TBL_TYPE, KEY_TIME + "=?", new String[]{time+""});
        return isDelete > 0;
    }
    public int updata(TypeData data){
        int value = -1;
//        value = mDb.update(TBL_TYPE,data.toContentValues(),
//                KEY_TYPEID+" = ?",new String[]{data.getTypeId()+""});
        return value;
    }
    public Cursor query(int time){
        Cursor cursor = null;
        cursor = mDb.query(TBL_TYPE,null,KEY_TIME+" = ?",new String[]{time+""}
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
