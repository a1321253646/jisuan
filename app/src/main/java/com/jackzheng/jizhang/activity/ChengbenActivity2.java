package com.jackzheng.jizhang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jackzheng.jizhang.R;
import com.jackzheng.jizhang.adapter.MainListViewAdapter;
import com.jackzheng.jizhang.adapter.MainListViewAdapter.AddLister;
import com.jackzheng.jizhang.data.ChengBenData;
import com.jackzheng.jizhang.data.RecordDataBean;
import com.jackzheng.jizhang.data.SavceDataBean;
import com.jackzheng.jizhang.manager.NewDataManager;
import com.jackzheng.jizhang.manager.TimeManager;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by jackzheng on 2018/1/7.
 */

public class ChengbenActivity2 extends Activity implements  AddLister{
    long time= -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent()!=null &&  getIntent().getExtras()!= null){
            time= (long)getIntent().getExtras().get("time");
        }
        setContentView(R.layout.activity_chenben);
        iniData();
        initData();
    }
    private EditText mCount,mPrice,mOther,mCha,mBingku,mYunshu,mJiagong,mBili,
                     mYear,mMonth,mDay,mHour,mMinute;
    private TextView mAll,mAllGuest;
    private ListView mList;
    private Button mButton,mReview,mSave,mClaen,mAllItem;
    private EditText mSpinner;

    private float allGuestValue= 0;
    private void reshowData(){
        ArrayMap<Long, RecordDataBean> recordData =
                NewDataManager.getIntance(this).getRecordData();
        RecordDataBean data = recordData.get(time);
        mCount.setText(data.inRecord.count+"");
        mPrice.setText(data.inRecord.price+"");
        mSpinner.setText(data.inRecord.level+"");
        mOther.setText(data.otherCost1+"");
        mBingku.setText(data.otherCost2+"");
        mJiagong.setText(data.otherCost3+"");
        mYunshu.setText(data.otherCost4+"");
        mCha.setText(data.cha+"");
        mBili.setText(data.bili+"");
        mAll.setText((data.otherCost1+data.otherCost2
                +data.otherCost3+data.otherCost4+
        data.inRecord.count*data.inRecord.price)+"");
        float allGuestValue= 0;
        for(SavceDataBean tmp:data.goodRecord ){
            ChengBenData chenbentmp = new ChengBenData();
            chenbentmp.isBad = false;
            chenbentmp.count = tmp.count;
            chenbentmp.level =tmp.level;
            chenbentmp.price =tmp.price;
            chenbentmp.all = tmp.all;
            allGuestValue +=tmp.all;
            mListData.add(chenbentmp);
        }
        for(SavceDataBean tmp:data.badRecord ){
            ChengBenData chenbentmp = new ChengBenData();
            chenbentmp.isBad = true;
            chenbentmp.count = tmp.count;
            chenbentmp.level =tmp.level;
            chenbentmp.price =tmp.price;
            chenbentmp.all = tmp.all;
            allGuestValue +=tmp.all;
            mListData.add(chenbentmp);
        }
        mAllGuest.setText(allGuestValue+"");
    }
    private void iniData(){
        mPrice = (EditText) findViewById(R.id.input_price);
        mCount = (EditText) findViewById(R.id.input_count);
        mOther = (EditText) findViewById(R.id.input_other);
        mYunshu = (EditText) findViewById(R.id.input_yunshu);
        mJiagong = (EditText) findViewById(R.id.input_jiagong);
        mBingku = (EditText) findViewById(R.id.input_bingku);
        mCha = (EditText) findViewById(R.id.input_chazhi);
        mBili = (EditText) findViewById(R.id.input_bili);
        mAll   = (TextView) findViewById(R.id.tv_all);
        mList = (ListView) findViewById(R.id.list);
        mButton = (Button) findViewById(R.id.chengbeng_bt);
        mReview = (Button) findViewById(R.id.chengbeng_review);
        mSave = (Button) findViewById(R.id.chengbeng_save);
        mClaen = (Button) findViewById(R.id.chengbeng_clean);
        mAllItem = (Button)findViewById(R.id.add_bt);
        mAllGuest = (TextView) findViewById(R.id.tv_all_guest);
        mYear = (EditText) findViewById(R.id.input_time_year);
        mMonth = (EditText) findViewById(R.id.input_time_month);
        mDay = (EditText) findViewById(R.id.input_time_day);
        mHour = (EditText) findViewById(R.id.input_time_hour);
        mMinute = (EditText) findViewById(R.id.input_time_minute);
        mSpinner = (EditText) findViewById(R.id.spinner_income);
    }
    MainListViewAdapter mAdapter = null;
    ArrayList<ChengBenData> mListData= new ArrayList<ChengBenData>();
    int mLevelIncome = 0;
    private void initData(){
        int size = 20;
        ArrayList<String> list = new ArrayList<String>();
        while(true){
            String name = size+"-"+(size+5);
            list.add(name);
            size += 5;
            if(size >120){
                break;
            }
        }
        if(time != -1){
            reshowData();
        }

        mAdapter = new MainListViewAdapter(this,mListData);
        mList.setAdapter(mAdapter);
        mAdapter.setVisitAdd(false);
        setListViewHeightBasedOnChildren(mList);
        if(time != -1){
            mAdapter.showChengPin(true);
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum();
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        mReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewData();
            }
        });
        int year = TimeManager.getYear();
        int month = TimeManager.getMonth();
        int day = TimeManager.getDay();
        int hour = TimeManager.getTime()/10000;
        int minte = TimeManager.getTime()%10000/100;
        if(time != -1){
            mSave.setVisibility(View.GONE);
            mButton.setVisibility(View.GONE);
            mReview.setVisibility(View.GONE);
            mReview.setVisibility(View.GONE);
            mClaen.setVisibility(View.GONE);
            mAllItem.setVisibility(View.GONE);
            mYear.setText((time/100000000)+"");
            mMonth.setText((time%100000000/1000000)+"");
            mDay.setText((time%1000000/10000)+"");
            mHour.setText((time%10000/100)+"");
            mMinute.setText((time%100)+"");
        }else{
            mYear.setText(year+"");
            mMonth.setText(month+"");
            mDay.setText(day+"");
            mHour.setText(hour+"");
            mMinute.setText(minte+"");
        }
    }

    private void reviewData(){
        Intent intent = new Intent(this,ShowDataActivity.class);
        startActivity(intent);
    }
    private void saveData() {
        int year = Integer.valueOf(mYear.getText().toString())%100;
        int month =  Integer.valueOf(mMonth.getText().toString());
        int day =  Integer.valueOf(mDay.getText().toString());
        int hour = Integer.valueOf(mHour.getText().toString());
        int minte =  Integer.valueOf(mMinute.getText().toString());
        long time = year*100000000+month*1000000+day*10000+hour*100+minte;
        Log.w("zsbin",time+"");
        ArrayList<SavceDataBean> good = new  ArrayList<SavceDataBean>();
        ArrayList<SavceDataBean> bad = new  ArrayList<SavceDataBean>();
        for(ChengBenData data : mListData){
            SavceDataBean bean = new SavceDataBean();
            bean.time = time;
            bean.level = data.level;
            bean.price = data.price;
            bean.count = data.count;
            bean.all = data.all;
            if(data.isBad){
                bean.dataType = SavceDataBean.TYPE_BAD;
                good.add(bean);
            }else{
                bean.dataType = SavceDataBean.TYPE_GOOD;
                bad.add(bean);
            }
        }
        SavceDataBean income = new SavceDataBean();
        income.dataType = SavceDataBean.TYPE_IN;
        income.level = mLevelIncome;
        income.price = price;
        income.count = count;
        income.all = price*count;
        income.time = time;
        RecordDataBean bean   = new RecordDataBean();
        bean.goodRecord = good;
        bean.badRecord = bad;
        bean.inRecord = income;
        bean.bili = mBiliData;
        bean.cha = cha;
        bean.otherCost1 = other1;
        bean.otherCost2 = other2;
        bean.otherCost3 = other3;
        bean.otherCost4 = other4;
        bean.time = time;
        NewDataManager.getIntance(this).addRecordData(bean);
    }

    int count;
    float price;
    float cha;
    int other1;
    int other2;
    float other3;
    float other4;
    int mBiliData;
    private boolean sum(){
        String  str = null;
        str = mCount.getText().toString();
        Log.d("zsbin","str= "+str );
        if(TextUtils.isEmpty(str)){
            return false;
        }

        count = Integer.valueOf(str);
        str = mPrice.getText().toString();
        Log.d("zsbin","str= "+str );
        if(TextUtils.isEmpty(str)){
            return false;
        }
        price = Float.valueOf(str);
        str = mCha.getText().toString();
        Log.d("zsbin","str= "+str );
        if(TextUtils.isEmpty(str)){
            return false;
        }
        cha = Float.valueOf(str);

        str = mBili.getText().toString();
        Log.d("zsbin","str= "+str );
        if(TextUtils.isEmpty(str)){
            return false;
        }
        mBiliData = Integer.valueOf(str);
        float bili = mBiliData/100;
        str = mOther.getText().toString();
        Log.d("zsbin","str= "+str );
        if(!TextUtils.isEmpty(str)){
            other1 = Integer.valueOf(str);
        }
        str = mBingku.getText().toString();
        Log.d("zsbin","str= "+str );
        if(!TextUtils.isEmpty(str)){
            other2 = Integer.valueOf(str);
        }
        str = mJiagong.getText().toString();
        Log.d("zsbin","str= "+str );
        if(!TextUtils.isEmpty(str)){
            other3 = Float.valueOf(str);
        }
        str = mYunshu.getText().toString();
        Log.d("zsbin","str= "+str );
        if(!TextUtils.isEmpty(str)){
            other4 = Float.valueOf(str);
        }

        float allCost = count*price+other1+other2+other3+other4;

        mAll.setText(allCost+"");
        float value1 = 0;
        float value2 = 0;
        for(ChengBenData data: mListData){
            float itembili = data.isBad?bili:1.0f;
            value1 += data.count*data.level*cha*itembili;
            value2 += data.count * itembili;
        }
        float value3 = (allCost+value1)/value2;
        float value4 = 0;
        for(ChengBenData data: mListData){
            float itembili = data.isBad?bili:1.0f;
            data.price = Float.valueOf((new DecimalFormat(".000")).format((value3 - data.level*cha)*itembili));
            data.all =data.price*data.count;
            value4 += data.all;
        }
//
//        int[] countList = mAdapter.getCountArray();
//        float[] priceList = new float[countList.length];
//        int size = priceList.length;
//        int value1 = 0;
//        int value2 = 0;
//        Log.d("zsbin","cha ="+cha);
//        for(int i = 0;i<size;i++){
//            Log.d("zsbin","countList "+i+":="+countList[i]);
//            value1 += countList[i];
//            value2 += i*cha*countList[i];
//        }
//        Log.d("zsbin","value1 "+value1);
//        Log.d("zsbin","value2 "+value2);
//        Log.d("zsbin","allCost "+allCost);
//        float value3 = (allCost+value2)/value1;
//        Log.d("zsbin","value3 "+value3);
//        float value4 = 0;
//        for(int i = 0; i<size;i++){
//            priceList[i]=value3-i*cha;
//            value4+=priceList[i]*countList[i];
//        }
        mAllGuest.setText((new DecimalFormat(".000")).format(value4));
        mAdapter.upData(mListData);
        mAdapter.showChengPin(true);
        mAdapter.notifyDataSetInvalidated();
        return true;
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
// 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
// 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
// listView.getDividerHeight()获取子项间分隔符占用的高度
// params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    public void addItem() {
        mListData.add(new ChengBenData());
        mAdapter.upData(mListData);
        mAdapter.notifyDataSetInvalidated();
        setListViewHeightBasedOnChildren(mList);
    }
}
