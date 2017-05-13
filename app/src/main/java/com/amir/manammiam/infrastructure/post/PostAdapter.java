package com.amir.manammiam.infrastructure.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.amir.manammiam.R;
import com.amir.manammiam.infrastructure.Utils;
import com.amir.manammiam.infrastructure.customView.TextViewFont;

import java.util.ArrayList;


public class PostAdapter extends BaseAdapter {

    //TODO: don't show who = 2 if this user has no car

    private static final String TAG = "PostAdapter";
    private ArrayList<ManamMiamPost> posts;
    private final LayoutInflater inflater;

    public PostAdapter(Context context) {
//        super(context);
        inflater = LayoutInflater.from(context);

        posts = new ArrayList<>();

    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public ManamMiamPost getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        super.getView(position, convertView, parent);
        final PostViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_post, parent, false);

            viewHolder = new PostViewHolder();
            viewHolder.infoContainer = convertView.findViewById(R.id.item_post_info_container);
            viewHolder.source = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_source);
            viewHolder.destination = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_destination);
            viewHolder.price = (TextViewFont) convertView.findViewById(R.id.include_src_dest_text_price);
            viewHolder.name = (TextViewFont) convertView.findViewById(R.id.item_post_text_name);
            viewHolder.time = (TextViewFont) convertView.findViewById(R.id.item_post_text_time);
            viewHolder.approvalContainer = (LinearLayout) convertView.findViewById(R.id.item_post_approval_container);
            viewHolder.rateBar = (RatingBar) convertView.findViewById(R.id.item_post_rate_bar);
            viewHolder.capacity = (TextViewFont) convertView.findViewById(R.id.item_post_text_capacity);
            viewHolder.capacityContainer = convertView.findViewById(R.id.item_post_capacity_container);
            viewHolder.carType = (TextViewFont) convertView.findViewById(R.id.item_post_text_car_type);
            viewHolder.carCode = (TextViewFont) convertView.findViewById(R.id.item_post_text_car_code);
            viewHolder.carColor = (TextViewFont) convertView.findViewById(R.id.item_post_text_car_color);
            viewHolder.text = (TextViewFont) convertView.findViewById(R.id.item_post_text_text);
            viewHolder.rateCount = (TextViewFont) convertView.findViewById(R.id.item_post_rate_count);
            viewHolder.expand = convertView.findViewById(R.id.item_post_expand);
            viewHolder.topContainer = convertView.findViewById(R.id.src_dest_container);
            viewHolder.loading = convertView.findViewById(R.id.item_post_loading);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (PostViewHolder) convertView.getTag();
        }

        final ManamMiamPost currPost = posts.get(position);
        viewHolder.source.setText(currPost.getSourceName());
        viewHolder.destination.setText(currPost.getDestinationName());
        viewHolder.price.setText(currPost.getPrice());
        viewHolder.name.setText(currPost.getSenderName());
        viewHolder.time.setText(currPost.getTime());
        viewHolder.capacity.setText(String.valueOf(currPost.getCapacity()));
        viewHolder.text.setText(currPost.getText());

        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.infoContainer.getVisibility() == View.GONE) {
                    Utils.expand(viewHolder.infoContainer, v);
                    currPost.setExpanded(true);
                } else {
                    Utils.collapse(viewHolder.infoContainer, v, null);
                    currPost.setExpanded(false);
                }

            }
        });

        if (currPost.isActivated()) {
            viewHolder.approvalContainer.setVisibility(View.VISIBLE);
            viewHolder.approvalContainer.setAlpha(1);
        } else {
            viewHolder.approvalContainer.setVisibility(View.GONE);
            viewHolder.approvalContainer.setAlpha(0);
        }

        //0 for false, 1 for true :)
        if (currPost.getWho() == ManamMiamPost.PASSENGER_ACCEPTED_A_SERVER) {
//            viewHolder.price.setVisibility(View.VISIBLE);
            viewHolder.infoContainer.setVisibility(View.GONE);
            viewHolder.expand.setVisibility(View.GONE);
            viewHolder.capacityContainer.setVisibility(View.VISIBLE);

        } else if (currPost.getWho() == ManamMiamPost.DRIVER_ASKING_PASSENGER) {
//            viewHolder.price.setVisibility(View.VISIBLE);
            viewHolder.infoContainer.setVisibility(View.VISIBLE);
            viewHolder.expand.setVisibility(View.VISIBLE);
            viewHolder.rateCount.setText(String.valueOf(currPost.getCar().getRateCount()));

            viewHolder.carColor.setText(currPost.getCar().getCarColor());
            viewHolder.carType.setText(currPost.getCar().getCarType());
            viewHolder.carCode.setText(currPost.getCar().getCarCode());
            viewHolder.rateBar.setRating(currPost.getCar().getRate());
            viewHolder.capacityContainer.setVisibility(View.VISIBLE);

        } else if (currPost.getWho() == ManamMiamPost.LOOKING_FOR_SERVER) {
//            viewHolder.price.setVisibility(View.GONE);
            viewHolder.price.setText("");
            viewHolder.infoContainer.setVisibility(View.GONE);
            viewHolder.capacityContainer.setVisibility(View.GONE);
            viewHolder.expand.setVisibility(View.GONE);
        }

        if (!currPost.isRead()) {
            viewHolder.topContainer.setBackgroundResource(R.drawable.round_header_primary);
        } else {
            viewHolder.topContainer.setBackgroundResource(R.drawable.round_header_read);
        }

        if (currPost.isExpanded()) {
//            expand(viewHolder.infoContainer, viewHolder.expand, true);
            viewHolder.infoContainer.setVisibility(View.VISIBLE);
            viewHolder.expand.setRotation(180);
        } else {
//            collapse(viewHolder.infoContainer, viewHolder.expand, true);
            viewHolder.expand.setRotation(0);
            viewHolder.infoContainer.setVisibility(View.GONE);
        }

//        if (currPost.isInLoadingState()) {
        viewHolder.loading.setVisibility(currPost.isInLoadingState() ? View.VISIBLE : View.GONE);
//        }

        return convertView;
    }


    public ArrayList<ManamMiamPost> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<ManamMiamPost> posts) {
        this.posts = posts;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public class PostViewHolder {
        TextViewFont source;
        TextViewFont destination;
        TextViewFont price;
        TextViewFont name;
        TextViewFont time;
        TextViewFont capacity;
        RatingBar rateBar;
        LinearLayout approvalContainer;
        TextViewFont carType;
        TextViewFont carColor;
        TextViewFont carCode;
        TextViewFont text;
        TextViewFont rateCount;
        View infoContainer;
        View expand;
        View topContainer;
        View capacityContainer;
        View loading;

        public LinearLayout getApprovalContainer() {
            return approvalContainer;
        }

        public View getLoading() {
            return loading;
        }
    }
}
