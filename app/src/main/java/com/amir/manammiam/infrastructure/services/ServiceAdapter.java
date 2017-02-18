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

        for (int i = 0; i < 10; i++)
            services.add(new ManamMiamService("1", "2", 5.0 - i / 2., "sepah", "pardis", "2000", 5, "Peugeot", "1396/09/29 20:30", "amir", "12 پ 234 73", "Blue"));
        //TODO: listener onServicesReceived
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
            convertView = inflater.inflate(R.layout.item_server, parent, false);
            viewHolder = new ServiceViewHolder();
            viewHolder.source = (TextViewFont) convertView.findViewById(R.id.item_server_text_source);
            viewHolder.destination = (TextViewFont) convertView.findViewById(R.id.item_server_text_destination);
            viewHolder.price = (TextViewFont) convertView.findViewById(R.id.item_server_text_price);
            viewHolder.name = (TextViewFont) convertView.findViewById(R.id.item_server_text_name);
            viewHolder.time = (TextViewFont) convertView.findViewById(R.id.item_server_text_time);
            viewHolder.capacity = (TextViewFont) convertView.findViewById(R.id.item_server_text_capacity);
            viewHolder.carType = (TextViewFont) convertView.findViewById(R.id.item_server_text_car_type);
            viewHolder.rateBar = (RatingBar) convertView.findViewById(R.id.item_server_rate_bar);

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
        viewHolder.carType.setText(services.get(position).getCarType());
        viewHolder.capacity.setText(String.valueOf(services.get(position).getCapacity()));
        viewHolder.rateBar.setRating((float) services.get(position).getRate());
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


    public class ServiceViewHolder {
        TextViewFont source;
        TextViewFont destination;
        TextViewFont price;
        TextViewFont name;
        TextViewFont time;
        TextViewFont capacity;
        TextViewFont carType;
        RatingBar rateBar;
        LinearLayout approvalContainer;

        boolean isActivated = false;

        public LinearLayout getApprovalContainer() {
            return approvalContainer;
        }
    }
}
