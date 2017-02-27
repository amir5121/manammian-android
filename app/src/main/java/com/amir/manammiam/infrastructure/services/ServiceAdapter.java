package com.amir.manammiam.infrastructure.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.amir.manammiam.R;
import com.amir.manammiam.infrastructure.customView.TextViewFont;

import java.util.ArrayList;

public class ServiceAdapter extends BaseAdapter {

    private ArrayList<ManamMiamService> services;
    private LayoutInflater inflater;

    public ServiceAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        services = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public ManamMiamService getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServiceViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_trip, parent, false);
            viewHolder = new ServiceViewHolder();
            viewHolder.sourceDestContainer = convertView.findViewById(R.id.src_dest_container);
            viewHolder.source = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_source);
            viewHolder.destination = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_destination);
            viewHolder.price = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_price);
            viewHolder.name = (TextViewFont) convertView.findViewById(R.id.item_trip_text_driver_name);
            viewHolder.time = (TextViewFont) convertView.findViewById(R.id.item_trip_text_time);
            viewHolder.capacity = (TextViewFont) convertView.findViewById(R.id.item_trip_capacity);
            viewHolder.carType = (TextViewFont) convertView.findViewById(R.id.item_trip_text_car_type);
            viewHolder.rateCount = (TextViewFont) convertView.findViewById(R.id.item_trip_rate_count);
            viewHolder.rateBar = (RatingBar) convertView.findViewById(R.id.item_trip_rate_bar);

            viewHolder.approvalContainer = (LinearLayout) convertView.findViewById(R.id.item_server_approval_container);

            convertView.setTag(viewHolder);
        } else {
            //todo: reset
            viewHolder = (ServiceViewHolder) convertView.getTag();
        }

        viewHolder.source.setText(services.get(position).getSource());
        viewHolder.destination.setText(services.get(position).getDestination());
        viewHolder.price.setText(services.get(position).getPrice());
        viewHolder.name.setText(services.get(position).getName());
        viewHolder.time.setText(services.get(position).getTime());
        viewHolder.carType.setText(services.get(position).getCar().getCarType());
        viewHolder.capacity.setText(String.valueOf(services.get(position).getCapacity()));
        viewHolder.capacity.setText(String.valueOf(services.get(position).getCapacity()));
        viewHolder.rateCount.setText(String.valueOf(services.get(position).getCar().getRateCount()));
        viewHolder.rateBar.setRating(services.get(position).getCar().getRate());
        viewHolder.isActivated = services.get(position).isActivated();


        if (services.get(position).isActivated()) {
            viewHolder.approvalContainer.setVisibility(View.VISIBLE);
            viewHolder.approvalContainer.setAlpha(1);
        } else {
            viewHolder.approvalContainer.setVisibility(View.GONE);
            viewHolder.approvalContainer.setAlpha(0);
        }

        return convertView;
    }

    public ArrayList<ManamMiamService> getServices() {
        return services;
    }

    public void setServices(ArrayList<ManamMiamService> services) {
        this.services = services;
    }


    public class ServiceViewHolder {
        TextViewFont source;
        TextViewFont destination;
        TextViewFont price;
        TextViewFont name;
        TextViewFont time;
        TextViewFont capacity;
        TextViewFont carType;
        RatingBar rateBar;
        TextViewFont rateCount;

        LinearLayout approvalContainer;
        boolean isActivated = false;
        View sourceDestContainer;

        public LinearLayout getApprovalContainer() {
            return approvalContainer;
        }
    }
}
