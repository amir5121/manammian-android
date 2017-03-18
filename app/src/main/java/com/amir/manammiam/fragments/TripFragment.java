package com.amir.manammiam.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.infrastructure.ScrollCallback;
import com.amir.manammiam.infrastructure.trip.TripAdapter;
import com.amir.manammiam.infrastructure.trip.TripCallbacks;
import com.amir.manammiam.infrastructure.trip.TripItem;
import com.amir.manammiam.services.MMTime;
import com.amir.manammiam.services.Rate;
import com.amir.manammiam.services.Trips;
import com.squareup.otto.Subscribe;

public class TripFragment extends BaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, TripCallbacks {
    private TripAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    // newInstance constructor for creating fragment with arguments
    public static TripFragment newInstance(int page, String title) {
        TripFragment fragmentFirst = new TripFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_with_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.fragment_with_list_list);
        adapter = new TripAdapter(getActivity(), this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        swipeRefresh = ((SwipeRefreshLayout) view.findViewById(R.id.fragment_with_list_swipe_refresh));
        swipeRefresh.setOnRefreshListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary, getActivity().getTheme()));
        } else {
            swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        }
        swipeRefresh.setRefreshing(true);
        bus.post(new Trips.TripRequest(application.getUser().getToken()));
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TripItem tripItem = adapter.getTrips().get(position);
        boolean hasBeenPickedUp = (tripItem.getCar() != null);
        View cancelContainer = view.findViewById(R.id.item_server_approval_container);
        View responseContainer = view.findViewById(R.id.item_trip_response_container);
        View loadingContainer = view.findViewById(R.id.item_trip_loading);
        switch (tripItem.getState()) {
            case TripItem.NONE:
                if (hasBeenPickedUp) {
                    loadingContainer.setVisibility(View.VISIBLE);
                    tripItem.setState(TripItem.LOADING);
                    bus.post(new MMTime.Request(application.getUser().getToken(), tripItem.getTime(), cancelContainer, responseContainer, loadingContainer, tripItem));
                } else {
                    cancelContainer.setVisibility(View.VISIBLE);
                    tripItem.setState(TripItem.CANCELING);
                }
                break;
            case TripItem.LOADING:
                //Won't happen !! i'll bet :)
                break;
            case TripItem.CANCELING:
                cancelContainer.setVisibility(View.GONE);
                tripItem.setState(TripItem.NONE);
                break;
            case TripItem.REPORT_RATE:
                responseContainer.setVisibility(View.GONE);
                tripItem.setState(TripItem.NONE);
                break;
        }
    }

    @Subscribe
    public void onTimeRecieved(MMTime.TimeResponse response) {
        if (response.didSucceed()) {
            if (response.isInTheFuture()) {
                response.getResponseContainer().setVisibility(View.VISIBLE);
                response.getTripItem().setState(TripItem.REPORT_RATE);

            } else {
                response.getCancelContainer().setVisibility(View.VISIBLE);
                response.getTripItem().setState(TripItem.CANCELING);
            }
        } else {
            //TODO: handle Error
        }
        response.getLoadingContainer().setVisibility(View.GONE);
    }

    @Subscribe
    public void tripsReceived(Trips.TripResponse response) {
        swipeRefresh.setRefreshing(false);
        if (response.didSucceed()) {
            adapter.setTrips(response.getTrips());
            adapter.notifyDataSetChanged();
        } else {
            //TODO: handle error
        }
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(true);
        bus.post(new Trips.TripRequest(application.getUser().getToken()));
    }

    @Override
    public void reportService(TripItem tripItem) {
        //TODO: implement
    }

    @Override
    public void rateSubmitted(float rating, View loading, View responseContainer, TripItem tripItem) {
        bus.post(new Rate.RateRequest(application.getUser().getToken(), tripItem.getCar().getCarId(), rating, loading, responseContainer, tripItem));
        //TODO: submitRate To the server
    }

    @Subscribe
    public void onRateSubmitted(Rate.RateResponse response) {
        if (response.didSucceed()) {
            Toast.makeText(getActivity(), getString(R.string.thanks_for_rating), Toast.LENGTH_SHORT).show();
        } else {
            //TODO: Handle error
            response.showErrorToast(getActivity());
        }

        ((RatingBar) response.getResponseContainer().findViewById(R.id.item_trip_rate_response)).setRating(0);
        response.getResponseContainer().setVisibility(View.GONE);
        response.getLoading().setVisibility(View.GONE);
        response.getTripItem().setState(TripItem.NONE);
    }

}
