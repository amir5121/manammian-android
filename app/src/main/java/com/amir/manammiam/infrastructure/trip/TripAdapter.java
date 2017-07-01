package com.amir.manammiam.infrastructure.trip;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseActivity;
import com.amir.manammiam.infrastructure.Constants;
import com.amir.manammiam.infrastructure.Utils;
import com.amir.manammiam.infrastructure.customView.TextViewFont;

import java.util.ArrayList;

public class TripAdapter extends BaseAdapter {
    private static final String TAG = "TripAdapter";
    private static final int REQUEST_CODE = 21312;
    private final LayoutInflater inflater;
    private final TripCallbacks listener;
    private final BaseActivity activity;
    private ArrayList<Trip> trips;
    private String cancelOrder;

    public TripAdapter(BaseActivity activity, TripCallbacks listener) {
        inflater = LayoutInflater.from(activity);
        trips = new ArrayList<>();
        this.listener = listener;
        cancelOrder = activity.getResources().getString(R.string.cancel_order);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public Trip getItem(int position) {
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
            convertView.findViewById(R.id.item_trip_capacity_container).setVisibility(View.GONE);
            viewHolder.rateCountContainer = convertView.findViewById(R.id.item_trip_rate_count_container);
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
            viewHolder.telegram = convertView.findViewById(R.id.item_trip_telegram);
            viewHolder.call = convertView.findViewById(R.id.item_trip_call);
            viewHolder.passengerBar = ((RatingBar) convertView.findViewById(R.id.item_trip_passenger_bar));


            ((TextViewFont) convertView.findViewById(R.id.item_trip_text_text)).setText(cancelOrder);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (TripViewHolder) convertView.getTag();
        }

        final Trip currItem = trips.get(position);
        final View finalConvertView = convertView;

        viewHolder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.reportService((PassengerTrip) trips.get(position));
            }
        });

        viewHolder.source.setText(currItem.getSourceName());
        viewHolder.destination.setText(currItem.getDestinationName());
        viewHolder.time.setText(currItem.getTime());
        if (currItem.getCar() != null) { //indicating that this item has a driver
            viewHolder.price.setText(currItem.getPrice());
            viewHolder.rateBar.setRating(currItem.getCar().getRate());
            viewHolder.carType.setText(currItem.getCar().getCarType());
            viewHolder.carColor.setText(currItem.getCar().getCarColor());
            viewHolder.carCode.setText(currItem.getCar().getCarCode());
            viewHolder.infoContainer.setVisibility(View.VISIBLE);
            viewHolder.name.setVisibility(View.VISIBLE);
            viewHolder.call.setVisibility(View.VISIBLE);
            viewHolder.telegram.setVisibility(View.VISIBLE);
            viewHolder.topContainer.setBackgroundResource(R.drawable.round_header_primary);


            if (currItem instanceof PassengerTrip) {
                viewHolder.rateCount.setText(String.valueOf(currItem.getCar().getRateCount()));
                viewHolder.passengerBar.setVisibility(View.GONE);
                viewHolder.rateCountContainer.setVisibility(View.VISIBLE);
                viewHolder.telegram.setVisibility(View.VISIBLE);
                viewHolder.call.setVisibility(View.VISIBLE);
                viewHolder.name.setVisibility(View.VISIBLE);
                viewHolder.rateBar.setVisibility(View.VISIBLE);

                viewHolder.name.setText(((PassengerTrip) currItem).getDriverName());

                viewHolder.telegram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currItem.getState() == Trip.NONE)
                            Utils.goToUserInTelegram(activity, ((PassengerTrip) currItem).getDriverTelegramId());
                    }
                });

                viewHolder.call.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //TODO: instead prompt the user and ask him that is he sure that he wants to make a call
                        if (currItem.getState() == Trip.NONE)
                            if (Utils.checkPermission(activity)) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ((PassengerTrip) currItem).getDriverPhoneNumber()));
                                activity.startActivity(intent);
                            } else {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
                            }
                    }
                });
            } else if (currItem instanceof DriverTrip) {
                viewHolder.passengerBar.setVisibility(View.VISIBLE);
                viewHolder.passengerBar.setNumStars(((DriverTrip) currItem).getCapacity());
                viewHolder.passengerBar.setRating(((DriverTrip) currItem).getNumberOfPassenger());
//                Log.e(TAG, "getView: passengerCount " + (((DriverTrip) currItem).getNumberOfPassenger() + 3) + " capacity: " + ((DriverTrip) currItem).getCapacity());
                viewHolder.name.setVisibility(View.GONE);
                viewHolder.rateCountContainer.setVisibility(View.GONE);
                viewHolder.telegram.setVisibility(View.GONE);
                viewHolder.call.setVisibility(View.GONE);
                viewHolder.rateBar.setVisibility(View.GONE);
            }


        } else {

            viewHolder.name.setVisibility(View.GONE);
            viewHolder.infoContainer.setVisibility(View.GONE);
            viewHolder.topContainer.setBackgroundResource(R.drawable.round_header_waiting);
            viewHolder.rateCountContainer.setVisibility(View.GONE);
            viewHolder.telegram.setVisibility(View.GONE);
            viewHolder.call.setVisibility(View.GONE);
            viewHolder.passengerBar.setVisibility(View.GONE);
        }


        if (currItem.getState() == Trip.CANCELING) {
            viewHolder.cancelRequestContainer.setVisibility(View.VISIBLE);
            viewHolder.telegram.setClickable(false);
        } else {
            viewHolder.cancelRequestContainer.setVisibility(View.GONE);
            viewHolder.telegram.setClickable(true);
        }

        if (currItem.getState() == Trip.LOADING) {
            viewHolder.loading.setVisibility(View.VISIBLE);
            viewHolder.telegram.setClickable(false);
        } else {
            viewHolder.loading.setVisibility(View.GONE);
            viewHolder.telegram.setClickable(true);
        }

        if (currItem.getState() == Trip.REPORT_RATE) {
            viewHolder.responseContainer.setVisibility(View.VISIBLE);
            viewHolder.telegram.setClickable(false);
        } else {
            viewHolder.responseContainer.setVisibility(View.GONE);
            viewHolder.telegram.setClickable(true);
        }

        viewHolder.rateResponse.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating != 0) {
                    View view = viewHolder.loading;
                    view.setAlpha(0);
                    view.setVisibility(View.VISIBLE);
                    view.animate().alpha(1).setDuration(Constants.ANIMATION_DURATION).start();
                    listener.rateSubmitted(rating, view, viewHolder.responseContainer, (PassengerTrip) currItem);
                    currItem.setState(Trip.LOADING);
                }
            }
        });

        viewHolder.cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = finalConvertView.findViewById(R.id.item_trip_loading);
                view.setAlpha(0);
                view.setVisibility(View.VISIBLE);
                view.animate().alpha(1).setDuration(Constants.ANIMATION_DURATION).start();
                currItem.setState(Trip.LOADING);

                listener.cancelService(currItem, view, viewHolder.cancelRequestContainer);
            }
        });


        return convertView;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    public ArrayList<Trip> getTrips() {
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
        View telegram;
        View call;
        RatingBar passengerBar;
        View rateCountContainer;

        public View getTelegram() {
            return telegram;
        }
    }
}
