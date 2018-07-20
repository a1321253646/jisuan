package com.jackzheng.jizhang.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.jackzheng.jizhang.manager.TimeManager;
import com.jackzheng.jizhang.sqlHelper.AddUpDb;

import java.util.ArrayList;

import static android.R.attr.data;

/**
 * Created by jackzheng on 2017/12/31.
 */

public class AddUpData {
    private int date = -1;
    private int income = -1;
    private int outcome = -1;
    private int stock = -1;
    private int balance = -1;
    private int staockBlance = -1;

    public int getStaockBlance() {
        return staockBlance;
    }

    public void setStaockBlance(int staockBlance) {
        this.staockBlance = staockBlance;
    }

    public AddUpData(int date, int income, int outcome, int stock, int balance, int stockBlance) {
        this.date = date;
        this.income = income;
        this.outcome = outcome;
        this.stock = stock;
        this.balance = balance;
        this.staockBlance = stockBlance;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getOutcome() {
        return outcome;
    }

    public void setOutcome(int outcome) {
        this.outcome = outcome;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    public ContentValues toContentValues(){
        ContentValues value = new ContentValues();
        value.put(AddUpDb.KEY_DATE,data);
        value.put(AddUpDb.KEY_BALANCE,balance);
        value.put(AddUpDb.KEY_INCOME,income);
        value.put(AddUpDb.KEY_OUTCOME,outcome);
        value.put(AddUpDb.KEY_STOCK,stock);
        value.put(AddUpDb.KEY_STOCK,staockBlance);
        return value;
    }
    public static ArrayList<AddUpData> cursorToArray(Cursor cursor){
        ArrayList<AddUpData> list = new ArrayList<AddUpData>();
        cursor.moveToFirst();
        int date = 0;
        int balance = 0;
        int income = 0;
        int outcome = 0;
        int stock = 0;
        int stockBlance = 0;
        while (!cursor.isAfterLast()){
            date = cursor.getInt(cursor.getColumnIndex(AddUpDb.KEY_DATE));
            balance = cursor.getInt(cursor.getColumnIndex(AddUpDb.KEY_BALANCE));
            income = cursor.getInt(cursor.getColumnIndex(AddUpDb.KEY_INCOME));
            outcome = cursor.getInt(cursor.getColumnIndex(AddUpDb.KEY_OUTCOME));
            stock = cursor.getInt(cursor.getColumnIndex(AddUpDb.KEY_STOCK));
            stockBlance = cursor.getInt(cursor.getColumnIndex(AddUpDb.KEY_STOCKBALANCE));
            AddUpData data = new AddUpData(date,income,outcome,stock,balance,stockBlance);
            list.add(data);
        }
        return list;
    }
    public static AddUpData getAddUpDataForDate(ArrayList<AddUpData> list,int date){
        for(AddUpData data:list){
            if(data.getDate() == date){
                return data;
            }
        }
        return null;
    }

    public static AddUpData getAddUpDataForToday(ArrayList<AddUpData> addUpDatas) {
        return getAddUpDataForDate(addUpDatas, TimeManager.getDate());
    }
}
