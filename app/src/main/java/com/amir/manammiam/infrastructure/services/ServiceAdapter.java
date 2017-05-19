package com.amir.manammiam.infrastructure.services;

import android.content.Context;
import android.support.annotation.NonNull;
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

    private final boolean hasFooter;
//    private final ServiceAdapterCallbacks listener;
    private View footer;
    private ArrayList<ManamMiamService> services;
    private LayoutInflater inflater;

    public ServiceAdapter(Context context, boolean hasFooter) {
        this.hasFooter = hasFooter;
        inflater = LayoutInflater.from(context);
        services = new ArrayList<>();
//        try {
//            listener = (ServiceAdapterCallbacks) context;
//        } catch (ClassCastException e) {
//            throw new RuntimeException("To use ServiceAdapter class you need to implement ServiceAdapterCallback interface");
//        }
    }

    @Override
    public int getCount() {
        if (hasFooter)
            return services.size() + 1;
        else return services.size();
    }

    @Override
    public ManamMiamService getItem(int position) {
        if (!hasFooter)
            return services.get(position);
        else if (position == services.size()) return null;
        else return services.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (position == services.size()) {
            if (footer == null) {
                footer = inflater.inflate(R.layout.item_car_footer, parent, false);
            }
            return footer;
        }

        ServiceViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_trip, parent, false);
            viewHolder = getServiceViewHolder(convertView);
        } else {
            viewHolder = (ServiceViewHolder) convertView.getTag();
            if (viewHolder == null) {
                convertView = inflater.inflate(R.layout.item_trip, parent, false);
                viewHolder = getServiceViewHolder(convertView);
            }
        }

        final ManamMiamService currService;
        currService = services.get(position);

        viewHolder.source.setText(currService.getSourceName());
        viewHolder.destination.setText(currService.getDestinationName());
        viewHolder.price.setText(currService.getPrice());
        viewHolder.name.setText(currService.getName());
        viewHolder.time.setText(currService.getTime());
        viewHolder.carType.setText(currService.getCar().getCarType());
        viewHolder.carColor.setText(currService.getCar().getCarColor());
        viewHolder.capacity.setText(String.valueOf(currService.getCapacity()));
        viewHolder.capacity.setText(String.valueOf(currService.getCapacity()));
        viewHolder.rateCount.setText(String.valueOf(currService.getCar().getRateCount()));
        viewHolder.rateBar.setRating(currService.getCar().getRate());


//        final View finalConvertView = convertView;
//        viewHolder.yesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View view = finalConvertView.findViewById(R.id.item_trip_loading);
//                view.setAlpha(0);
//                view.setVisibility(View.VISIBLE);
//                view.animate().alpha(1).setDuration(Constants.ANIMATION_DURATION).start();
//                currService.setState(ManamMiamService.LOADING);
//
////                listener.serviceReserved(currService, view, viewHolder.cancelRequestContainer);
//            }
//        });

        switch (currService.getState()) {
            case ManamMiamService.NONE:
                viewHolder.approvalContainer.setVisibility(View.GONE);
                viewHolder.loading.setVisibility(View.GONE);
//                viewHolder.approvalContainer.setAlpha(0);
                break;
            case ManamMiamService.LOADING:
                viewHolder.loading.setVisibility(View.VISIBLE);
                viewHolder.approvalContainer.setVisibility(View.GONE);
                break;
            case ManamMiamService.RESERVING:
                viewHolder.approvalContainer.setVisibility(View.VISIBLE);
//                viewHolder.approvalContainer.setAlpha(1);
                break;
        }

        return convertView;
    }

    @NonNull
    private ServiceViewHolder getServiceViewHolder(View convertView) {
        ServiceViewHolder viewHolder;
        viewHolder = new ServiceViewHolder();
        viewHolder.sourceDestContainer = convertView.findViewById(R.id.src_dest_container);
        viewHolder.source = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_source);
        viewHolder.destination = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_destination);
        viewHolder.price = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_price);
        viewHolder.name = (TextViewFont) convertView.findViewById(R.id.item_trip_text_driver_name);
        viewHolder.time = (TextViewFont) convertView.findViewById(R.id.item_trip_text_time);
        viewHolder.capacity = (TextViewFont) convertView.findViewById(R.id.item_trip_capacity);
        viewHolder.carType = (TextViewFont) convertView.findViewById(R.id.item_trip_text_car_type);
        viewHolder.carColor = (TextViewFont) convertView.findViewById(R.id.item_trip_text_car_color);
        viewHolder.rateCount = (TextViewFont) convertView.findViewById(R.id.item_trip_rate_count);
        viewHolder.rateBar = (RatingBar) convertView.findViewById(R.id.item_trip_rate_bar);
        viewHolder.loading = convertView.findViewById(R.id.item_trip_loading);
//        viewHolder.yesButton = convertView.findViewById(R.id.item_server_text_accept);

        viewHolder.approvalContainer = (LinearLayout) convertView.findViewById(R.id.item_server_approval_container);

        convertView.setTag(viewHolder);
        return viewHolder;
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
        View sourceDestContainer;
        View loading;
        RatingBar rateBar;
        TextViewFont rateCount;
        LinearLayout approvalContainer;
        TextViewFont carColor;

        public LinearLayout getApprovalContainer() {
            return approvalContainer;
        }

        public View getLoading() {
            return loading;
        }
    }
}
