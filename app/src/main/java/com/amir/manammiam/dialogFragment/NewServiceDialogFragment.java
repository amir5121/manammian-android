package com.amir.manammiam.dialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseDialogFragment;
import com.amir.manammiam.infrastructure.Constants;
import com.amir.manammiam.infrastructure.car.Car;
import com.amir.manammiam.infrastructure.car.CarAdapter;
import com.amir.manammiam.infrastructure.customView.EditTextFont;
import com.amir.manammiam.infrastructure.customView.TextViewFont;
import com.amir.manammiam.infrastructure.post.ManamMiamPost;
import com.amir.manammiam.services.Account;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class NewServiceDialogFragment extends BaseDialogFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TAG = "NewServiceDFragment";
    private static final String SOURCE = "SOURCE";
    private static final String DESTINATION = "DESTINATION";
    private static final String DATE = "DATE";

    private EditTextFont priceEditText;
    private EditTextFont capacityEditText;
    private View carsLoading;
    private View submitLoadingContainer;
    private View submitButton;
    private CarAdapter adapter;
    private boolean priceOK;
    private boolean capacityOK;
    private int price = -1;
    private int capacity = -1;
    private Car selectedCar = null;

    public static NewServiceDialogFragment getInstance(ManamMiamPost post) {
        NewServiceDialogFragment dialogFragment = new NewServiceDialogFragment();
        Bundle args = new Bundle();
        args.putString(SOURCE, post.getSourceName());
        args.putString(DESTINATION, post.getDestinationName());
        args.putString(DATE, post.getTime());
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_service, container, false);

        setUpView(view);

        //TODO: (query for source id and destination id using server_id) OR (add source_id and destination_id to manamMiamPost)
        bus.post(new Account.CarsRequest(application.getUser().getToken()));

        return view;
    }

    private void setUpView(View view) {
        Bundle args = getArguments();

        TextViewFont sourceTextView = ((TextViewFont) view.findViewById(R.id.dialog_new_service_text_source));
        TextViewFont destinationTextView = ((TextViewFont) view.findViewById(R.id.dialog_new_service_text_destination));
        TextViewFont dateTextView = ((TextViewFont) view.findViewById(R.id.dialog_new_service_text_date));

        sourceTextView.setText(args.getString(SOURCE));
        destinationTextView.setText(args.getString(DESTINATION));
        dateTextView.setText(args.getString(DATE));

        priceEditText = ((EditTextFont) view.findViewById(R.id.dialog_new_service_input_price));
        capacityEditText = ((EditTextFont) view.findViewById(R.id.dialog_new_service_input_capacity));
        carsLoading = view.findViewById(R.id.dialog_new_service_cars_progress_bar);
        submitLoadingContainer = view.findViewById(R.id.dialog_new_service_submit_progress);
        submitButton = view.findViewById(R.id.dialog_new_service_btn_submit);
        submitButton.setOnClickListener(this);

        view.findViewById(R.id.dialog_new_service_btn_cancel).setOnClickListener(this);

        ListView listView = (ListView) view.findViewById(R.id.dialog_new_service_cars_list);
        adapter = new CarAdapter(getActivity(), true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    price = Integer.valueOf(s.toString());
                    priceOK = price >= Constants.MINIMUM_PRICE && price < Constants.MAXIMUM_PRICE;
                } catch (NumberFormatException e) {
                    priceOK = false;
                }
                considerSubmitState();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!priceOK)
                    priceEditText.setError(NewServiceDialogFragment.this.getString(R.string.invalid_price));
            }
        });
        capacityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    capacity = Integer.valueOf(s.toString());
                    capacityOK = (capacity >= Constants.MINIMUM_CAPACITY && capacity < Constants.MAXIMUM_CAPACITY);
                } catch (NumberFormatException e) {
                    capacityOK = false;
                }
                considerSubmitState();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!capacityOK)
                    capacityEditText.setError(NewServiceDialogFragment.this.getString(R.string.invalid_capacity));
            }
        });
    }

    //
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
////        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//        return dialog;

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.dialog_new_service_btn_cancel) {
//            Log.e(getClass().getSimpleName(),(priceEditText.getText().toString().isEmpty() || priceEditText.getText().toString().equals("")? "was Empty": "was full"));
            dismiss();
        } else if (itemId == R.id.dialog_new_service_btn_submit) {
            submitButton.animate().alpha(.7f).setDuration(Constants.ANIMATION_DURATION);
            submitLoadingContainer.setAlpha(0);
            submitLoadingContainer.setVisibility(View.VISIBLE);
            submitLoadingContainer.animate().alpha(1).setDuration(Constants.ANIMATION_DURATION);

            //// TODO: post and receive to and from the bus

        }
    }

    private void considerSubmitState() {
        submitButton.setEnabled(priceOK && capacityOK);
    }

    @Subscribe
    public void onCarsReceived(Account.CarsResponse response) {
        if (response.didSucceed()) {
            ArrayList<Car> cars = response.getCars();
            if (cars.size() > 0) {
                selectedCar = cars.get(0);
                selectedCar.setViewActivated(true);
            }
            //todo: remove unverified cars and blocked cars
            adapter.setCars(cars);
            adapter.notifyDataSetChanged();
        } else {
            response.showErrorToast(getActivity());
            //TODO: manage error
        }
        carsLoading.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < adapter.getCars().size()) {
            selectedCar = adapter.getCars().get(position);
            adapter.setSelectedCar(position, view);
        }
    }
}

