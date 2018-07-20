package com.jackzheng.jizhang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jackzheng.jizhang.R;
import com.jackzheng.jizhang.data.AddUpData;
import com.jackzheng.jizhang.data.RecordData;
import com.jackzheng.jizhang.manager.DataManager;
import com.jackzheng.jizhang.manager.TimeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;

/**
 * Created by jackzheng on 2018/1/5.
 */

public class HistoryActivity extends Activity implements View.OnClickListener{
    private int mActivityType = 0;
    private int mShowDate = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histrory);
        Intent  intent = getIntent();
        mActivityType = intent.getIntExtra("type",0);
        mShowDate = intent.getIntExtra("date",0);
        initData();
        refreshData();
        refreshUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
        refreshUi();
    }

    private ImageView mDetailBack ,mDetailAdd;
    private TextView mDetailContext,mDetailAll,mDetailIn,mDetailOut,mDetailStrock;
    private ListView mDetailList;
    private void initData(){
        mDetailBack = (ImageView) findViewById(R.id.detail_back);
        mDetailBack.setOnClickListener(this);
        mDetailAdd = (ImageView) findViewById(R.id.detail_add);
        mDetailAdd.setOnClickListener(this);
        mDetailContext = (TextView)  findViewById(R.id.detail_context);
        mDetailAll = (TextView) findViewById(R.id.detail_all);
        mDetailIn = (TextView)  findViewById(R.id.detail_income);
        mDetailOut = (TextView)  findViewById(R.id.detail_out);
        mDetailStrock = (TextView) findViewById(R.id.detail_stock);
        mDetailList = (ListView)  findViewById(R.id.lv_history);
        mDetailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int date = 0;
                int type = 0;
                int time = 0;
                if(mActivityType == MainActivity.HISTORY_TYPE_TODAY){
                    date = mThisDate.get(position).getDate();
                    time = mThisDate.get(position).getTime();
                }else if(mActivityType == MainActivity.HISTORY_TYPE_THISMONTH){
                    date = mThisMonth.get(position).getDate();
                    type = MainActivity.HISTORY_TYPE_TODAY;
                }else if(mActivityType == MainActivity.HISTORY_TYPE_ALL){
                    date = mAllAddUp.get(position).getDate();
                    type = MainActivity.HISTORY_TYPE_TODAY;
                }else if(mActivityType == MainActivity.HISTORY_TYPE_CENSUS){

                }
                if(data != 0 && mActivityType != MainActivity.HISTORY_TYPE_TODAY){
                    Intent intent = new Intent(HistoryActivity.this , HistoryActivity.class);
                    intent.putExtra("date",date);
                    intent.putExtra("type",type);
                    startActivity(intent);
                }
            }
        });
    }
    private void refreshUi(){
        int date = TimeManager.getDate();
        if(mActivityType == MainActivity.HISTORY_TYPE_TODAY){
            if(mShowDate != 0){
                date = mShowDate;
            }
            mDetailContext.setText(""+date/10000+"年"+date%10000/100+"月"+date%100);
        }
        if(mActivityType == MainActivity.HISTORY_TYPE_THISMONTH){
            mDetailContext.setText(""+date/10000+"年"+date%10000/100+"月");
        }
        if(mActivityType == MainActivity.HISTORY_TYPE_ALL){
            mDetailContext.setText("全部数据");
        }
        if(mActivityType == MainActivity.HISTORY_TYPE_CENSUS){

        }
    }
    ArrayList<RecordData> mThisDate = null;
    ArrayList<AddUpData> mThisMonth = null;
    ArrayList<AddUpData> mAllAddUp = null;
    ArrayList<RecordData> mAllDate = null;
    private void refreshData(){
        List<Map<String,String>> showData = null;
        if(mActivityType == MainActivity.HISTORY_TYPE_TODAY){
           mThisDate = DataManager.getIntance(this).getDayRecord(mShowDate);
            showData = DataManager.getIntance(this).getHistoryItemDataList(mActivityType,mThisDate);
        }
        if(mActivityType == MainActivity.HISTORY_TYPE_THISMONTH){
            if(mShowDate ==0){
                mShowDate = TimeManager.getDate();
            }
            mThisMonth = DataManager.getIntance(this).getMonthList(mShowDate);
            showData = DataManager.getIntance(this).getHistoryItemDataList(mActivityType,mThisMonth);
        }
        if(mActivityType == MainActivity.HISTORY_TYPE_ALL){
            mAllAddUp = DataManager.getIntance(this).getAllAddUpp();
            showData = DataManager.getIntance(this).getHistoryItemDataList(mActivityType,mAllAddUp);
        }
        if(mActivityType == MainActivity.HISTORY_TYPE_CENSUS){
            mAllDate = DataManager.getIntance(this).getAllRecord();
        }
        SimpleAdapter adapter = new SimpleAdapter(this ,showData,R.layout.item_history,
                new String[]{"name","context","in","out"},
                new int[]{R.id.item_history_name,R.id.item_history_context,R.id.item_history_income,R.id.item_history_out});
        mDetailList.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.detail_back:
                break;
            case R.id.detail_add :
                break;
        }
    }
}
