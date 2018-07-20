package com.jackzheng.jizhang.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jackzheng.jizhang.R;
import com.jackzheng.jizhang.data.RecordData;
import com.jackzheng.jizhang.data.TypeData;
import com.jackzheng.jizhang.manager.DataManager;
import com.jackzheng.jizhang.manager.SharepreferenceManager;
import com.jackzheng.jizhang.manager.TimeManager;

import java.util.ArrayList;

import static com.jackzheng.jizhang.R.id.tv_addrecord_out;

/**
 * Created by jackzheng on 2018/1/1.
 */

public class AddRecordActivity extends Activity  implements View.OnClickListener{
    public RecordData mRecordData = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_addrecord);
        initUi();
        initData();
        showData(TypeData.RECORD_TYPE_OUT);
        Intent intent = getIntent();
        int data = intent.getIntExtra("date",0);
        int time = intent.getIntExtra("time",0);
        if(data != 0 ){
            mRecordData = DataManager.getIntance(this).getRecordByTimeAnData(data,time);
        }

        super.onCreate(savedInstanceState);
    }

    private ImageView mImBack,mImSave,mImCamera;
    private EditText mEtCost,mEtRemask,mEtAddType,mEtTypePrice;
    private TextView mTvOut,mTvIn,mTvStockIn,mTvStockOut;
    private Spinner mSpType;
    private int mRecordType =-1;
    private void initUi() {
        mImBack = (ImageView) findViewById(R.id.addrecord_back);
        mImBack.setEnabled(true);
        mImBack.setClickable(true);
        mImBack.setOnClickListener(this);
        mImCamera = (ImageView) findViewById(R.id.addrecord_camera);
        mImCamera.setEnabled(true);
        mImCamera.setClickable(true);
        mImCamera.setOnClickListener(this);
        mImSave = (ImageView) findViewById(R.id.addrecord_save);
        mImSave.setEnabled(true);
        mImSave.setClickable(true);
        mImSave.setOnClickListener(this);
        mEtAddType = (EditText) findViewById(R.id.addrecord_newtype);
        mEtRemask = (EditText) findViewById(R.id.addrecord_remark);
        mEtCost = (EditText) findViewById(R.id.addrecord_cost);
        mEtTypePrice = (EditText) findViewById(R.id.addrecord_type_price);
        mTvIn = (TextView) findViewById(R.id.tv_addrecord_int);
        mTvIn.setEnabled(true);
        mTvIn.setClickable(true);
        mTvIn.setOnClickListener(this);
        mTvOut = (TextView) findViewById(tv_addrecord_out);
        mTvOut.setEnabled(true);
        mTvOut.setClickable(true);
        mTvOut.setOnClickListener(this);
        mTvStockIn = (TextView) findViewById(R.id.tv_addrecord_stock_in);
        mTvStockIn.setEnabled(true);
        mTvStockIn.setClickable(true);
        mTvStockIn.setOnClickListener(this);
        mTvStockOut = (TextView) findViewById(R.id.tv_addrecord_stock_out);
        mTvStockOut.setEnabled(true);
        mTvStockOut.setClickable(true);
        mTvStockOut.setOnClickListener(this);
        mSpType = (Spinner) findViewById(R.id.addrecord_spinner);
    }
    private void initData(){
        if(mRecordData != null){
            changeRecordTpye(mRecordData.getRecordType());
            mEtAddType.setText(DataManager.getIntance(this).getTypeName(mRecordData));
            mEtRemask.setText(mRecordData.getRecordText());
            mEtCost.setText(mRecordData.getTypeCount());
            mEtTypePrice.setText(mRecordData.getPrice());
            return ;
        }
        changeRecordTpye(TypeData.RECORD_TYPE_OUT);
    }
    private ArrayAdapter<String> mTypeAdapter ;
    private ArrayList<TypeData> mTypeStringArray = null;
    private int mTypeId = 0;
    private String mTypeName = null;
    private int mTypePrice = 0;
    private void showData(int data) {
        if(data ==  mRecordType ){
            return;
        }
        mRecordType = data;
        mTypeStringArray = DataManager.getIntance(getApplicationContext()).getTypeStringArray(data);
        String[] typeStringArray = new String[mTypeStringArray.size()];
        for(int i =0;i<mTypeStringArray.size();i++){
            typeStringArray[i] = mTypeStringArray.get(i).getTypeName();
        }
        if(mTypeAdapter ==null){
            mTypeAdapter = new ArrayAdapter<String>(
                    this,android.R.layout.simple_spinner_item,typeStringArray);
            mTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mTypeId = mTypeStringArray.get(position).getTypeId();
                    mTypePrice = mTypeStringArray.get(position).getUnitPrice();
                    mEtTypePrice.setText(""+mTypePrice);
                    mTypeName = mTypeStringArray.get(position).getTypeName();
                }
            });
            mSpType.setAdapter(mTypeAdapter);
        }else{
            mTypeAdapter.notifyDataSetChanged();
        }
        mEtRemask.setText("");
        mEtCost.setText("0");
        mEtAddType.setText("");
    }
    private void changeRecordTpye(int type){
        mTvOut.setTextColor(Color.rgb(0,0xff,00));
        mTvIn.setTextColor(Color.rgb(0,0xff,00));
        mTvStockOut.setTextColor(Color.rgb(0,0xff,00));
        mTvStockIn.setTextColor(Color.rgb(0,0xff,00));
        switch (mRecordType){
            case TypeData.RECORD_TYPE_INCOME :
                mTvIn.setTextColor(Color.rgb(0xff,0,00));
                break;
            case TypeData.RECORD_TYPE_OUT :
                mTvOut.setTextColor(Color.rgb(0xff,0,00));
                break;
            case TypeData.RECORD_TYPE_STOCK_IN :
                mTvStockIn.setTextColor(Color.rgb(0xff,0,00));
                break;
            case TypeData.RECORD_TYPE_STOCK_OUT :
                mTvStockOut.setTextColor(Color.rgb(0xff,0,00));
                break;
        }
        showData(type);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_addrecord_out:
                changeRecordTpye(TypeData.RECORD_TYPE_OUT);
                break;
            case R.id.tv_addrecord_int:
                changeRecordTpye(TypeData.RECORD_TYPE_INCOME);
                break;
            case R.id.tv_addrecord_stock_out:
                changeRecordTpye(TypeData.RECORD_TYPE_STOCK_OUT);
                break;
            case R.id.tv_addrecord_stock_in:
                changeRecordTpye(TypeData.RECORD_TYPE_STOCK_IN);
                break;
            case R.id.addrecord_back:
                finish();
                break;
            case R.id.addrecord_camera:
                cameraWork();
                break;
            case R.id.addrecord_save:
                saveData();
                break;
        }
    }
    private String mCameraPath = null;
    private void cameraWork(){

    }
    private void saveData(){
        int date =TimeManager.getDate();
        int time =TimeManager.getTime();
        boolean isChange = false;
        if(mRecordData != null){
            date = mRecordData.getDate();
            time = mRecordData.getTime();
            isChange = true;
        }
        String typeName = mEtAddType.getText().toString();
        int price = Integer.getInteger(mEtTypePrice.getText().toString());
        int count = Integer.getInteger(mEtCost.getText().toString());
        if(!TextUtils.isEmpty(typeName)){
            mTypeName = typeName;
            mTypePrice = price;
            mTypeId = SharepreferenceManager.getIntance(getApplicationContext()).addNewTypeId();
            DataManager.getIntance(getApplicationContext()).saveNewType(mTypeId,mRecordType,price,typeName);
        }
        if(price != mTypePrice){
            mTypePrice = price;
            DataManager.getIntance(getApplicationContext()).changeTypeByPrice(mTypeId,mRecordType,mTypePrice,mTypeName);

        }
        DataManager.getIntance(getApplicationContext()).saveRecordData(
                mTypeId,count,mTypePrice,mRecordType,mCameraPath,mEtRemask.getText().toString(),date,time,isChange);

    }

}
