package com.amir.manammiam.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.infrastructure.services.ManamMiamService;
import com.amir.manammiam.infrastructure.services.ServiceAdapter;

import java.util.ArrayList;

public final class ServicesFragment extends BaseFragment {

    public static final int ANIM_DURATION = 350;
    private ServiceAdapter adapter;
    private ServiceAdapter.ServiceViewHolder lastTouchedView;

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

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        ListView listView = ((ListView) view.findViewById(R.id.fragment_services_list));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.e(getClass().getSimpleName(), "---------------------CRAP");

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
                        Log.e("ServiceFragment", String.valueOf(services.get(position).getRate()));
                    }
                });

                //todo: get server query's all the necessary info at once
            }
        });


//        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
//        tvLabel.setText(page + " -- " + title);
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
                Log.e("ServiceAdapter", "making it Gone");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
}
