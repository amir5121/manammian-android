package com.amir.manammiam.infrastructure.car;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;

import com.amir.manammiam.R;
import com.amir.manammiam.infrastructure.customView.TextViewFont;

import java.util.ArrayList;

public class CarAdapter extends BaseAdapter {
    private final String BLOCKED_STRING;
    private final String ALL_STRING;
    private final String FEMALE_STRING;
    private final String UNVERIFIED_STRING;
    private final String MALE_STRING;
    private final String ERROR_STRING;
    private final LayoutInflater inflater;
    ArrayList<Car> cars;

    public CarAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        cars = new ArrayList<>();
        ERROR_STRING = context.getString(R.string.error);
        UNVERIFIED_STRING = context.getString(R.string.unverified);
        MALE_STRING = context.getString(R.string.male);
        FEMALE_STRING = context.getString(R.string.female);
        ALL_STRING = context.getString(R.string.all);
        BLOCKED_STRING = context.getString(R.string.blocked);
    }

    @Override
    public int getCount() {
        return cars.size();
    }

    @Override
    public Car getItem(int position) {
        return cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_car, parent, false);
            viewHolder = new CarViewHolder();
            
            viewHolder.carType = ((TextViewFont) convertView.findViewById(R.id.item_car_text_car_type));
            viewHolder.carColor = ((TextViewFont) convertView.findViewById(R.id.item_car_text_car_color));
            viewHolder.carCode = ((TextViewFont) convertView.findViewById(R.id.item_car_text_car_code));
            viewHolder.rateCount = ((TextViewFont) convertView.findViewById(R.id.item_car_text_rate_count));
            viewHolder.rateBar = ((RatingBar) convertView.findViewById(R.id.item_car_rate));
            viewHolder.genderText = ((TextViewFont) convertView.findViewById(R.id.item_car_text_gender));
            viewHolder.genderContainer = convertView.findViewById(R.id.item_car_gender_container);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CarViewHolder) convertView.getTag();
        }
        Car currCar = cars.get(position);
        viewHolder.carCode.setText(currCar.getCarCode());
        viewHolder.carColor.setText(currCar.getCarColor());
        viewHolder.carType.setText(currCar.getCarType());
        viewHolder.rateCount.setText(String.valueOf(currCar.getRateCount()));
        viewHolder.rateBar.setRating(currCar.getRate());

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


        return convertView;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    private class CarViewHolder {
        TextViewFont carType;
        TextViewFont carColor;
        TextViewFont carCode;
        TextViewFont rateCount;
        RatingBar rateBar;
        TextViewFont genderText;
        View genderContainer;
    }
}
