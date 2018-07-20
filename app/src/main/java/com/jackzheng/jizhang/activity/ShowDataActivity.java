package com.jackzheng.jizhang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jackzheng.jizhang.R;
import com.jackzheng.jizhang.adapter.HeadLIstViewAdapter;
import com.jackzheng.jizhang.data.RecordDataBean;
import com.jackzheng.jizhang.manager.NewDataManager;

import java.util.ArrayList;

/**
 * Created by jackzheng on 2018/2/18.
 */

public class ShowDataActivity extends Activity {
    ArrayList<String> mLeftStrings = new ArrayList<String>();
    ArrayList<String> mTopStrings = new ArrayList<String>();
    ArrayMap<Long, RecordDataBean> recordData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_showdata);
//        mLeftStrings.add("收虾价格");
//        mLeftStrings.add("收虾规格");
//        mLeftStrings.add("收虾数量");
//        mLeftStrings.add("运输费用");
//        mLeftStrings.add("加工费用");
//        mLeftStrings.add("冷冻费用");
//        mLeftStrings.add("其它费用");
//        mLeftStrings.add("正25单价");
//        mLeftStrings.add("次25单价");
//        mLeftStrings.add("规格差价");
//        mLeftStrings.add("次正价比");
//        mLeftStrings.add( "次品率");
        setContentView(R.layout.activity_show_tmp);
        initData();
        initView();
    }
    SyncScrollView mLeftScrollView,mDataScrollView;
    ListView mLeftListView,mTopListView;
    HorizontalListView mDataListView;

    private void initData() {
//        mLeftScrollView = (SyncScrollView)findViewById(R.id.header_horizontal);
//        mDataScrollView = (SyncScrollView)findViewById(R.id.data_horizontal);
//        mLeftListView = (ListView) findViewById(R.id.ls_lef);
        mTopListView = (ListView) findViewById(R.id.lv_list);
//        mDataListView = (HorizontalListView) findViewById(R.id.ls_top);
    }
    Object[] objects;
    private void initView(){
//        mLeftScrollView.setScrollView(mDataScrollView);
//        mDataScrollView.setScrollView(mLeftScrollView);
//        HeadLIstViewAdapter leftAdapter = new HeadLIstViewAdapter(this,mLeftStrings);
//        mLeftListView.setAdapter(leftAdapter);
//        ChengbenActivity.setListViewHeightBasedOnChildren(mLeftListView);
        recordData = NewDataManager.getIntance(this).getRecordData();
        Log.w("zsbin","recordData size= "+recordData.size());
        objects  = recordData.keySet().toArray();
        for(Object tmp: objects){
            long time = (long)tmp;
            mTopStrings.add((time/100000000)+"年"+
                    (time%100000000/1000000)+"月"+
                    (time%1000000/10000)+"日"+
                    (time%10000/100)+"："+
                    (time%100));
        }
        Log.w("zsbin","mTopStrings= "+mTopStrings.toString());
        HeadLIstViewAdapter topAdapter = new HeadLIstViewAdapter(this,mTopStrings);
        mTopListView.setAdapter(topAdapter);
        setListViewHeightBasedOnChildren(mTopListView);
        mTopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long object = (long) objects[position];
                Intent intent = new Intent(ShowDataActivity.this,ChengbenActivity2.class);
                intent.putExtra("time",object);
                startActivity(intent);
            }
        });

    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获得adapter
        HeadLIstViewAdapter adapter = (HeadLIstViewAdapter) listView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            //计算总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //计算分割线高度
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        //给listview设置高度
        listView.setLayoutParams(params);
    }
}
