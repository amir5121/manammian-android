package com.amir.manammiam.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseAuthenticatedActivity;
import com.amir.manammiam.infrastructure.User;
import com.amir.manammiam.infrastructure.customView.TypeWriter;
import com.amir.manammiam.services.Account;
import com.amir.manammiam.services.ServiceFailure;
import com.squareup.otto.Subscribe;

public class TokenLoginActivity extends BaseAuthenticatedActivity {
    private static final long ANIMATION_LOOP_DURATION = 550;
    private static final String TAG = "TokenLoginActivity";
    private View imageView;
    private boolean isHittingGround = false;
    private AnimatorSet animationUp;
    private AnimatorSet animationDown;
    boolean discontinueAnimation = false;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_token_login);
//
//        bus.post(new Account.ProfileRequest(application.getUser().getToken()));
//
//        imageView = findViewById(R.id.activity_token_login_image);
//        float translateYOffset = -getResources().getDisplayMetrics().density * 150f;
//
//        ((TypeWriter) findViewById(R.id.activity_token_login_ellipse)).animateText("...");
//
//        Log.e(TAG, "onCreate: ");
//
//        setUpAnimation(translateYOffset);
//    }

    @Override
    protected void onManamMiamCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_token_login);

//        Log.e(TAG, "onManamMiamCreate: 0 " + (int) '0' + " 1 " + (int) '1' + " 9 " + (int) '9');
//        Log.e(TAG, "onManamMiamCreate: ۰ " + (int) '۰' + " ۱ " + (int) '۱' + " ۹ " + (int) '۹');

        bus.post(new Account.ProfileRequest(application.getUser().getToken()));

        imageView = findViewById(R.id.activity_token_login_image);
        float translateYOffset = -getResources().getDisplayMetrics().density * 150f;

        ((TypeWriter) findViewById(R.id.activity_token_login_ellipse)).animateText("...");

        setUpAnimation(translateYOffset);
    }


    @Subscribe
    public void onRequestFailureRecevied(ServiceFailure failure) {
        Toast.makeText(this, getText(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
        //TODO: handle when the user has no internet better
        finish();
    }

    @Subscribe
    public void onProfileRecived(Account.ProfileResponse response) {
        if (response.didSucceed()) {
            User user =
                    new User(response.getPhoneNumber(),
                            response.getName(),
                            response.getGender(),
                            response.getTelegramId(),
                            response.getPermission(),
                            application.getUser().getToken(),
                            response.isDriver());

            application.setUser(user);
            startActivity(new Intent(this, MainActivity.class));

        } else {
            finish();
            response.showErrorToast(this);
            startActivity(new Intent(this, LoginActivity.class));
            //TODO: handle Error??

            //TODO: invalidate everything

        }
        discontinueAnimation = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDown.start();
    }

    private void setUpAnimation(float translateYOffset) {

        animationDown = new AnimatorSet();
        animationUp = new AnimatorSet();

        imageView.setTranslationY(translateYOffset);

        ObjectAnimator yTransAnimUp = ObjectAnimator
                .ofFloat(imageView, "translationY", translateYOffset)
                .setDuration(ANIMATION_LOOP_DURATION);

        ObjectAnimator yScaleAnimUp = ObjectAnimator
                .ofFloat(imageView, "scaleY", 1f)
                .setDuration(ANIMATION_LOOP_DURATION);

        ObjectAnimator yTransAnimDown = ObjectAnimator
                .ofFloat(imageView, "translationY", 0)
                .setDuration(ANIMATION_LOOP_DURATION);

        final ObjectAnimator yScaleAnimDown = ObjectAnimator
                .ofFloat(imageView, "scaleY", .4f)
                .setDuration(ANIMATION_LOOP_DURATION / 2);

        yTransAnimDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isHittingGround) {
                    if (animation.getAnimatedFraction() > .45f) {
                        yScaleAnimDown.start();
                        isHittingGround = true;
                    }
                }
            }
        });


        animationUp.playTogether(yScaleAnimUp, yTransAnimUp);
        animationDown.play(yTransAnimDown);
        animationDown.setInterpolator(new AccelerateInterpolator());
        animationUp.setInterpolator(new AccelerateDecelerateInterpolator());


        animationDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!discontinueAnimation) {
                    animationUp.start();
                    isHittingGround = true;
                } else {
                    finish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animationUp.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!discontinueAnimation)
                    animationDown.start();
                else finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
//        animationDown.removeAllListeners();
//        animationUp.removeAllListeners();
        animationUp.end();
        animationDown.end();
    }

    //    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
//                R.animator.manammiam_loading);
//        set.setTarget(imageView);
//        set.start();
//
//    }
}
