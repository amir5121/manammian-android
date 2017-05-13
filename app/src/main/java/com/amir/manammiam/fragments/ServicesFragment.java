package com.amir.manammiam.fragments;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.infrastructure.services.ManamMiamService;
import com.amir.manammiam.infrastructure.services.ServiceAdapter;
import com.amir.manammiam.services.Services;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public final class ServicesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final int ANIM_DURATION = 350;
    private static final String IS_REFRESHABLE = "IS_REFRESHABLE";
    private ServiceAdapter adapter;
    private ServiceAdapter.ServiceViewHolder lastTouchedView;
    private SwipeRefreshLayout swipeRefresh;

    // newInstance constructor for creating fragment with arguments
    public static ServicesFragment newInstance(boolean isRefreshable) {
        ServicesFragment fragmentFirst = new ServicesFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_REFRESHABLE, isRefreshable);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getArguments().getInt("someInt", 0);

    }

    @Subscribe
    public void onServicesReceived(Services.ServicesResponse response) {
        if (response.didSucceed()) {
            adapter.setServices(response.getServices());
            adapter.notifyDataSetChanged();
        } else {
            //TODO: Handle errors
        }

        swipeRefresh.setRefreshing(false);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_with_list, container, false);
        swipeRefresh = ((SwipeRefreshLayout) view.findViewById(R.id.fragment_with_list_swipe_refresh));
        boolean isRefreshable = getArguments().getBoolean(IS_REFRESHABLE);
        adapter = new ServiceAdapter(getActivity(), !isRefreshable);

        swipeRefresh.setEnabled(isRefreshable);

        swipeRefresh.setOnRefreshListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary, getActivity().getTheme()));
        } else {
            swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        }
        swipeRefresh.setRefreshing(isRefreshable);

        ListView listView = ((ListView) view.findViewById(R.id.fragment_with_list_list));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final ArrayList<ManamMiamService> services = adapter.getServices();

                if (lastTouchedView != null) {
                    deselectView(lastTouchedView);
                }

                lastTouchedView = (ServiceAdapter.ServiceViewHolder) view.getTag();

                for (int i = 0; i < services.size(); i++) {
                    if (services.get(i).getState() == ManamMiamService.RESERVING && i != position) {
                        services.get(i).setState(ManamMiamService.NONE);
                    }
                }

                final ServiceAdapter.ServiceViewHolder viewHolder = (ServiceAdapter.ServiceViewHolder) view.getTag();
                final ManamMiamService currService = services.get(position);
                if (currService.getState() == ManamMiamService.RESERVING) {
                    currService.setState(ManamMiamService.NONE);
                    deselectView(viewHolder);

                } else {
                    currService.setState(ManamMiamService.RESERVING);
                    viewHolder.getApprovalContainer().setVisibility(View.VISIBLE);
                    viewHolder.getApprovalContainer().setAlpha(0);
                    viewHolder.getApprovalContainer().animate().alpha(1).setDuration(ANIM_DURATION).setListener(null);
                }

                view.findViewById(R.id.item_server_text_accept).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.getLoading().setVisibility(View.VISIBLE);
                        deselectView(viewHolder);
                        currService.setState(ManamMiamService.LOADING);
                        bus.post(new Services.ReserveRequest(currService, viewHolder.getLoading(), application.getUser().getToken()));
                        //TODO: submit to server
                    }
                });

            }
        });

        if (isRefreshable)
            bus.post(new Services.ServicesRequest(application.getUser().getToken(), application.getUser().getGender()));
        return view;
    }

    private void deselectView(final ServiceAdapter.ServiceViewHolder view) {

        view.getApprovalContainer().animate().alpha(0).setDuration(ANIM_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.getApprovalContainer().setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    @Override
    public void onRefresh() {
        bus.post(new Services.ServicesRequest(application.getUser().getToken(), application.getUser().getGender()));
    }

    public ServiceAdapter getAdapter() {
        return adapter;
    }

    @Subscribe
    public void onReserveResponseReceived(Services.ReserveResponse response) {
        if (response.didSucceed()) {
            Toast.makeText(getContext(), getString(R.string.service_reserved), Toast.LENGTH_SHORT).show();
        } else {
            response.showErrorToast(getContext());
        }

        response.getService().setState(ManamMiamService.NONE);
        response.getLoading().setVisibility(View.GONE);
    }
}
