package com.amir.manammiam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseActivity;
import com.amir.manammiam.fragments.EnrollFragment;
import com.amir.manammiam.infrastructure.EditTextFont;
import com.amir.manammiam.infrastructure.TextViewFont;

public class LoginActivity extends BaseActivity implements View.OnClickListener, EnrollFragment.enrollFragmentCallBacks{

    private static final int ANIMATION_DURATION = 500;
    private String textViewUserName = null;
    private String textViewPassword = null;
    private FrameLayout enrollContainer;
    private RelativeLayout mainContainer;
    private int mainHeight;

    //TODO: support screen rotation

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpView();
    }

    private void setUpView() {
        findViewById(R.id.activity_login_btn_login).setOnClickListener(this);
        findViewById(R.id.activity_login_text_enroll).setOnClickListener(this);
        enrollContainer = (FrameLayout) findViewById(R.id.activity_login_frame_enroll_fragment_container);
        mainContainer = (RelativeLayout) findViewById(R.id.activity_login_root);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mainHeight = mainContainer.getHeight();
        enrollContainer.setTranslationY(mainHeight);
        enrollContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        int itemId = view.getId();
        if (itemId == R.id.activity_login_btn_login) {
            //todo: check for sql injection
            //todo: login
            textViewUserName = ((EditTextFont)findViewById(R.id.activity_login_edit_username)).getText().toString();
            textViewPassword = ((EditTextFont)findViewById(R.id.activity_login_edit_password)).getText().toString();

            application.getUser().setLoggedIn(true);
            startActivity(new Intent(this, MainActivity.class));
            finish();

        } else if (itemId == R.id.activity_login_text_enroll) {
            enrollContainer.animate().translationY(0).setDuration(ANIMATION_DURATION).setInterpolator(new AccelerateInterpolator());
        }
    }

    @Override
    public void onBackPressed() {
        if (enrollContainer.getTranslationY() == 0) {
            enrollContainer.animate().translationY(mainHeight).setDuration(ANIMATION_DURATION).setInterpolator(new AccelerateDecelerateInterpolator());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCancelPressed() {
        enrollContainer.animate().translationY(mainHeight).setDuration(ANIMATION_DURATION).setInterpolator(new AccelerateDecelerateInterpolator());
    }
}
