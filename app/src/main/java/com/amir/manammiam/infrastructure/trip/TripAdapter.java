package com.amir.manammiam.infrastructure.trip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;

import com.amir.manammiam.R;
import com.amir.manammiam.infrastructure.customView.TextViewFont;

import java.util.ArrayList;

public class TripAdapter extends BaseAdapter {
    private static final String TAG = "TripAdapter";
    private final LayoutInflater inflater;
    private final TripCallbacks listener;
    private ArrayList<TripItem> trips;
    private String cancelOrder;

    public TripAdapter(Context context, TripCallbacks listener) {
        inflater = LayoutInflater.from(context);
        trips = new ArrayList<>();
        this.listener = listener;
        cancelOrder = context.getResources().getString(R.string.cancel_order);
    }

    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public TripItem getItem(int position) {
        return trips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TripViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new TripViewHolder();
            convertView = inflater.inflate(R.layout.item_trip, parent, false);
            convertView.findViewById(R.id.item_trip_capacity_text).setVisibility(View.GONE);
            viewHolder.source = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_source);
            viewHolder.destination = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_destination);
            viewHolder.price = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_price);
            viewHolder.name = (TextViewFont) convertView.findViewById(R.id.item_trip_text_driver_name);
            viewHolder.time = (TextViewFont) convertView.findViewById(R.id.item_trip_text_time);
            viewHolder.rateCount = (TextViewFont) convertView.findViewById(R.id.item_trip_rate_count);
            viewHolder.rateBar = (RatingBar) convertView.findViewById(R.id.item_trip_rate_bar);
            viewHolder.rateResponse = (RatingBar) convertView.findViewById(R.id.item_trip_rate_response);
            viewHolder.report = convertView.findViewById(R.id.item_trip_report_button);
            viewHolder.carType = (TextViewFont) convertView.findViewById(R.id.item_trip_text_car_type);
            viewHolder.carColor = (TextViewFont) convertView.findViewById(R.id.item_trip_text_car_color);
            viewHolder.carCode = (TextViewFont) convertView.findViewById(R.id.item_trip_text_car_code);
            viewHolder.infoContainer = convertView.findViewById(R.id.item_trip_info_container);
            viewHolder.topContainer = convertView.findViewById(R.id.src_dest_container);
            viewHolder.responseContainer = convertView.findViewById(R.id.item_trip_response_container);
            viewHolder.cancelRequest = convertView.findViewById(R.id.item_server_text_accept);
            viewHolder.cancelRequestContainer = convertView.findViewById(R.id.item_server_approval_container);
            viewHolder.loading = convertView.findViewById(R.id.item_trip_loading);


            ((TextViewFont)convertView.findViewById(R.id.item_trip_text_text)).setText(cancelOrder);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (TripViewHolder) convertView.getTag();
        }

        viewHolder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.reportService(trips.get(position));
            }
        });



        final TripItem currItem = trips.get(position);
        viewHolder.source.setText(currItem.getSourceName());
        viewHolder.destination.setText(currItem.getDestinationName());
        viewHolder.time.setText(currItem.getTime());
        if (currItem.getDriverName() != null) { //indicating that this item has no driver yet
            viewHolder.price.setText(currItem.getPrice());
            viewHolder.name.setText(currItem.getDriverName());
            viewHolder.rateBar.setRating(currItem.getCar().getRate());
            viewHolder.carType.setText(currItem.getCar().getCarType());
            viewHolder.carColor.setText(currItem.getCar().getCarColor());
            viewHolder.carCode.setText(currItem.getCar().getCarCode());
            viewHolder.rateCount.setText(String.valueOf(currItem.getCar().getRateCount()));
            viewHolder.infoContainer.setVisibility(View.VISIBLE);
            viewHolder.name.setVisibility(View.VISIBLE);
            viewHolder.topContainer.setBackgroundResource(R.drawable.round_header_primary);

            if (currItem.getState() == TripItem.REPORT_RATE) {
                viewHolder.responseContainer.setVisibility(View.VISIBLE);
            } else {
                viewHolder.responseContainer.setVisibility(View.GONE);
            }

        } else {
            viewHolder.name.setVisibility(View.GONE);
            viewHolder.infoContainer.setVisibility(View.GONE);
            viewHolder.topContainer.setBackgroundResource(R.drawable.round_header_waiting);

            if (currItem.getState() == TripItem.CANCELING) {
                viewHolder.cancelRequestContainer.setVisibility(View.VISIBLE);
            } else {
                viewHolder.cancelRequestContainer.setVisibility(View.GONE);
            }
        }


        if (currItem.getState() == TripItem.LOADING) {
            viewHolder.loading.setVisibility(View.VISIBLE);
        } else {
            viewHolder.loading.setVisibility(View.GONE);
        }


        final View finalConvertView = convertView;
        viewHolder.rateResponse.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                View view = finalConvertView.findViewById(R.id.item_trip_loading);
                view.setVisibility(View.VISIBLE);
                listener.rateSubmitted(rating, view, viewHolder.responseContainer, currItem);
                currItem.setState(TripItem.LOADING);
            }
        });

        return convertView;
    }

    public void setTrips(ArrayList<TripItem> trips) {
        this.trips = trips;
    }

    public ArrayList<TripItem> getTrips() {
        return trips;
    }


    public class TripViewHolder {
        TextViewFont source;
        TextViewFont destination;
        TextViewFont price;
        TextViewFont name;
        TextViewFont time;
        RatingBar rateBar;
        RatingBar rateResponse;
        View report;
        TextViewFont carType;
        TextViewFont carColor;
        TextViewFont carCode;
        View infoContainer;
        View topContainer;
        TextViewFont rateCount;
        View responseContainer;
        View loading;
        View cancelRequest;
        View cancelRequestContainer;
    }
}
