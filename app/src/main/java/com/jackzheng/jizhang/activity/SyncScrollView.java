package com.jackzheng.jizhang.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by StephenHe on 2016/8/4.
 * 实现两个HorizontalScrollView同步滚动
 */

public class SyncScrollView extends ScrollView {
    private View mView;

    public SyncScrollView(Context context) {
        super(context);
    }

    public SyncScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SyncScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mView != null) {
            mView.scrollTo(l, t);
        }
    }

    public void setScrollView(View view) {
        mView = view;
    }
}