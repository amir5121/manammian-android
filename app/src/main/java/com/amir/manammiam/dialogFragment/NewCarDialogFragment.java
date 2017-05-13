package com.amir.manammiam.dialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseDialogFragment;
import com.amir.manammiam.infrastructure.NumberPlateAdapter;
import com.amir.manammiam.infrastructure.Utils;
import com.amir.manammiam.infrastructure.customView.EditTextFont;
import com.amir.manammiam.services.Account;
import com.squareup.otto.Subscribe;

public final class NewCarDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    //TODO: in order to add a car request user must have filled their contact information (i.e. phone number and telegram ID)

    private static final String TAG = "NewCarDialogFragment";
    private Button carCode2Button;
    //    private NumberPlateAdapter adapter;
    GridView gridView;
    private View submitButton;
    boolean carTypeOK = false;
    boolean carColorOK = false;
    boolean carCode1OK = false;
    boolean carCode2OK = false;
    boolean carCode3OK = false;
    boolean carCode4OK = false;
    private EditTextFont carCode1;
    private EditTextFont carCode3;
    private EditTextFont carCode4;
    private EditTextFont carType;
    private EditTextFont carColor;
    private View loadingContainer;
    private CheckBox isTaxiCheckBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_car, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        carCode1 = (EditTextFont) view.findViewById(R.id.dialog_new_car_car_code1);
        carCode2Button = (Button) view.findViewById(R.id.dialog_new_car_car_code2_button);
        carCode2Button.setOnClickListener(this);
        carCode3 = (EditTextFont) view.findViewById(R.id.dialog_new_car_car_code3);
        carCode4 = (EditTextFont) view.findViewById(R.id.dialog_new_car_car_code4);
        carType = (EditTextFont) view.findViewById(R.id.dialog_new_car_car_type);
        carColor = (EditTextFont) view.findViewById(R.id.dialog_new_car_car_color);
        isTaxiCheckBox = (CheckBox) view.findViewById(R.id.dialog_new_car_is_taxi_check_box);
        view.findViewById(R.id.dialog_new_car_cancel).setOnClickListener(this);

        final NumberPlateAdapter adapter = new NumberPlateAdapter(getActivity());
        gridView = (GridView) view.findViewById(R.id.dialog_new_car_grid_view);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                carCode2Button.setText(adapter.getAlphabet()[position]);
                Utils.collapse(gridView, null, null);
                carCode2OK = true;
                considerSubmissionState();
            }
        });

        submitButton = view.findViewById(R.id.dialog_new_car_submit);
        submitButton.setOnClickListener(this);

        carColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                carColorOK = s.length() > 2;
                considerSubmissionState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        carType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                carTypeOK = s.length() > 2;
                considerSubmissionState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        carCode1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                carCode1OK = (s.length() == 3);
                considerSubmissionState();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        carCode3.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                carCode3OK = (s.length() == 2);
                considerSubmissionState();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        carCode4.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                carCode4OK = (s.length() == 2);
                considerSubmissionState();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loadingContainer = view.findViewById(R.id.dialog_new_car_loading);
    }

    private void considerSubmissionState() {
        submitButton.setEnabled(carColorOK && carTypeOK && carCode2OK && carCode1OK && carCode3OK && carCode4OK);
    }


    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.dialog_new_car_car_code2_button) {
            Utils.expand(gridView, null);
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } else if (itemId == R.id.dialog_new_car_cancel) {
            dismiss();
        } else if (itemId == R.id.dialog_new_car_submit) {
            loadingContainer.setVisibility(View.VISIBLE);
            bus.post(new Account.AddCarRequest(application.getUser().getToken(),
                    carCode1.getText().toString() + carCode2Button.getText() + carCode3.getText().toString() + " " + carCode4.getText().toString(),
                    carColor.getText().toString(),
                    carType.getText().toString(),
                    //TODO: implement
                    isTaxiCheckBox.isChecked()
            ));
        }
    }

    @Subscribe
    public void onAddCarResponceReceived(Account.AddCarResponse response) {
        if (response.didSucceed()) {

            Toast.makeText(getActivity(), getString(R.string.new_car_request_submitted), Toast.LENGTH_LONG).show();

        } else {
            //todo: handle error
            response.showErrorToast(getContext());

        }

        dismiss();
    }
}
