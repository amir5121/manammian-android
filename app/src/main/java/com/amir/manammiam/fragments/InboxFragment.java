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

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.dialogFragment.NewServiceDialogFragment;
import com.amir.manammiam.infrastructure.post.ManamMiamPost;
import com.amir.manammiam.infrastructure.post.PostAdapter;
import com.amir.manammiam.services.Posts;
import com.squareup.otto.Subscribe;

import static com.amir.manammiam.fragments.ServicesFragment.ANIM_DURATION;

public final class InboxFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private PostAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    // newInstance constructor for creating fragment with arguments
    public static InboxFragment newInstance(int page, String title) {
        InboxFragment fragmentFirst = new InboxFragment();
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
        getArguments().getInt("someInt", 0);

        adapter = new PostAdapter(getActivity());


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_with_list, container, false);
        setUpView(view);
        updatePosts();
        return view;
    }


    @Subscribe
    public void PostRecieved(Posts.PostResponse response) {
        if (response.didSucceed()) {
            //TODO: add these to database
            //TODO: Should send a notification?
            adapter.setPosts(response.getPosts());
            adapter.notifyDataSetChanged();
        } else {
            response.showErrorToast(getActivity());
            //TODO: handle error
        }
//        if (swipeRefresh != null)
        swipeRefresh.setRefreshing(false);
    }

    private void updatePosts() {
        swipeRefresh.setRefreshing(true);
        bus.post(new Posts.PostsRequest(application.getUser().getToken()));
    }

    private void setUpView(View view) {
        swipeRefresh = ((SwipeRefreshLayout) view.findViewById(R.id.fragment_with_list_swipe_refresh));
        if (swipeRefresh != null) {
            swipeRefresh.setOnRefreshListener(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary, getActivity().getTheme()));
            } else {
                swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
            }
        }

        ListView listView = (ListView) view.findViewById(R.id.fragment_with_list_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO: What should actually happen when you click on a post

                ManamMiamPost manamMiamPostItem = adapter.getPosts().get(position);
                boolean wasActivated = manamMiamPostItem.isActivated();

                PostAdapter.PostViewHolder viewHolder = (PostAdapter.PostViewHolder) view.getTag();
                if (wasActivated) {

                    manamMiamPostItem.setActivated(false);
                    unSelectView(viewHolder);

                } else {
                    if (manamMiamPostItem.getWho() == ManamMiamPost.LOOKING_FOR_SERVER) {
                        NewServiceDialogFragment serviceDialog = new NewServiceDialogFragment();

                        serviceDialog.show(getFragmentManager(), "serviceDialog");
                    } else if (manamMiamPostItem.getWho() == ManamMiamPost.DRIVER_ASKING_PASSENGER) {
                        manamMiamPostItem.setActivated(true);
                        viewHolder.getApprovalContainer().setVisibility(View.VISIBLE);
                        viewHolder.getApprovalContainer().setAlpha(0);
                        viewHolder.getApprovalContainer().animate().alpha(1).setDuration(ANIM_DURATION).setListener(null);
                    } else if (manamMiamPostItem.getWho() == ManamMiamPost.PASSENGER_CHOSEN_A_SERVER) {
                        //nothing
                    }
                }


            }
        });
    }

    @Override
    public void onRefresh() {
        updatePosts();
    }

    private void unSelectView(final PostAdapter.PostViewHolder view) {

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
