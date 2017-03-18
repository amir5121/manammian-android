package com.amir.manammiam.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.amir.manammiam.infrastructure.ScrollCallback;

public abstract class ManamMiamAdapter extends BaseAdapter {
//    private final ScrollCallback listener;
//    private int lastPos = 2000;

    public ManamMiamAdapter(Context context) {
//        try {
//            listener = (ScrollCallback) context;
//
//        } catch (ClassCastException e) {
//            throw new RuntimeException("In Order to use this Adapter You need to Implement ScrollCallback");
//        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (lastPos > position) listener.onScrolled(false);
//        else listener.onScrolled(true);
//        lastPos = position;
        return null;
    }
}
