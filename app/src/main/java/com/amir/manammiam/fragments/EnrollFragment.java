package com.amir.manammiam.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.infrastructure.customView.EditTextFont;

public final class EnrollFragment extends BaseFragment implements View.OnClickListener {

    Button genderButton;
    EditTextFont usernameEdit;
    EditTextFont passwordEdit;
    EditTextFont passwordRepeatEdit;
    EditTextFont nameEdit;
    EditTextFont email;
    private enrollFragmentCallBacks listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (enrollFragmentCallBacks) context;
        } catch (ClassCastException e) {
            throw new RuntimeException("You need to implement EnrollFragmentCallbacks in order to use this fragment");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enroll, container, true);
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        genderButton = (Button) view.findViewById(R.id.fragment_enroll_btn_gender);
        usernameEdit = (EditTextFont) view.findViewById(R.id.fragment_enroll_edit_username);
        passwordEdit = (EditTextFont) view.findViewById(R.id.fragment_enroll_edit_password);
        passwordRepeatEdit = (EditTextFont) view.findViewById(R.id.fragment_enroll_edit_password_repeat);
        nameEdit = (EditTextFont) view.findViewById(R.id.fragment_enroll_edit_name);
        email = (EditTextFont) view.findViewById(R.id.fragment_enroll_edit_email);
        view.findViewById(R.id.fragment_enroll_btn_enroll).setOnClickListener(this);
        genderButton.setOnClickListener(this);
        view.findViewById(R.id.fragment_enroll_btn_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.fragment_enroll_btn_gender) {
            if (genderButton.getText().equals(getResources().getString(R.string.male))) {
                genderButton.setText(getResources().getString(R.string.female));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    genderButton.setBackgroundColor(getResources().getColor(R.color.female_color, null));

                } else {
                    genderButton.setBackgroundColor(getResources().getColor(R.color.female_color));
                }
            } else {
                genderButton.setText(getResources().getString(R.string.male));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    genderButton.setBackgroundColor(getResources().getColor(R.color.male_color, null));
                } else {
                    genderButton.setBackgroundColor(getResources().getColor(R.color.male_color));
                }
            }
        } else if (itemId == R.id.fragment_enroll_btn_enroll) {
        } else if (itemId == R.id.fragment_enroll_btn_close) {
            listener.onCancelPressed();
        }
    }
    public interface enrollFragmentCallBacks {
        void onCancelPressed();
    }

}
