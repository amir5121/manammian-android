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
import android.widget.Toast;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.dialogFragment.NewServiceDialogFragment;
import com.amir.manammiam.infrastructure.post.ManamMiamPost;
import com.amir.manammiam.infrastructure.post.PostAdapter;
import com.amir.manammiam.services.Posts;
import com.squareup.otto.Subscribe;

import static com.amir.manammiam.fragments.ServicesFragment.ANIM_DURATION;

public final class InboxFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "Inbox Fragment";
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

        final ListView listView = (ListView) view.findViewById(R.id.fragment_with_list_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                
                final ManamMiamPost manamMiamPostItem = adapter.getPosts().get(position);
                boolean wasActivated = manamMiamPostItem.isActivated();

                final PostAdapter.PostViewHolder viewHolder = (PostAdapter.PostViewHolder) view.getTag();
                if (wasActivated) {

                    manamMiamPostItem.setActivated(false);
                    unSelectView(viewHolder);

                } else {
                    if (manamMiamPostItem.getWho() == ManamMiamPost.LOOKING_FOR_SERVER) {
                        //TODO: check if the user is a driver...
                        NewServiceDialogFragment serviceDialog =
                                NewServiceDialogFragment.getInstance(
                                        manamMiamPostItem.getSourceName(),
                                        manamMiamPostItem.getDestinationName(),
                                        manamMiamPostItem.getTime(),
                                        manamMiamPostItem.getSourceId(),
                                        manamMiamPostItem.getDestinationId(),
                                        manamMiamPostItem.getPassengerId());

                        serviceDialog.show(getFragmentManager(), "serviceDialog");
                    } else if (manamMiamPostItem.getWho() == ManamMiamPost.DRIVER_ASKING_PASSENGER) {
                        manamMiamPostItem.setActivated(true);
                        viewHolder.getApprovalContainer().setVisibility(View.VISIBLE);
                        viewHolder.getApprovalContainer().setAlpha(0);
                        viewHolder.getApprovalContainer().animate().alpha(1).setDuration(ANIM_DURATION).setListener(null);

                        view.findViewById(R.id.item_post_text_accept).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                manamMiamPostItem.setLoadingState(true);
                                manamMiamPostItem.setActivated(false);
                                viewHolder.getLoading().setVisibility(View.VISIBLE);
                                viewHolder.getApprovalContainer().animate().alpha(0).setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        viewHolder.getApprovalContainer().setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });

                                bus.post(new Posts.AcceptRequest(manamMiamPostItem, viewHolder.getLoading(), application.getUser().getToken()));
//                                bus.post(new Posts.AcceptRequest(manamMiamPostItem, viewHolder.getLoading(), application.getUser().getToken()));
                                view.findViewById(R.id.item_post_text_accept).setOnClickListener(null);
                            }
                        });

                    } else if (manamMiamPostItem.getWho() == ManamMiamPost.PASSENGER_ACCEPTED_A_SERVER) {
                        //nothing need to happen i guess

                        //this bit just ran and no-one cared
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

    @Subscribe
    public void onAcceptResponseRecevied(Posts.AcceptResponse response) {
        if (response.didSucceed()) {
            Toast.makeText(getContext(), getString(R.string.accept_request_submitted), Toast.LENGTH_SHORT).show();
        } else {
            response.showErrorToast(getContext());
        }
        response.getPost().setLoadingState(false);
        response.getPost().setActivated(false);

        response.getLoading().setVisibility(View.GONE);
    }
}
