package com.jackzheng.jizhang.data;

import java.util.ArrayList;

/**
 * Created by jackzheng on 2018/1/4.
 */

public class MainListAdapterData {
    private ArrayList mListData = null;
    private ArrayList<String> mShowData = null;
    public  MainListAdapterData(ArrayList listData,ArrayList<String> showData){
        this.mListData = listData;
        this.mShowData = showData;
    }
}
