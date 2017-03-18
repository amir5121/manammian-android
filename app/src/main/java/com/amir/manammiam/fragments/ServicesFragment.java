package com.amir.manammiam.fragments;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.infrastructure.ScrollCallback;
import com.amir.manammiam.infrastructure.services.ManamMiamService;
import com.amir.manammiam.infrastructure.services.ServiceAdapter;
import com.amir.manammiam.services.Services;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public final class ServicesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    public static final int ANIM_DURATION = 350;
    private ServiceAdapter adapter;
    private ServiceAdapter.ServiceViewHolder lastTouchedView;
    private SwipeRefreshLayout swipeRefresh;

    // newInstance constructor for creating fragment with arguments
    public static ServicesFragment newInstance(int page, String title) {
        ServicesFragment fragmentFirst = new ServicesFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ServiceAdapter(getActivity());
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
        swipeRefresh.setOnRefreshListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary, getActivity().getTheme()));
        } else {
            swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        }
        swipeRefresh.setRefreshing(true);

        ListView listView = ((ListView) view.findViewById(R.id.fragment_with_list_list));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final ArrayList<ManamMiamService> services = adapter.getServices();
                boolean wasActivated = services.get(position).isActivated();

                if (lastTouchedView != null) {
                    unSelectView(lastTouchedView);
                }
                lastTouchedView = (ServiceAdapter.ServiceViewHolder) view.getTag();

                for (int i = 0; i <services.size(); i++) {
                    if (services.get(i).isActivated() && i != position) {
                        services.get(i).setActivated(false);
                    }
                }

                ServiceAdapter.ServiceViewHolder viewHolder = (ServiceAdapter.ServiceViewHolder) view.getTag();
                if (wasActivated) {
                    services.get(position).setActivated(false);
                    unSelectView(viewHolder);

                } else {
                    services.get(position).setActivated(true);
                    viewHolder.getApprovalContainer().setVisibility(View.VISIBLE);
                    viewHolder.getApprovalContainer().setAlpha(0);
                    viewHolder.getApprovalContainer().animate().alpha(1).setDuration(ANIM_DURATION).setListener(null);
                }

                view.findViewById(R.id.item_server_text_accept).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: implement
                    }
                });

                //todo: get server query's all the necessary info at once
            }
        });

        bus.post(new Services.ServicesRequest(application.getUser().getToken()));
        return view;
    }

    private void unSelectView(final ServiceAdapter.ServiceViewHolder view) {

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
        bus.post(new Services.ServicesRequest(application.getUser().getToken()));
    }
}
