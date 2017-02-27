package com.amir.manammiam.infrastructure.customView.bottomBar;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class BottomBarManager implements View.OnClickListener {
    private final BottomBarItemClicked listener;
    private ArrayList<BottomBarItem> bottomBarItems;

    public BottomBarManager(BottomBarItemClicked listener) {
        bottomBarItems = new ArrayList<>();
        this.listener = listener;
    }

    public void addItem(BottomBarItem view) {
        view.setOnClickListener(this);
        bottomBarItems.add(view);
    }

    public void setItemSelected(int id) {
        boolean itemFounded = false;
        for (BottomBarItem item :
                bottomBarItems) {
            if (item.isItemSelected()) {
                if (item.getId() != id) {
                    item.setItemSelected(false);
                } else {
                    itemFounded = true;
                }
            } else {
                if (item.getId() == id) {
                    item.setItemSelected(true);
                    itemFounded = true;
                }
            }
        }

        if (!itemFounded) {
            throw new RuntimeException("tried to select an invalid item");
        }

    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        listener.itemClicked(itemId);
        setItemSelected(itemId);
    }


    public void setItemSelectedByPos(int position) {
        for (int i = 0; i < bottomBarItems.size(); i++) {
//            Log.e(getClass().getName(), "setting " + i + " " + position + " unselected" + bottomBarItems.get(i).isSelected());
            if (bottomBarItems.get(i).isItemSelected()) {
                if (position != i) {
                    bottomBarItems.get(i).setItemSelected(false);
                }
            } else {
                if (position == i) {
                    bottomBarItems.get(i).setItemSelected(true);
                }
            }
        }
    }
}
