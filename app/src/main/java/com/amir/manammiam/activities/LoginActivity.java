package com.amir.manammiam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseActivity;
import com.amir.manammiam.fragments.EnrollFragment;
import com.amir.manammiam.infrastructure.User;
import com.amir.manammiam.infrastructure.Utils;
import com.amir.manammiam.infrastructure.customView.EditTextFont;
import com.amir.manammiam.services.Account;
import com.amir.manammiam.services.ServiceFailure;
import com.mancj.slideup.SlideUp;
import com.squareup.otto.Subscribe;

public class LoginActivity extends BaseActivity implements View.OnClickListener, EnrollFragment.EnrollFragmentCallBacks {

    private static final int ANIMATION_DURATION = 500;
    private static final String USERNAME_ERROR = "USERNAME_ERROR";
    private static final String PASSWORD_ERROR = "PASSWORD_ERROR";
    private static final String TAG = "LoginActivity";
    //    private int mainHeight;
    private EditTextFont editPhoneNumber;
    private EditTextFont editPassword;
    private Button loginBtn;
    private View loginProgressBar;
    private SlideUp slideUp;

    //TODO: support screen rotation ?

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpView();
    }

    private void setUpView() {
        loginBtn = (Button) findViewById(R.id.activity_login_btn_login);
        loginBtn.setOnClickListener(this);
        loginProgressBar = findViewById(R.id.activity_login_progress_login);
        loginProgressBar.setVisibility(View.GONE);
        findViewById(R.id.activity_login_text_enroll).setOnClickListener(this);
        FrameLayout enrollContainer = (FrameLayout) findViewById(R.id.activity_login_frame_enroll_fragment_container);
        slideUp = new SlideUp.Builder(enrollContainer)
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .build();
//        RelativeLayout mainContainer = (RelativeLayout) findViewById(R.id.activity_login_root);
        editPhoneNumber = ((EditTextFont) findViewById(R.id.activity_login_edit_phone_number));
        editPhoneNumber.addTextChangedListener(new TextWatcher() {
            CharSequence text;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                text = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e(TAG, "onTextChanged: " + ((int) s.toString().substring(start, start + count).toCharArray()[0]) );
                if (s.toString().substring(start, start + count).equals(" ") || Utils.isPersian(s.toString().substring(start, start + count))) {
                    editPhoneNumber.setText(text);
                    editPhoneNumber.setSelection(start + count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editPassword = ((EditTextFont) findViewById(R.id.activity_login_edit_password));

    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (mainHeight == 0) {
//            mainHeight = mainContainer.getHeight();
//            enrollContainer.setTranslationY(mainHeight);
//            enrollContainer.setVisibility(View.VISIBLE);
//        }
//    }

    @Override
    public void onClick(View view) {
        int itemId = view.getId();
        if (itemId == R.id.activity_login_btn_login) {
            loginProgressBar.setVisibility(View.VISIBLE);
            loginBtn.setEnabled(false);
            loginBtn.animate().scaleX(.9f).scaleY(.9f).alpha(.3f).setDuration(ANIMATION_DURATION / 2);
            bus.post(new Account.LoginRequest(editPhoneNumber.getText().toString(), editPassword.getText().toString()));

        } else if (itemId == R.id.activity_login_text_enroll) {
            slideUp.show();
//            enrollContainer.animate().translationY(0).setDuration(ANIMATION_DURATION).setInterpolator(new AccelerateInterpolator());
        }
    }

    @Subscribe
    public void onRequestFailureRecevied(ServiceFailure failure){
        enableLoginButton();
    }

    @Subscribe
    public void onLoggedIn(Account.LoginResponse response) {
        if (!response.didSucceed()) {
            response.showErrorToast(this);
            enableLoginButton();

        } else {
            application.setUser(new User(null, null, User.MALE, null, User.BLOCKED, response.getToken(), false));
            startActivity(new Intent(this, TokenLoginActivity.class));
            finish();
        }
        editPassword.setError(response.getPropertyError(USERNAME_ERROR));
        editPassword.setError(response.getPropertyError(PASSWORD_ERROR));

    }

    private void enableLoginButton() {
        loginBtn.setBackgroundResource(R.drawable.round_red_button);
        loginProgressBar.setVisibility(View.GONE);
        loginBtn.setEnabled(true);
        loginBtn.animate().scaleX(1).scaleY(1).alpha(1).setInterpolator(new OvershootInterpolator()).setDuration(ANIMATION_DURATION / 2);
    }

    @Override
    public void onBackPressed() {
        if (slideUp.isVisible()) {
            slideUp.hide();
//            enrollContainer.animate().translationY(mainHeight).setDuration(ANIMATION_DURATION).setInterpolator(new AccelerateDecelerateInterpolator());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCancelPressed() {
        slideUp.hide();
//        enrollContainer.animate().translationY(mainHeight).setDuration(ANIMATION_DURATION).setInterpolator(new AccelerateDecelerateInterpolator());
    }
}
