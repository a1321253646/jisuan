package com.jackzheng.jizhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jackzheng.jizhang.R;

import java.util.ArrayList;

/**
 * Created by jackzheng on 2018/2/18.
 */

public class HeadLIstViewAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> mList;
    private LayoutInflater mInflater = null;
    public HeadLIstViewAdapter(Context context, ArrayList<String> list){
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
            convertView = mInflater.inflate(R.layout.item_data_show,null);
            holder = new ViewHolder();
            holder.text =(TextView) convertView.findViewById(R.id.tx_data_show);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        holder.text.setText(mList.get(position));
        return convertView;
    }
    public static class ViewHolder{
        public TextView text;
    }
}
