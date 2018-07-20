package com.jackzheng.jizhang.manager;

import android.content.Context;
import android.database.Cursor;
import android.util.ArrayMap;

import com.jackzheng.jizhang.data.RecordDataBean;
import com.jackzheng.jizhang.data.SavceDataBean;
import com.jackzheng.jizhang.sqlHelper.CommDb;
import com.jackzheng.jizhang.sqlHelper.SaveDateDb;

import java.util.ArrayList;

/**
 * Created by jackzheng on 2018/2/17.
 */

public class NewDataManager {
    private static NewDataManager mInstance ;
    private SaveDateDb mDateDb ;
    private CommDb mCommDb;
    private ArrayMap<Long,RecordDataBean> mRecordData;
    public static synchronized NewDataManager getIntance(Context context){
        if(mInstance ==null){
            mInstance = new NewDataManager(context);
        }
        return mInstance;
    }
    private NewDataManager(Context context){
        mDateDb = SaveDateDb.getIntance(context).open();
        mCommDb = CommDb.getIntance(context).open();
        initData(context);
    }

    private void initData(Context context) {
        Cursor cursor = mDateDb.queryAll();
        ArrayList<SavceDataBean> savceDataBeen = SavceDataBean.cursorToArray(cursor);
        mRecordData = RecordDataBean.getRecordDataBean(savceDataBeen);
    }
    public void addRecordData(RecordDataBean data){
        mDateDb.insert(data);
        RecordDataBean.addRecordDataBean(data,mRecordData);
    }
    public ArrayMap<Long,RecordDataBean> getRecordData(){
        return mRecordData;
    }
}
