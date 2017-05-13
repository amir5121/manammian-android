package com.amir.manammiam.infrastructure.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.amir.manammiam.R;
import com.amir.manammiam.infrastructure.customView.TextViewFont;

import java.util.ArrayList;

public class ManamMiamLocationAdapter extends BaseAdapter {
    private ArrayList<ManamMiamLocation> locations;
    private LayoutInflater inflater;
    private View footer;

    public ManamMiamLocationAdapter(Context context) {
        locations = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return locations.size() + 1;
    }

    @Override
    public ManamMiamLocation getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == locations.size()) {
            if (footer == null) {
                footer = inflater.inflate(R.layout.item_car_footer, parent, false);
            }
            return footer;
        }

        LocationViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_location, parent, false);
            viewHolder = new LocationViewHolder();
            viewHolder.mainText = (TextViewFont) convertView.findViewById(R.id.item_location_main_text);
            viewHolder.detailedText = (TextViewFont) convertView.findViewById(R.id.item_location_detailed_text);
            viewHolder.selectedContainer = convertView.findViewById(R.id.item_location_selected);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (LocationViewHolder) convertView.getTag();
            if (viewHolder == null) {
                convertView = inflater.inflate(R.layout.item_location, parent, false);
                viewHolder = new LocationViewHolder();
                viewHolder.mainText = (TextViewFont) convertView.findViewById(R.id.item_location_main_text);
                viewHolder.detailedText = (TextViewFont) convertView.findViewById(R.id.item_location_detailed_text);
                viewHolder.selectedContainer = convertView.findViewById(R.id.item_location_selected);
                convertView.setTag(viewHolder);
            }
        }

        ManamMiamLocation currLoc = locations.get(position);
        viewHolder.mainText.setText(currLoc.getName());
        viewHolder.detailedText.setText(currLoc.getDetailed());

        viewHolder.selectedContainer.setVisibility(currLoc.isSelected() ? View.VISIBLE : View.GONE);

        return convertView;
    }

    public void addLocation(ManamMiamLocation manamMiamLocation) {
        locations.add(manamMiamLocation);
    }
//
//    public void itemClicked(Object tag) {
//        if (lastClickedItem != null) {
//            lastClickedItem.selectedContainer.setVisibility(View.GONE);
//        }
//        lastClickedItem = (LocationViewHolder) tag;
//    }

    public ArrayList<ManamMiamLocation> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<ManamMiamLocation> locations) {
        this.locations = locations;
    }

    class LocationViewHolder {
        View selectedContainer;
        TextViewFont mainText;
        TextViewFont detailedText;
    }
}
