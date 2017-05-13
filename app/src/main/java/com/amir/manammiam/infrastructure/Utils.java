package com.amir.manammiam.infrastructure;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseActivity;

public final class Utils {
    private Utils() {
    }

    public static void expand(final View v, final View expandButton) {
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
                if (expandButton != null)
                    expandButton.setRotation(interpolatedTime * 180);

            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
//        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(Constants.ANIMATION_DURATION - 100);

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

    public static void collapse(final View v, final View expandButton, final CollapseCallback callback) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();

                    if (expandButton != null)
                        expandButton.setRotation(180 + interpolatedTime * 180);

                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
//        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(Constants.ANIMATION_DURATION - 100);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (callback != null) callback.collapseEnded();
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }

    public static boolean isPersian(String string) {
        for (int i = 0; i < string.length(); i++) {
            int charAsciiNum = (int) string.charAt(i);
            if ((charAsciiNum > 1575 && charAsciiNum < 1641) || charAsciiNum == 1662 || charAsciiNum == 1711 || charAsciiNum == 1670 || charAsciiNum == 1688)
                return true;
        }
        return false;
    }

    /**
     * Will return the only installed telegram null if none or multiple are install
     */
    public static String getTheOnlyInstalledTelegram(Context context) {
        int installed = 0;
        String installedPackage = null;
        for (String telegramPackage :
                Constants.TELEGRAM_PACKAGES) {
            if (isAppInstalled(context, telegramPackage)) {
                installed++;
                installedPackage = telegramPackage;
            }

        }

        if (installed == 1) return installedPackage;
        return null;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        if (packageName == null) return false;
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void goToUserInTelegram(BaseActivity activity, String userId) {
        String onlyActiveTelegram = getTheOnlyInstalledTelegram(activity);
        if (onlyActiveTelegram != null) {
//            if (BaseActivity.isTelegramInstalled) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TELEGRAM_LINK + userId));
            myIntent.setPackage(onlyActiveTelegram);
            activity.startActivity(myIntent);
        } else {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TELEGRAM_LINK + userId));
            activity.startActivity(myIntent);
        }
//            Toast.makeText(activity, activity.getString(R.string.telegram_is_not_installed), Toast.LENGTH_SHORT).show();
    }

    public static void gainPermission(BaseActivity activity, int requestCode) {
        if (
                ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                        Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    public static boolean checkPermission(BaseActivity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1)
            return ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
        return true;
    }


}
