package com.amir.manammiam.infrastructure.customView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.amir.manammiam.R;

public class BottomButton {


    public BottomButton(Context context, LinearLayout parent, int drawable, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bottom_button, parent, false);

        parent.addView(view);
//        inflater.inflate(R.layout.item_bottom_button, parent, false);
    }
}
