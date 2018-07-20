package com.jackzheng.jizhang.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jackzheng.jizhang.R;
import com.jackzheng.jizhang.data.ChengBenData;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by jackzheng on 2018/1/4.
 */

public class MainListViewAdapter extends BaseAdapter {
    Context mContext = null;
    ArrayList<ChengBenData> mListData = null;
    public static final int TOOL_TYPE_INPUT = 1;
    public static final int TOOL_TYPE_OUT = 2;
    int[] mPriceArray = null;
    private LayoutInflater mInflater = null;
    DecimalFormat mDecimalFormat =null;
    public MainListViewAdapter(Context context, ArrayList<ChengBenData> list){
        this.mContext = context;
        this.mListData = list;
        mInflater = LayoutInflater.from(mContext);
        mPriceArray = new int[mListData.size()];
        mDecimalFormat=new DecimalFormat(".000");
        Log.d("zsbin","list="+list);
    }
    public int[] getCountArray(){
        return mPriceArray;
    }
    private float[] mPriceList;
    public void setPriceList(float[] list){
        mPriceList =list;
    }
    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean isShowChegnpin = false;
    public void showChengPin (boolean show){
        isShowChegnpin= show;
    }
    class MyTextWatcher implements TextWatcher {
        public MyTextWatcher(ViewHolder holder) {
            mHolder = holder;
        }

        private ViewHolder mHolder;

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && !"".equals(s.toString())) {
                int position = (Integer) mHolder.edit.getTag();
                String str = s.toString();
                if(TextUtils.isEmpty(str)){
                    str = "0";
                }
                mListData.get(position).count= Integer.valueOf(str);
                Log.d("zsbin","mPriceArray position = "+position
                        +"=="+mListData.get(position).count);
            }
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_chengben_input,null);
            holder = new ViewHolder();
            holder.edit =(EditText) convertView.findViewById(R.id.item_chengben_edit);
            holder.dangweiChengpin = (TextView) convertView.findViewById(R.id.item_chengping_dangwei);
            holder.dangwei = (TextView) convertView.findViewById(R.id.item_chengben_dangwei);
            holder.textChengpin = (TextView) convertView.findViewById(R.id.item_changpei_price);
            holder.spinner = (Spinner) convertView.findViewById(R.id.spinner);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.all = (TextView) convertView.findViewById(R.id.item_changpei_all);
            holder.allText1 = (TextView) convertView.findViewById(R.id.item_changpei_text1);
            holder.allText2 = (TextView) convertView.findViewById(R.id.item_changpei_text2);

            holder.edit.setTag(new Integer(position));
            holder.checkbox.setTag(new Integer(position));
            holder.spinner.setTag(new Integer(position));
            holder.edit.addTextChangedListener(new MyTextWatcher(holder));
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int  p = (Integer) buttonView.getTag();
                    mListData.get(p).isBad = isChecked;
                }
            });
            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int p  =(Integer) parent.getTag();
                    mListData.get(p).level = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if(position >= mListData.size()){
//            holder.edit.setVisibility(View.GONE);
//            holder.dangweiChengpin.setVisibility(View.GONE);
//            holder.dangwei.setVisibility(View.GONE);
//            holder.textChengpin.setVisibility(View.GONE);
//            holder.spinner.setVisibility(View.GONE);
//            holder.checkbox.setVisibility(View.GONE);
//            holder.allText1.setVisibility(View.GONE);
//            holder.allText2.setVisibility(View.GONE);
//            holder.all.setVisibility(View.GONE);
//            return convertView;
//        }else{
            holder.edit.setVisibility(View.VISIBLE);
            holder.dangweiChengpin.setVisibility(View.VISIBLE);
            holder.dangwei.setVisibility(View.VISIBLE);
            holder.textChengpin.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.VISIBLE);
            holder.checkbox.setVisibility(View.VISIBLE);
            holder.allText1.setVisibility(View.VISIBLE);
            holder.allText2.setVisibility(View.VISIBLE);
            holder.all.setVisibility(View.VISIBLE);
        }
        if(isShowChegnpin) {
            holder.textChengpin.setVisibility(View.VISIBLE);
            holder.dangweiChengpin.setVisibility(View.VISIBLE);
            holder.allText1.setVisibility(View.VISIBLE);
            holder.allText2.setVisibility(View.VISIBLE);
            holder.all.setVisibility(View.VISIBLE);
            holder.textChengpin.setText(
                    new DecimalFormat(".00").format(mListData.get(position).price));
            holder.all.setText(
                    new DecimalFormat(".00").format(mListData.get(position).all));
        }
//        }else{
//            holder.allText1.setVisibility(View.GONE);
//            holder.allText2.setVisibility(View.GONE);
//            holder.all.setVisibility(View.GONE);
//            holder.textChengpin.setVisibility(View.GONE);
//            holder.dangweiChengpin.setVisibility(View.GONE);
//        }
        holder.checkbox.setChecked(mListData.get(position).isBad);
        holder.spinner.setSelection(mListData.get(position).level);
        if(mListData.get(position).count != 0){
            holder.edit.setText(mListData.get(position).count+"");
        }
        return convertView;
    }
    public static class ViewHolder{
        public Spinner spinner = null;
        public EditText edit = null;
        public CheckBox checkbox = null;
        public TextView dangwei =null;
        public TextView textChengpin = null;
        public TextView dangweiChengpin = null;
        public TextView all = null;
        public TextView allText1 = null;
        public TextView allText2 = null;
    }
    private boolean mIsVisitAdd = true;
    public void setVisitAdd(boolean  isVisit){
        mIsVisitAdd =isVisit;
    }
    public interface AddLister{
        void addItem();
    }
    public void upData(ArrayList<ChengBenData> data){
        mListData = data;
    }
}
