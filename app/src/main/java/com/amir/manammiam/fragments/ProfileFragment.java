package com.amir.manammiam.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.infrastructure.User;
import com.amir.manammiam.infrastructure.customView.TextViewFont;
import com.amir.manammiam.services.Account;
import com.squareup.otto.Subscribe;

public final class ProfileFragment extends BaseFragment {

    private TextViewFont nameText;
    private TextViewFont usernameText;
    private TextViewFont emailText;
    private TextViewFont genderText;
    private TextViewFont permissionText;
    private View verificationContainer;
    private View loadingContainer;

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
        bus.post(new Account.ProfileRequest(application.getUser().getToken()));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Log.e(getClass().getSimpleName(), "got instantiated");
        nameText = ((TextViewFont)view.findViewById(R.id.fragment_profile_text_name));
        usernameText = ((TextViewFont)view.findViewById(R.id.fragment_profile_text_username));
        emailText = ((TextViewFont)view.findViewById(R.id.fragment_profile_text_email));
        genderText = ((TextViewFont)view.findViewById(R.id.fragment_profile_text_gender));
        permissionText = ((TextViewFont)view.findViewById(R.id.fragment_profile_text_permission));
        verificationContainer = view.findViewById(R.id.fragment_profile_verification_container);
        loadingContainer = view.findViewById(R.id.fragment_profile_progress_bar_container);
        return view;
    }

    @Subscribe
    public void onProfileRecieved(Account.ProfileResponse response) {
        if (response.didSucceed()) {
            nameText.setText(response.name);
            usernameText.setText(response.username);
            emailText.setText(response.mail);
            genderText.setText(getResources().getString(response.gender== User.MALE ? R.string.male : R.string.female));
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

        loadingContainer.setVisibility(View.GONE);
    }
}
