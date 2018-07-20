package com.jackzheng.jizhang.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jackzheng on 2018/1/4.
 */

public class SharepreferenceManager {
    public static SharepreferenceManager mIntance =null;
    private SharedPreferences sharedPreferences = null;
    private static Context mContext=null;
    public static final String LAST_TYPE_ID = "last_type_id";
    private SharepreferenceManager(Context context){
       sharedPreferences = context.getSharedPreferences("wujay", Context.MODE_PRIVATE);
    }
    public static synchronized SharepreferenceManager getIntance(Context context){
        if(mIntance == null){
            mIntance =new SharepreferenceManager(context);
        }
        return mIntance;
    }
    public int addNewTypeId(){
        int id = sharedPreferences.getInt(LAST_TYPE_ID,0);
        id++;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(LAST_TYPE_ID,id);
        edit.commit();
        return id;
    }

}
