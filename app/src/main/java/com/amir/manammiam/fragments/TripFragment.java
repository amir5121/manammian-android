package com.amir.manammiam.fragments;

import android.animation.Animator;
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
import com.amir.manammiam.base.BaseActivity;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.dialogFragment.ReportDialogFragment;
import com.amir.manammiam.infrastructure.Constants;
import com.amir.manammiam.infrastructure.SwitchRequest;
import com.amir.manammiam.infrastructure.trip.Trip;
import com.amir.manammiam.infrastructure.trip.TripAdapter;
import com.amir.manammiam.infrastructure.trip.TripCallbacks;
import com.amir.manammiam.infrastructure.trip.PassengerTrip;
import com.amir.manammiam.services.MMTime;
import com.amir.manammiam.services.Rate;
import com.amir.manammiam.services.Trips;
import com.squareup.otto.Subscribe;

public class TripFragment extends BaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, TripCallbacks {
    private static final String TAG = "TripFragment";
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
        adapter = new TripAdapter((BaseActivity) getActivity(), this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        swipeRefresh = ((SwipeRefreshLayout) view.findViewById(R.id.fragment_with_list_swipe_refresh));
        swipeRefresh.setOnRefreshListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary, getActivity().getTheme()));
        } else {
            swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        }
        refreshTrips();
        return view;
    }

    private void refreshTrips() {
        swipeRefresh.setRefreshing(true);
        bus.post(new Trips.TripRequest(application.getUser().getToken()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        final Trip trip = adapter.getTrips().get(position);
        boolean hasBeenPickedUp = (trip.getCar() != null);
        final View cancelContainer = view.findViewById(R.id.item_server_approval_container);
        final View responseContainer = view.findViewById(R.id.item_trip_response_container);
        View loadingContainer = view.findViewById(R.id.item_trip_loading);
        switch (trip.getState()) {
            case Trip.NONE:
                if (hasBeenPickedUp) {
                    loadingContainer.setAlpha(0);
                    loadingContainer.setVisibility(View.VISIBLE);
                    loadingContainer.animate().alpha(1).setDuration(Constants.ANIMATION_DURATION).start();
                    trip.setState(Trip.LOADING);
                    bus.post(new MMTime.Request(application.getUser().getToken(), trip.getTime(), cancelContainer, responseContainer, loadingContainer, trip));
                } else {
                    cancelContainer.setAlpha(0);
                    cancelContainer.setVisibility(View.VISIBLE);
                    cancelContainer.animate().alpha(1).setDuration(Constants.ANIMATION_DURATION).start();
                    trip.setState(Trip.CANCELING);
                }
                ((TripAdapter.TripViewHolder) view.getTag()).getTelegram().setClickable(false);
                break;
            case Trip.LOADING:
                //Won't happen
                break;
            case Trip.CANCELING:
                cancelContainer.animate().alpha(0).setDuration(Constants.ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        cancelContainer.setVisibility(View.GONE);
                        trip.setState(Trip.NONE);
                        cancelContainer.animate().setListener(null);
                        ((TripAdapter.TripViewHolder) view.getTag()).getTelegram().setClickable(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
            case Trip.REPORT_RATE:
                responseContainer.animate().alpha(0).setDuration(Constants.ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        responseContainer.setVisibility(View.GONE);
                        responseContainer.animate().setListener(null);
                        ((TripAdapter.TripViewHolder) view.getTag()).getTelegram().setClickable(true);

                        trip.setState(Trip.NONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
        }
    }

    @Subscribe
    public void onTimeReceived(final MMTime.TimeResponse response) {
        if (response.didSucceed()) {
            if (response.isInTheFuture()) {
                if (response.getTrip() instanceof PassengerTrip) {
                    response.getCancelContainer().setAlpha(0);
                    response.getCancelContainer().setVisibility(View.VISIBLE);
                    response.getCancelContainer().animate().setDuration(Constants.ANIMATION_DURATION).alpha(1).start();
                    response.getTrip().setState(Trip.CANCELING);
                } else {
                    response.getTrip().setState(Trip.NONE);
                }

            } else {
                response.getResponseContainer().setAlpha(0);
                response.getResponseContainer().setVisibility(View.VISIBLE);
                response.getResponseContainer().animate().setDuration(Constants.ANIMATION_DURATION).alpha(1).start();
                response.getTrip().setState(Trip.REPORT_RATE);
            }
        } else {

            response.showErrorToast(getContext());
            //TODO: handle Error
        }

        response.getLoadingContainer().animate().alpha(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                response.getLoadingContainer().setVisibility(View.GONE);
                response.getLoadingContainer().animate().setListener(null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
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
        refreshTrips();
    }

    @Override
    public void reportService(PassengerTrip passengerTrip) {
        ReportDialogFragment.newInstance(passengerTrip.getServerID()).show(getFragmentManager(), "ReportDialog");
    }

    @Override
    public void rateSubmitted(float rating, View loading, View responseContainer, PassengerTrip passengerTrip) {
        bus.post(new Rate.RateRequest(application.getUser().getToken(), rating, loading, responseContainer, passengerTrip));
    }

    @Override
    public void cancelService(Trip item, View loading, View cancelContainer) {
        bus.post(new Trips.CancelRequest(item, loading, cancelContainer, application.getUser().getToken()));
    }

    @Subscribe
    public void onRateSubmitted(final Rate.RateResponse response) {
        if (response.didSucceed()) {
            Toast.makeText(getActivity(), getString(R.string.thanks_for_rating), Toast.LENGTH_SHORT).show();
        } else {
            //TODO: Handle error
            response.showErrorToast(getActivity());
        }
        ((RatingBar) response.getResponseContainer().findViewById(R.id.item_trip_rate_response)).setRating(0);

        response.getResponseContainer().setVisibility(View.GONE);
        response.getLoading().setVisibility(View.GONE);
        response.getPassengerTrip().setState(Trip.NONE);
    }


    @Subscribe
    public void onCancelResponseReceived(Trips.CancelResponse response) {
        if (response.didSucceed()) {
            Toast.makeText(getContext(), getString(R.string.trip_canceled), Toast.LENGTH_SHORT).show();
            refreshTrips();
            //TODO: after cancelling the trip remove it from the list view
            // TODO: after cancelling the trip notify the driver that a passenger canceled it's trip

        } else {
            response.showErrorToast(getContext());
            //TODO: handle errors
        }

        response.getCancelContainer().setVisibility(View.GONE);
        response.getLoading().setVisibility(View.GONE);
        response.getTrip().setState(Trip.NONE);
    }


    @Subscribe
    public void switchToFragment(SwitchRequest request) {
        //this is just a hack.. if this fragment is live the and a request to switch to it is made it will also refresh it here
        if (request.pageNumber == Constants.TRIPS_FRAGMENT_PAGE_NUMBER)
            refreshTrips();
    }

}