package com.amir.manammiam.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.infrastructure.Car;
import com.amir.manammiam.infrastructure.User;
import com.amir.manammiam.infrastructure.customView.TextViewFont;
import com.amir.manammiam.services.Account;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public final class ProfileFragment extends BaseFragment {

    private TextViewFont nameText;
    private TextViewFont usernameText;
    private TextViewFont emailText;
    private TextViewFont genderText;
    private TextViewFont permissionText;
    private View verificationContainer;
    private View loadingContainer;
    private LinearLayout carsContainer;
    private View carsProgressBar;
    private boolean carsReceived;

    // newInstance constructor for creating fragment with arguments
    public static ProfileFragment newInstance(int page, String title) {
        ProfileFragment fragmentFirst = new ProfileFragment();
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

        //getArguments().getInt("someInt", 0);

    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        bus.unregister(this);
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        bus.register(this);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameText = ((TextViewFont) view.findViewById(R.id.fragment_profile_text_name));
        usernameText = ((TextViewFont) view.findViewById(R.id.fragment_profile_text_username));
        emailText = ((TextViewFont) view.findViewById(R.id.fragment_profile_text_email));
        genderText = ((TextViewFont) view.findViewById(R.id.fragment_profile_text_gender));
        permissionText = ((TextViewFont) view.findViewById(R.id.fragment_profile_text_permission));
        verificationContainer = view.findViewById(R.id.fragment_profile_verification_container);
        loadingContainer = view.findViewById(R.id.fragment_profile_progress_bar_container);
        carsContainer = ((LinearLayout) view.findViewById(R.id.fragment_profile_cars_container));
        carsProgressBar = view.findViewById(R.id.fragment_profile_cars_progress_bar);
        carsReceived = false;

        Log.e(getClass().getSimpleName(), "posting request for profile and cars");
        bus.post(new Account.ProfileRequest(application.getUser().getToken()));
        bus.post(new Account.CarsRequest(application.getUser().getToken()));
        return view;
    }

    @Subscribe
    public void onCarsRecieved(Account.CarsResponse response) {
        carsReceived = true;
        if (response.didSucceed()) {
//            if (carsContainer != null)
            addCars(response.getCars());
            carsProgressBar.setVisibility(View.GONE );
        } else {

        }
    }

    private void addCars(ArrayList<Car> cars) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        carsContainer.removeAllViews();
        for (Car car : cars) {
            View view = inflater.inflate(R.layout.item_car, carsContainer, false);
            ((RatingBar) view.findViewById(R.id.item_car_rate)).setRating(car.getRate());
            ((TextViewFont) view.findViewById(R.id.item_car_text_car_type)).setText(car.getCarType());
            ((TextViewFont) view.findViewById(R.id.item_car_text_car_code)).setText(car.getCarCode());
            ((TextViewFont) view.findViewById(R.id.item_car_text_car_color)).setText(car.getCarColor());

            ((TextViewFont) view.findViewById(R.id.item_car_text_rate_count)).setText(String.valueOf(car.getRateCount()));

            int gender = car.getGenderAccepted();
            TextViewFont genderText = ((TextViewFont) view.findViewById(R.id.item_car_text_gender));
            View genderContainer = view.findViewById(R.id.item_car_gender_container);
            switch (gender) {
                case Car.ACCEPT_ALL:
                    genderContainer.setBackgroundResource(R.drawable.round_background_transparent_green);
                    genderText.setText(getResources().getString(R.string.all));
                    break;
                case Car.MALE:
                    genderContainer.setBackgroundResource(R.drawable.round_background_transparent_green);
                    genderText.setText(getResources().getString(R.string.male));
                    break;
                case Car.FEMALE:
                    genderContainer.setBackgroundResource(R.drawable.round_background_transparent_green);
                    genderText.setText(getResources().getString(R.string.female));
                    break;
                case Car.NO_VERIFIED_CAR:
                    genderContainer.setBackgroundResource(R.drawable.round_background_transparent_yellow);
                    genderText.setText(getResources().getString(R.string.unverified));
                    break;
                case Car.BLOCKED:
                    genderContainer.setBackgroundResource(R.drawable.round_background_transparent_red);
                    genderText.setText(getResources().getString(R.string.blocked));
                    break;
                default:
                    genderContainer.setBackgroundResource(R.drawable.round_background_transparent_red);
                    genderText.setText(getResources().getString(R.string.error));
                    break;
            }

            carsContainer.addView(view);

        }
    }

    @Subscribe
    public void onProfileRecieved(Account.ProfileResponse response) {
        if (!carsReceived) carsProgressBar.setVisibility(View.VISIBLE);
        if (response.didSucceed() && nameText != null && usernameText != null && emailText != null && genderText != null && permissionText != null) {
            nameText.setText(response.name);
            usernameText.setText(response.username);
            emailText.setText(response.mail);
            genderText.setText(getResources().getString(response.gender == User.MALE ? R.string.male : R.string.female));
            int res;
            if (response.permission == User.VERIFIED) {
                res = R.string.verified;
                verificationContainer.setBackgroundResource(R.drawable.round_background_transparent_green);
            } else if (response.permission == User.UNVERIFIED) {
                res = R.string.unverified;
                verificationContainer.setBackgroundResource(R.drawable.round_background_transparent_yellow);
            } else {
                res = R.string.blocked;
                verificationContainer.setBackgroundResource(R.drawable.round_background_transparent_red);
            }
            permissionText.setText(getResources().getString(res));

        } else {
            response.showErrorToast(getActivity());
        }

        if (loadingContainer != null)
            loadingContainer.setVisibility(View.GONE);
    }
}
