package com.amir.manammiam.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseFragment;
import com.amir.manammiam.infrastructure.Constants;
import com.amir.manammiam.infrastructure.User;
import com.amir.manammiam.infrastructure.Utils;
import com.amir.manammiam.infrastructure.customView.EditTextFont;
import com.amir.manammiam.services.Account;
import com.squareup.otto.Subscribe;

public final class EnrollFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "EnrollFragment";
    Button genderButton;
    EditTextFont telegramID;
    EditTextFont passwordEdit;
    EditTextFont passwordRepeatEdit;
    EditTextFont nameEdit;
    EditTextFont phoneNumber;
    private EnrollFragmentCallBacks listener;
    private View submitButton;
    private View loadingContainer;
    private boolean telegramIDOK;
    private boolean passwordOK;
    private boolean passwordRepeatOK;
    private boolean nameOK;
    private boolean phoneNumberOk;
    private boolean genderOk = false;


    //    private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    //taken from: http://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
    private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
    private static final String PHONE_NUMBER_PATTERN = "09([0-9]{9})";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (EnrollFragmentCallBacks) context;
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
        telegramID = (EditTextFont) view.findViewById(R.id.fragment_enroll_edit_telegram_id);
        passwordEdit = (EditTextFont) view.findViewById(R.id.fragment_enroll_edit_password);
        passwordRepeatEdit = (EditTextFont) view.findViewById(R.id.fragment_enroll_edit_password_repeat);
        nameEdit = (EditTextFont) view.findViewById(R.id.fragment_enroll_edit_name);
        phoneNumber = (EditTextFont) view.findViewById(R.id.fragment_enroll_edit_phone_number);
        loadingContainer = view.findViewById(R.id.fragment_enroll_loading);
        submitButton = view.findViewById(R.id.fragment_enroll_btn_enroll);
        submitButton.setOnClickListener(this);
        genderButton.setOnClickListener(this);
//        view.findViewById(R.id.fragment_enroll_btn_close).setOnClickListener(this);

        telegramID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                telegramIDOK = s.length() >= 5 && !s.toString().contains(" ") && !Utils.isPersian(s.toString());
                if (telegramIDOK) {
                    telegramID.setError(null);
                } else {
                    telegramID.setError(getString(R.string.telegram_id_rules));
                }
                considerSubmitState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                phoneNumberOk = EmailValidator.getInstance().isValid(s.toString());
                phoneNumberOk = s.toString().matches(PHONE_NUMBER_PATTERN);
                if (phoneNumberOk) {
                    phoneNumber.setError(null);
                } else {
                    phoneNumber.setError(getString(R.string.invalid_phone_number));
                }
                considerSubmitState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordOK = s.toString().matches(PASSWORD_PATTERN);
                if (passwordOK) {
                    passwordEdit.setError(null);
                } else {
                    passwordEdit.setError(getString(R.string.password_rules));
                }
                considerSubmitState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordRepeatEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordRepeatOK = passwordEdit.getText().toString().equals(s.toString());
                if (passwordRepeatOK) {
                    passwordRepeatEdit.setError(null);
                } else {
                    passwordRepeatEdit.setError(getString(R.string.passwords_must_match));
                }
                considerSubmitState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameOK = s.toString().length() > 0;
                if (nameOK) {
                    nameEdit.setError(null);
                } else {
                    nameEdit.setError(getString(R.string.can_not_be_empty));
                }
                considerSubmitState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void considerSubmitState() {
        submitButton.setEnabled(phoneNumberOk && nameOK && passwordOK && passwordRepeatOK && genderOk && telegramIDOK);
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.fragment_enroll_btn_gender) {
            genderOk = true;
            considerSubmitState();
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
            loadingContainer.setAlpha(0);
            loadingContainer.setVisibility(View.VISIBLE);
            loadingContainer.animate().alpha(1).setDuration(Constants.ANIMATION_DURATION).start();

            bus.post(new Account.EnrollRequest(phoneNumber.getText().toString(),
                    telegramID.getText().toString(),
                    nameEdit.getText().toString(),
                    passwordEdit.getText().toString(),
                    genderButton.getText().equals(getString(R.string.male)) ? User.MALE : User.FEMALE));

//        } else if (itemId == R.id.fragment_enroll_btn_close) {
//            listener.onCancelPressed();
        }
    }

    public interface EnrollFragmentCallBacks {
        void onCancelPressed();
    }

    @Subscribe
    public void onEnrollResponseReceived(Account.EnrollResponse response) {
        if (response.didSucceed()) {
            Toast.makeText(getContext(), getString(R.string.enroll_successful), Toast.LENGTH_SHORT).show();

            //TODO: verify phone number
//            Toast.makeText(getContext(), getString(R.string.verify_email), Toast.LENGTH_SHORT).show();
        } else {
            response.showErrorToast(getContext());
        }

        loadingContainer.setVisibility(View.GONE);
        listener.onCancelPressed();

    }

}
