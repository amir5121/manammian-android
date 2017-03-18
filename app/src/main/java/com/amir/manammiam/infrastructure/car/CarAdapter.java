package com.amir.manammiam.infrastructure.car;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.amir.manammiam.R;
import com.amir.manammiam.base.ManamMiamAdapter;
import com.amir.manammiam.infrastructure.Constants;
import com.amir.manammiam.infrastructure.customView.TextViewFont;

import java.util.ArrayList;

public class CarAdapter extends ManamMiamAdapter {
    private final String BLOCKED_STRING;
    private final String ALL_STRING;
    private final String FEMALE_STRING;
    private final String UNVERIFIED_STRING;
    private final String MALE_STRING;
    private final String ERROR_STRING;
    private final LayoutInflater inflater;
    private ArrayList<Car> cars;
    private boolean hasFooter;
    private View footer;
    private CarViewHolder lastActivatedView;
    private boolean isFirstLoad;

    public CarAdapter(Context context, boolean hasFooter) {
        super(context);
        inflater = LayoutInflater.from(context);
        cars = new ArrayList<>();
        isFirstLoad = true;
        this.hasFooter = hasFooter;
        ERROR_STRING = context.getString(R.string.error);
        UNVERIFIED_STRING = context.getString(R.string.unverified);
        MALE_STRING = context.getString(R.string.male);
        FEMALE_STRING = context.getString(R.string.female);
        ALL_STRING = context.getString(R.string.all);
        BLOCKED_STRING = context.getString(R.string.blocked);
    }

    @Override
    public int getCount() {
        if (hasFooter)
            return cars.size() + 1;
        else return cars.size();
    }

    @Override
    public Car getItem(int position) {
//        if (position > cars.size()) return null;
        return cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);

        if (hasFooter)
            if (position == cars.size()) {
                if (footer == null) footer = inflater.inflate(R.layout.item_car_footer, parent, false);
                return footer;
            }

        CarViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_car, parent, false);
            viewHolder = getCarViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CarViewHolder) convertView.getTag();
            if (viewHolder == null) {
                convertView = inflater.inflate(R.layout.item_car, parent, false);
                viewHolder = getCarViewHolder(convertView);

                convertView.setTag(viewHolder);
            }
        }

//        if (viewHolder == null) return convertView;

        Car currCar = cars.get(position);
        viewHolder.carCode.setText(currCar.getCarCode());
        viewHolder.carColor.setText(currCar.getCarColor());
        viewHolder.carType.setText(currCar.getCarType());
        viewHolder.rateCount.setText(String.valueOf(currCar.getRateCount()));
        viewHolder.rateBar.setRating(currCar.getRate());

        if (currCar.isViewActivated()) {
            viewHolder.activationContainer.setVisibility(View.VISIBLE);
        } else {
            viewHolder.activationContainer.setVisibility(View.GONE);
        }

        switch (currCar.getGenderAccepted()) {
            case Car.ACCEPT_ALL:
                viewHolder.genderContainer.setBackgroundResource(R.drawable.round_background_transparent_green);
                viewHolder.genderText.setText(ALL_STRING);
                break;
            case Car.MALE:
                viewHolder.genderContainer.setBackgroundResource(R.drawable.round_background_transparent_green);
                viewHolder.genderText.setText(MALE_STRING);
                break;
            case Car.FEMALE:
                viewHolder.genderContainer.setBackgroundResource(R.drawable.round_background_transparent_green);
                viewHolder.genderText.setText(FEMALE_STRING);
                break;
            case Car.NO_VERIFIED_CAR:
                viewHolder.genderContainer.setBackgroundResource(R.drawable.round_background_transparent_yellow);
                viewHolder.genderText.setText(UNVERIFIED_STRING);
                break;
            case Car.BLOCKED:
                viewHolder.genderContainer.setBackgroundResource(R.drawable.round_background_transparent_red);
                viewHolder.genderText.setText(BLOCKED_STRING);
                break;
            default:
                viewHolder.genderContainer.setBackgroundResource(R.drawable.round_background_transparent_red);
                viewHolder.genderText.setText(ERROR_STRING);
                break;
        }
        if (isFirstLoad && position == 0) {
            lastActivatedView = viewHolder;
            isFirstLoad = false;
        }
        return convertView;
    }

    @NonNull
    private CarViewHolder getCarViewHolder(View convertView) {
        CarViewHolder viewHolder;
        viewHolder = new CarViewHolder();

        viewHolder.carType = ((TextViewFont) convertView.findViewById(R.id.item_car_text_car_type));
        viewHolder.carColor = ((TextViewFont) convertView.findViewById(R.id.item_car_text_car_color));
        viewHolder.carCode = ((TextViewFont) convertView.findViewById(R.id.item_car_text_car_code));
        viewHolder.rateCount = ((TextViewFont) convertView.findViewById(R.id.item_car_text_rate_count));
        viewHolder.rateBar = ((RatingBar) convertView.findViewById(R.id.item_car_rate));
        viewHolder.genderText = ((TextViewFont) convertView.findViewById(R.id.item_car_text_gender));
        viewHolder.genderContainer = convertView.findViewById(R.id.item_car_gender_container);
        viewHolder.activationContainer = convertView.findViewById(R.id.item_car_selected);
        return viewHolder;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        isFirstLoad = true;
    }

    public void setSelectedCar(int position, View view) {
        for (int i = 0; i < cars.size(); i++) {
            Car currCar = cars.get(i);
            if (i == position) {
                if (!currCar.isViewActivated()) {
                    CarViewHolder viewHolder = (CarViewHolder) view.getTag();
                    currCar.setViewActivated(true);
                    viewHolder.setActivated(true);

                    if (lastActivatedView != null)
                        lastActivatedView.setActivated(false);

                    lastActivatedView = viewHolder;
                }
            } else {
                currCar.setViewActivated(false);

            }
        }
    }

    private class CarViewHolder {
        TextViewFont carType;
        TextViewFont carColor;
        TextViewFont carCode;
        TextViewFont rateCount;
        RatingBar rateBar;
        TextViewFont genderText;
        View genderContainer;
        View activationContainer;

        void setActivated(boolean b) {
            if (b) {
                activationContainer.setAlpha(0);
                activationContainer.setVisibility(View.VISIBLE);
                activationContainer.animate().setDuration(Constants.ANIMATION_DURATION).alpha(1).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        activationContainer.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            } else {
                activationContainer.animate().setDuration(Constants.ANIMATION_DURATION).alpha(0).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        activationContainer.setVisibility(View.GONE);

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
    }
}
