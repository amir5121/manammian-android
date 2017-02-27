package com.amir.manammiam.infrastructure.customView.bottomBar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public final class BottomBarItem extends LinearLayout{
    private static final long ANIME_DURATION = 350;
    private boolean isSelected;

    public BottomBarItem(Context context) {
        super(context);
        doStuff();
    }

    public BottomBarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        doStuff();
    }

    public BottomBarItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        doStuff();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomBarItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        doStuff();
    }

    private void doStuff() {
        this.isSelected = false;
    }

    public boolean isItemSelected() {
        return isSelected;
    }

    public void setItemSelected(boolean setSelected) {
        isSelected = setSelected;
        if (setSelected) {
            animate().scaleY(.95f).scaleX(.95f).alpha(1).setDuration(ANIME_DURATION).start();

        } else {
            animate().scaleY(.7f).scaleX(.7f).alpha(.85f).setDuration(ANIME_DURATION).start();
        }
    }
}
