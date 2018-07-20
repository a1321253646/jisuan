package com.jackzheng.jizhang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by jackzheng on 2017/12/31.
 */

public class MainActivity extends Activity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        setContentView(R.layout.activity_main);
//        Button button = (Button) findViewById(R.id.chengbeng_bt);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,ChengbenActivity.class);
//                startActivity(intent);
//            }
//        });
//       initUi();
        startActivity(new Intent(MainActivity.this,ChengbenActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        initView();
        super.onResume();
    }

    private TextView mTvMonth;
    private TextView mTvYear;
    private TextView mTvInCome;
    private TextView mTvOutCome;
    private TextView mTvStock;
    private Button mBtAdd;
    private ListView mLvList;
    private TextView mTvChengBen;
    private int mDay = -1;
    private void initUi() {
//        mTvMonth = (TextView) findViewById(R.id.text_history_mom);
//        mTvYear = (TextView) findViewById(R.id.text_history_year);
//        mTvInCome = (TextView) findViewById(R.id.text_history_income);
//        mTvOutCome = (TextView) findViewById(R.id.text_history_outcome);
//        mTvYear = (TextView) findViewById(R.id.text_history_outcome);
//        mTvStock = (TextView) findViewById(R.id.text_history_stock);
//        mBtAdd = (Button) findViewById(R.id.button);
//        mLvList = (ListView) findViewById(R.id.list_history);
//        mTvChengBen = (TextView) findViewById(R.id.chengbeng_bt);
//        mTvChengBen.setEnabled(true);
//        mTvChengBen.setClickable(true);
//        mTvChengBen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        mBtAdd.setEnabled(true);
//        mBtAdd.setClickable(true);
//        mBtAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,AddRecordActivity.class);
//                startActivity(intent);
//            }
//        });
//        mLvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
//                intent.putExtra("type",HISTORY_TYPE_ARRAY[position]);
//                startActivity(intent);
//            }
//        });
    }
    public static int HISTORY_TYPE_TODAY = 1;
    public static int HISTORY_TYPE_THISMONTH = 2;
    public static int HISTORY_TYPE_ALL = 3;
    public static int HISTORY_TYPE_CENSUS = 4;
    public static int[] HISTORY_TYPE_ARRAY  = {
            HISTORY_TYPE_TODAY,
            HISTORY_TYPE_THISMONTH,
            HISTORY_TYPE_ALL,
            HISTORY_TYPE_CENSUS
    };

    private void initView() {
//        mTvMonth.setText(TimeManager.getMonth());
//        mTvYear.setText(TimeManager.getYear());
//        CensusData thisMonth = DataManager.getIntance(getApplicationContext()).getThisMonth();
//        mTvInCome.setText(thisMonth.inCome);
//        mTvOutCome.setText(thisMonth.outCome);
//        mTvStock.setText(thisMonth.stock);
//        List list = DataManager.getIntance(this).getMainListViewShowData();
//        SimpleAdapter adapter = new SimpleAdapter(this ,list,R.layout.item_history,
//                new String[]{"name","context","in","out"},
//                new int[]{R.id.item_history_name,R.id.item_history_context,R.id.item_history_income,R.id.item_history_out});
//        mLvList.setAdapter(adapter);
    }
}
