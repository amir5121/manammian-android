package com.amir.manammiam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseActivity;
import com.amir.manammiam.fragments.EnrollFragment;
import com.amir.manammiam.infrastructure.customView.EditTextFont;
import com.amir.manammiam.services.Account;
import com.squareup.otto.Subscribe;

public class LoginActivity extends BaseActivity implements View.OnClickListener, EnrollFragment.enrollFragmentCallBacks {

    private static final int ANIMATION_DURATION = 500;
    private static final String USERNAME_ERROR = "USERNAME_ERROR";
    private static final String PASSWORD_ERROR = "PASSWORD_ERROR";
    private FrameLayout enrollContainer;
    private RelativeLayout mainContainer;
    private int mainHeight;
    private EditTextFont editUsername;
    private EditTextFont editPassword;
    private Button loginBtn;
    private View loginProgressBar;

    //TODO: support screen rotation

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.e(getClass().getSimpleName(), "loginActivity created");

//        ((EditTextFont)findViewById(R.id.activity_login_edit_password)).setError("ERROR");
        setUpView();
    }

    private void setUpView() {
        loginBtn = (Button) findViewById(R.id.activity_login_btn_login);
        loginBtn.setOnClickListener(this);
        loginProgressBar = findViewById(R.id.activity_login_progress_login);
        loginProgressBar.setVisibility(View.GONE);
        findViewById(R.id.activity_login_text_enroll).setOnClickListener(this);
        enrollContainer = (FrameLayout) findViewById(R.id.activity_login_frame_enroll_fragment_container);
        mainContainer = (RelativeLayout) findViewById(R.id.activity_login_root);
        editUsername = ((EditTextFont) findViewById(R.id.activity_login_edit_username));
        editPassword = ((EditTextFont) findViewById(R.id.activity_login_edit_password));

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
            loginProgressBar.setVisibility(View.VISIBLE);
            loginBtn.setEnabled(false);
            loginBtn.animate().scaleX(.9f).scaleY(.9f).alpha(.3f).setDuration(ANIMATION_DURATION / 2);
            bus.post(new Account.LoginRequest(editUsername.getText().toString(), editPassword.getText().toString()));

        } else if (itemId == R.id.activity_login_text_enroll) {
            enrollContainer.animate().translationY(0).setDuration(ANIMATION_DURATION).setInterpolator(new AccelerateInterpolator());
        }
    }

    @Subscribe
    public void onLoggedIn(Account.LoginResponse response) {
        if (!response.didSucceed()) {
            response.showErrorToast(this);
            loginBtn.setBackgroundResource(R.drawable.round_red_button);
            loginProgressBar.setVisibility(View.GONE);
            loginBtn.setEnabled(true);
            loginBtn.animate().scaleX(1).scaleY(1).alpha(1).setInterpolator(new OvershootInterpolator()).setDuration(ANIMATION_DURATION / 2);

        } else {

            //TODO: save to database
            application.getUser().setToken(response.getToken());
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        editPassword.setError(response.getPropertyError(USERNAME_ERROR));
        editPassword.setError(response.getPropertyError(PASSWORD_ERROR));

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
