package com.amir.manammiam.infrastructure.customView;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.amir.manammiam.infrastructure.Constants;
import com.amir.manammiam.infrastructure.ScrollCallback;

public class MViewPager extends ViewPager {

    private static final String TAG = "MViewPager";
    GestureDetectorCompat gestureDetector;
    private ScrollCallback listener;

    public MViewPager(Context context) {
        super(context);
        setUpGestureDetector(context);

    }


    public MViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpGestureDetector(context);
    }

    private void setUpGestureDetector(Context context) {
        try {
            listener = (ScrollCallback) context;
        } catch (ClassCastException e) {
            throw new RuntimeException("In order to use this view implement ScrollCallback interface");
        }
        gestureDetector = new GestureDetectorCompat(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (distanceY > Constants.FAB_SHOW_HIDE_INTENSITY)
                    listener.onScrolled(true);
                if (distanceY < -Constants.FAB_SHOW_HIDE_INTENSITY + 10) // +10 cause fuck you..
                    listener.onScrolled(false);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }
}
