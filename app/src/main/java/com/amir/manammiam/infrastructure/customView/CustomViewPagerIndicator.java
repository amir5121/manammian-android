package com.amir.manammiam.infrastructure.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.viewpagerindicator.UnderlinePageIndicator;


public class CustomViewPagerIndicator extends UnderlinePageIndicator {
    public CustomViewPagerIndicator(Context context) {
        super(context);
    }

    public CustomViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //making it unclickable
        return false;
    }
}
