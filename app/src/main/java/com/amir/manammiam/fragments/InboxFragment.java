package com.amir.manammiam.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.infrastructure.post.ManamMiamPost;
import com.amir.manammiam.infrastructure.post.PostAdapter;

import java.util.ArrayList;

import static com.amir.manammiam.R.string.services;
import static com.amir.manammiam.fragments.ServicesFragment.ANIM_DURATION;

public final class InboxFragment extends BaseFragment {

    private PostAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        ListView listView = (ListView) view.findViewById(R.id.fragment_inbox_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final ArrayList<ManamMiamPost> posts = adapter.getPosts();
                boolean wasActivated = posts.get(position).isActivated();

                PostAdapter.PostViewHolder viewHolder = (PostAdapter.PostViewHolder) view.getTag();
                if (wasActivated) {
                    posts.get(position).setActivated(false);
                    unSelectView(viewHolder);

                } else {
                    posts.get(position).setActivated(true);
                    viewHolder.getApprovalContainer().setVisibility(View.VISIBLE);
                    viewHolder.getApprovalContainer().setAlpha(0);
                    viewHolder.getApprovalContainer().animate().alpha(1).setDuration(ANIM_DURATION).setListener(null);
                }


            }
        });
        return view;
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
