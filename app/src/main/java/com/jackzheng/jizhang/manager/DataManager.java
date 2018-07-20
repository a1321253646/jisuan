package com.jackzheng.jizhang.manager;

import android.content.Context;
import android.database.Cursor;

import com.jackzheng.jizhang.activity.MainActivity;
import com.jackzheng.jizhang.data.AddUpData;
import com.jackzheng.jizhang.data.CensusData;
import com.jackzheng.jizhang.data.RecordData;
import com.jackzheng.jizhang.data.TypeData;
import com.jackzheng.jizhang.sqlHelper.AddUpDb;
import com.jackzheng.jizhang.sqlHelper.CommDb;
import com.jackzheng.jizhang.sqlHelper.DataDb;
import com.jackzheng.jizhang.sqlHelper.TypeDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.id;

/**
 * Created by jackzheng on 2018/1/1.
 */

public class DataManager {
    private static DataManager mInstance ;
    private static String[] mListViewString={"今天","当月","全部","统计"};
    public static synchronized DataManager getIntance(Context context){
        if(mInstance ==null){
            mInstance = new DataManager(context);
        }
        return mInstance;
    }
    private DataManager(Context context){
        mCommDb = CommDb.getIntance(context).open();
        mDataDb = DataDb.getIntance(context).open();
        mTypeDb = TypeDb.getIntance(context).open();
        mAddUpDb = AddUpDb.getIntance(context).open();
        initData(context);
    }
    CommDb mCommDb ;
    DataDb mDataDb;
    TypeDb mTypeDb;
    AddUpDb mAddUpDb;
    ArrayList<TypeData> mTypeList = null;
    ArrayList<RecordData> mThisDayRecordList = null;
    ArrayList<AddUpData> mAddUpList = null;
    int mThisDay = -1;
    public void initData(Context context){
        if(mTypeList == null){
            mTypeList = TypeData.cursorToArray(mTypeDb.queryAll());
        }
        int date = TimeManager.getDate();
        if(date != mThisDay || mThisDayRecordList == null){
            mThisDay = date;
            mThisDayRecordList = RecordData.cursorToArray(mDataDb.queryByDate(mThisDay));
        }
        if(mAddUpList == null){
            mAddUpList = AddUpData.cursorToArray(mAddUpDb.queryAll());
        }
    }
    public RecordData getRecordByTimeAnData(int date,int time){
        ArrayList<RecordData> dayRecord = getDayRecord(date);
        for(RecordData data :dayRecord){
            if(data.getTime() == time){
                return data;
            }
        }
        return null;
    }
    public ArrayList<RecordData> getDayRecord(int date){
        Cursor cursor = mDataDb.queryByDate(date);
        ArrayList<RecordData> recordDatas = RecordData.cursorToArray(cursor);
        return recordDatas;
    }
    public ArrayList<RecordData> getThisDay(){
        Cursor cursor = mDataDb.queryByDate(TimeManager.getDate());
        ArrayList<RecordData> recordDatas = RecordData.cursorToArray(cursor);
        return recordDatas;
    }
    public ArrayList<AddUpData> getMonthList(int date){
        int startDay = date/100*100;
        int endDay = startDay+99;
        ArrayList<AddUpData> list = AddUpData.cursorToArray(
                mAddUpDb.queryDays(startDay,endDay));
        return list;
    }
    public ArrayList<AddUpData> getThisMonthList(){
        return getMonthList(mThisDay);
    }
    public CensusData getThisMonth(){
        int startDay = mThisDay/100*100;
        ArrayList<AddUpData> list = AddUpData.cursorToArray(
                mAddUpDb.queryDays(startDay,mThisDay));

        int income=0,out=0,stock=0 ,stockBlance = 0,blance= 0;
        for(AddUpData data :list){
            income += data.getIncome();
            out += data.getOutcome();
            blance = income - out;
            stock += data.getStock();
        }
        stockBlance = list.get(list.size()-1).getStaockBlance();
        return new CensusData(income,out,blance,stock,stockBlance);
    }
    public String getTypeName(RecordData data){
        for(TypeData tmp :mTypeList){
            if (data.getTypeId() == data.getTypeId()){
                return tmp.getTypeName();
            }
        }
        return null;
    }
    public ArrayList<TypeData> getTypeList(){
        return  mTypeList;
    }
    public ArrayList<AddUpData> getmAddUpList(){
        return  mAddUpList;
    }
    public ArrayList<TypeData> getTypeStringArray(int recordType){
        int leng = mTypeList.size();
        ArrayList<TypeData> value = new ArrayList<TypeData>();
        int i = 0;
        for(TypeData data :mTypeList){
            if(data.getRecordId() == recordType){
                value.add(data);
            }
        }
        return  value;
    }
    public void saveNewType(int typeId, int recordId, int unitPrice, String typeName){
        TypeData data = new TypeData(id,recordId,unitPrice,typeName);
        mTypeDb.insert(data);
    }
    public void changeTypeByPrice(int typeId, int recordId, int unitPrice, String typeName){
        TypeData data = new TypeData(id,recordId,unitPrice,typeName);
        mTypeDb.updata(data);
    }
    private Map<String,String> creatMap(String name,String context,String in,String out){
        Map<String,String> map = new HashMap<String,String >();
        map.put("name",name);
        map.put("context",context);
        map.put("in",in);
        map.put("out",out);
        return map;
    }
    public List<Map<String,String>> getHistoryItemDataList(int type ,List list){
        ArrayList<Map<String,String>> value = new ArrayList<Map<String,String>>();
        String in  = "";
        String out = "";
        String name = "";
        String context = "";

        for(Object data:list ){

            if(type == MainActivity.HISTORY_TYPE_TODAY){
                RecordData recordData = (RecordData) data;
                int time   = recordData.getTime();
                name = time/10000+":"+time%10000/100+":"+time%100;
                int typeid = recordData.getTypeId();
                TypeData typeData = null;
                for(TypeData tmp : mTypeList){
                    if(tmp.getTypeId() == typeid){
                        typeData = tmp;
                    }
                }
                context = typeData.getTypeName();
                String dangwei="";
                if(typeData.getRecordId() == TypeData.RECORD_TYPE_OUT){
                    context += " 支出";
                    dangwei = "元";
                }else if(typeData.getRecordId() == TypeData.RECORD_TYPE_INCOME){
                    context += " 收入";
                    dangwei = "元";
                }else if(typeData.getRecordId() == TypeData.RECORD_TYPE_STOCK_IN){
                    context += " 入库";
                    dangwei = "公斤";
                }else if(typeData.getRecordId() == TypeData.RECORD_TYPE_STOCK_OUT){
                    context += " 出库";
                    dangwei = "公斤";
                }
                context += ":"+recordData.getTypeCount()+"*"+recordData.getPrice()+"=";
                context += (recordData.getPrice()*recordData.getTypeCount())+dangwei;
            }else if(type == MainActivity.HISTORY_TYPE_THISMONTH ||
                    type == MainActivity.HISTORY_TYPE_ALL) {
                AddUpData recordData = (AddUpData) data;
                int date = recordData.getDate();
                name = date / 10000 + "/" + date % 10000 / 100 + "/" + date % 100;
                in = "" + recordData.getIncome();
                out = recordData.getOutcome() + "";
                context = "结余：" + recordData.getBalance() + " 库存:" + recordData.getStaockBlance();
            }else if(type == MainActivity.HISTORY_TYPE_CENSUS){

            }
            value.add(creatMap(name,context,in,out));
        }
        return value;
    }
    public List<Map<String,String>> getMainListViewShowData(){
        ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
       AddUpData today = mAddUpList.get(mAddUpList.size());
        String in  = today.getIncome()+"";
        String out = today.getOutcome()+"";
        String name = mListViewString[0];
        String context = "结余："+today.getBalance()+" 库存结余:"+today.getStock();
        list.add(creatMap(name,context,in,out));
        CensusData monthData =  getThisMonth();
        in  = monthData.inCome+"";
        out = monthData.outCome+"";
        context = "结余："+monthData.balance+" 库存结余:"+monthData.stock;
        name = mListViewString[1];
        monthData = getAllCensusData();
        list.add(creatMap(name,context,in,out));
        in  = monthData.inCome+"";
        out = monthData.outCome+"";
        context = "结余："+monthData.balance+" 库存结余:"+monthData.stock;
        name = mListViewString[2];
        list.add(creatMap(name,context,in,out));

        in  = "";
        out = "";
        context = "查看统计数据";
        name = mListViewString[3];
        list.add(creatMap(name,context,in,out));
        return list;

    }
    private CensusData getAllCensusData(){
        int income=0,out=0,stock=0 ,stockBlance = 0,blance= 0;
        for(AddUpData data : mAddUpList){
            income += data.getIncome();
            out += data.getOutcome();
            blance = income - out;
            stock += data.getStock();
        }
        stockBlance = mAddUpList.get(mAddUpList.size()-1).getStaockBlance();
        return new CensusData(income,out,blance,stock,stockBlance);
    }
    public void saveRecordData(int typeId, int typeCount, int price,
                               int recordType, String recordImage, String recordText,int date,int time,boolean isChange){
        RecordData data = new RecordData(date,time,typeId,typeCount,price,recordType,recordImage,recordText);

        if(isChange){
            mDataDb.updata(data);
        }else{
            mDataDb.insert(data);
            mThisDayRecordList.add(data);
        }

        AddUpData changeData = AddUpData.getAddUpDataForDate(mAddUpList, date);
        boolean isNew = false;
        if(changeData == null){
            AddUpData lastDate = mAddUpList.get(mAddUpList.size()-1);
            changeData = new AddUpData(date,0,0,0,0,lastDate.getStaockBlance());
            mAddUpList.add(changeData);
            isNew = true;
        }
        int value = 0;
        int cost = price * typeCount;
        switch (recordType){
            case TypeData.RECORD_TYPE_INCOME:
                value = changeData.getIncome() + cost;
                changeData.setIncome(value);
                changeData.setBalance(changeData.getBalance() + cost);
                break;
            case TypeData.RECORD_TYPE_OUT:
                value = changeData.getOutcome() + cost;
                changeData.setOutcome(value);
                changeData.setBalance(changeData.getBalance() - cost);
                break;
            case TypeData.RECORD_TYPE_STOCK_IN:
                value = changeData.getBalance();
                changeData.setBalance(value + cost);
                changeData.setStaockBlance(changeData.getBalance()+cost);
                break;
            case TypeData.RECORD_TYPE_STOCK_OUT:
                value = changeData.getBalance();
                changeData.setBalance(value - cost);
                changeData.setStaockBlance(changeData.getBalance() - cost);
                break;
        }
        if(isNew){
            mAddUpDb.insert(changeData);
        }else{
            mAddUpDb.updata(changeData);
        }
    }
    public ArrayList<RecordData> getAllRecord() {
        Cursor cursor = mDataDb.queryAll();
        return  RecordData.cursorToArray(cursor);
    }
    public ArrayList<AddUpData> getAllAddUpp() {
        return mAddUpList;
    }
}
