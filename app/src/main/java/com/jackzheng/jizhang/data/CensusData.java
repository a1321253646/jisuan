package com.jackzheng.jizhang.data;

/**
 * Created by jackzheng on 2018/1/1.
 */

public class CensusData {
    public int inCome = 0;
    public int outCome = 0;
    public int balance = 0;
    public int stock = 0;
    public int stockBalance = 0;
    public CensusData(int in,int out,int blance,int stock,int stockBalance){
        this.inCome = in;
        this.outCome = out;
        this.stock = stock;
        this.balance = blance;
        this.stockBalance = stockBalance;
    }
}
