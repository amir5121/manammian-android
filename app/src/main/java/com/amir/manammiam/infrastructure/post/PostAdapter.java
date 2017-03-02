package com.amir.manammiam.infrastructure.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.amir.manammiam.R;
import com.amir.manammiam.infrastructure.customView.TextViewFont;

import java.util.ArrayList;


public class PostAdapter extends BaseAdapter {

    //TODO: don't show who = 2 if this user has no car

    private static final String TAG = "PostAdapter";
    private static final long ANIME_DURATION = 400;
    private ArrayList<ManamMiamPost> posts;
    private final LayoutInflater inflater;

    public PostAdapter(Context context) {
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
        final PostViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_post, parent, false);
            final View finalConvertView = convertView;

            viewHolder = new PostViewHolder();
            viewHolder.infoContainer = finalConvertView.findViewById(R.id.item_post_info_container);
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
            viewHolder.expand = convertView.findViewById(R.id.item_post_expand);
            viewHolder.topContainer = convertView.findViewById(R.id.src_dest_container);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (PostViewHolder) convertView.getTag();
        }

        viewHolder.source.setText(posts.get(position).getSourceName());
        viewHolder.destination.setText(posts.get(position).getDestinationName());
        viewHolder.price.setText(posts.get(position).getPrice());
        viewHolder.name.setText(posts.get(position).getSenderName());
        viewHolder.time.setText(posts.get(position).getTime());
        viewHolder.capacity.setText(String.valueOf(posts.get(position).getCapacity()));
        viewHolder.text.setText(posts.get(position).getText());

        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.infoContainer.getVisibility() == View.GONE) {
                    expand(viewHolder.infoContainer, v, false);
                    posts.get(position).setExpanded(true);
                } else {
                    collapse(viewHolder.infoContainer, v, false);
                    posts.get(position).setExpanded(false);
                }

            }
        });

        if (posts.get(position).isActivated()) {
            viewHolder.approvalContainer.setVisibility(View.VISIBLE);
            viewHolder.approvalContainer.setAlpha(1);
        } else {
            viewHolder.approvalContainer.setVisibility(View.GONE);
            viewHolder.approvalContainer.setAlpha(0);
        }

        //0 for false, 1 for true :)
        if (posts.get(position).getWho() == ManamMiamPost.PASSENGER_CHOSEN_A_SERVER) {
            viewHolder.price.setVisibility(View.VISIBLE);
            viewHolder.infoContainer.setVisibility(View.GONE);
            viewHolder.expand.setVisibility(View.GONE);
            viewHolder.capacityContainer.setVisibility(View.VISIBLE);

        } else if (posts.get(position).getWho() == ManamMiamPost.DRIVER_ASKING_PASSENGER) {
            viewHolder.price.setVisibility(View.VISIBLE);
            viewHolder.infoContainer.setVisibility(View.VISIBLE);
            viewHolder.expand.setVisibility(View.VISIBLE);

            viewHolder.carColor.setText(posts.get(position).getCar().getCarColor());
            viewHolder.carType.setText(posts.get(position).getCar().getCarType());
            viewHolder.carCode.setText(posts.get(position).getCar().getCarCode());
            viewHolder.rateBar.setRating(posts.get(position).getCar().getRate());
            viewHolder.capacityContainer.setVisibility(View.VISIBLE);

        } else if (posts.get(position).getWho() == ManamMiamPost.LOOKING_FOR_SERVER) {
            viewHolder.price.setVisibility(View.GONE);
            viewHolder.infoContainer.setVisibility(View.GONE);
            viewHolder.capacityContainer.setVisibility(View.GONE);
            viewHolder.expand.setVisibility(View.GONE);
        }

        if (posts.get(position).isRead()) {
            viewHolder.topContainer.setBackgroundResource(R.drawable.round_header_primary);
        } else {
            viewHolder.topContainer.setBackgroundResource(R.drawable.round_header_read);
        }

        if (posts.get(position).isExpanded()) {
//            expand(viewHolder.infoContainer, viewHolder.expand, true);
            viewHolder.infoContainer.setVisibility(View.VISIBLE);
            viewHolder.expand.setRotation(180);
        } else {
//            collapse(viewHolder.infoContainer, viewHolder.expand, true);
            viewHolder.expand.setRotation(0);
            viewHolder.infoContainer.setVisibility(View.GONE);

        }

        return convertView;
    }

    private static void expand(final View v, final View expandButton, boolean immediate) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
                expandButton.setRotation(interpolatedTime * 180);

            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
//        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        if (immediate)
            a.setDuration(0);
        else
            a.setDuration(ANIME_DURATION);

        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }

    private static void collapse(final View v, final View expandButton, final boolean immediate) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                    if (immediate)
                        expandButton.setRotation(interpolatedTime * 180);
                    else expandButton.setRotation(180 + interpolatedTime * 180);

                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
//        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        if (immediate)
            a.setDuration(0);
        else
            a.setDuration(ANIME_DURATION);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
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
        View infoContainer;
        View expand;
        View topContainer;
        View capacityContainer;

        public LinearLayout getApprovalContainer() {
            return approvalContainer;
        }
    }
}
