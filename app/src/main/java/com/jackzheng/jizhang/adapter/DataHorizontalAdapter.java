package com.jackzheng.jizhang.adapter;

import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jackzheng.jizhang.R;
import com.jackzheng.jizhang.data.RecordDataBean;
import com.jackzheng.jizhang.manager.NewDataManager;

import java.util.ArrayList;

/**
 * Created by jackzheng on 2018/2/18.
 */

public class DataHorizontalAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> mList;
    private LayoutInflater mInflater = null;
    public DataHorizontalAdapter(Context context, ArrayList<String> list){
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_data_horizontal,null);
            holder = new ViewHolder();
            holder.list = (ListView)convertView.findViewById(R.id.lv_data_horizontal);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        ArrayList<String> array = new ArrayList<>();
        long time = Long.valueOf(mList.get(position));
        ArrayMap<Long, RecordDataBean> recordData =
                NewDataManager.getIntance(mContext).getRecordData();
        RecordDataBean bean= recordData.get(time);
        array.add(bean.inRecord.price+"");
        array.add(bean.inRecord.level+"");
        array.add(bean.inRecord.count+"");
        array.add(bean.otherCost1+"");
        array.add(bean.otherCost2+"");
        array.add(bean.otherCost3+"");
        array.add(bean.otherCost4+"");
        array.add(bean.goodRecord.get(0).price+"");
        array.add(bean.badRecord.get(0).price+"");
        array.add(bean.cha+"");
        array.add(bean.bili+"%");
        array.add("99%");
        HeadLIstViewAdapter adapter =new HeadLIstViewAdapter(mContext,array);
        holder.list.setAdapter(adapter);
        return convertView;
    }
    public static class ViewHolder{
        public ListView list;
    }
}
