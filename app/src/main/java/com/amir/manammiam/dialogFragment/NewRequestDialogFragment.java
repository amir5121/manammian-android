package com.amir.manammiam.dialogFragment;

import android.animation.Animator;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseDialogFragment;
import com.amir.manammiam.fragments.ServicesFragment;
import com.amir.manammiam.infrastructure.CollapseCallback;
import com.amir.manammiam.infrastructure.Constants;
import com.amir.manammiam.infrastructure.Utils;
import com.amir.manammiam.infrastructure.customView.EditTextFont;
import com.amir.manammiam.infrastructure.customView.TextViewFont;
import com.amir.manammiam.infrastructure.location.ManamMiamLocation;
import com.amir.manammiam.infrastructure.location.ManamMiamLocationAdapter;
import com.amir.manammiam.services.Location;
import com.amir.manammiam.services.Services;
import com.amir.manammiam.services.Trips;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;

public class NewRequestDialogFragment extends BaseDialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener, CollapseCallback {

    private static final String TAG = "SrcDestChooserFragment";
    private static final long ONE_MONTH = 2592000000L;
    public static final int WHITE = 0xFFFFFFFF;
    private static int GREEN = 0xFF64DD17;
    private EditTextFont sourceEditText;
    private EditTextFont destinationEditText;
    private TextViewFont dateText;
    private ManamMiamLocation selectedSource = null;
    private ManamMiamLocation selectedDestination = null;
    private boolean isChoosingSource;
    private boolean destinationBackgroundIsGreen = false;
    private boolean sourceBackgroundIsGreen = false;
    private String chosenDate;
    private boolean chosenADate = false;
    private View createServiceButton;
    private View createTripButton;
    private ListView locationListView;
    private View listLoadingContainer;
    private View fullLoadingContainer;
    private ManamMiamLocationAdapter locationAdapter;
    private View serviceFragmentContainer;
    private ServicesFragment serviceFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_request, container, false);
        view.findViewById(R.id.dialog_new_request_btn_cancel).setOnClickListener(this);
        listLoadingContainer = view.findViewById(R.id.dialog_new_request_loading_list);
        sourceEditText = (EditTextFont) view.findViewById(R.id.dialog_new_request_et_source);
        sourceEditText.requestFocus();

        destinationEditText = (EditTextFont) view.findViewById(R.id.dialog_new_request_et_destination);
        dateText = (TextViewFont) view.findViewById(R.id.dialog_new_request_date);
        dateText.setOnClickListener(this);

        createServiceButton = view.findViewById(R.id.dialog_new_request_create_service);
        createServiceButton.setOnClickListener(this);

        createServiceButton.setVisibility(application.getUser().isDriver() ? View.VISIBLE : View.GONE);

        createTripButton = view.findViewById(R.id.dialog_new_request_create_trip);
        createTripButton.setOnClickListener(this);

        locationListView = (ListView) view.findViewById(R.id.dialog_new_request_locations_list);
        locationAdapter = new ManamMiamLocationAdapter(getActivity());
        locationListView.setAdapter(locationAdapter);

        serviceFragmentContainer = view.findViewById(R.id.dialog_new_request_services_container);

        fullLoadingContainer = view.findViewById(R.id.dialog_new_request_loading_full);

        FragmentManager fm = getChildFragmentManager();
        serviceFragment = (ServicesFragment) fm.findFragmentByTag("serviceFragment");
        if (serviceFragment == null) {
            serviceFragment = ServicesFragment.newInstance(false);
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.dialog_new_request_services_container, serviceFragment, "serviceFragment");
            ft.commit();
            fm.executePendingTransactions();
        }

        //TODO: dismiss this dialog after reserving a service

        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                whoHasTheFocus();
                if (isChoosingSource) {
                    selectedSource = locationAdapter.getItem(position);
                    sourceEditText.setTextColor(GREEN);
                    sourceBackgroundIsGreen = true;
                    sourceEditText.setText(selectedSource.getName());

                    destinationEditText.requestFocus();

                } else {
                    selectedDestination = locationAdapter.getItem(position);
                    destinationEditText.setTextColor(GREEN);
                    destinationBackgroundIsGreen = true;
                    destinationEditText.setText(selectedDestination.getName());
                    destinationEditText.clearFocus();

                    if (!sourceBackgroundIsGreen) sourceEditText.requestFocus();

                }

                if (destinationBackgroundIsGreen && sourceBackgroundIsGreen) {
                    Utils.collapse(locationListView, null, NewRequestDialogFragment.this);
                }

                considerButtonsState();
            }
        });


        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (sourceBackgroundIsGreen && selectedSource != null && !selectedSource.getName().equals(s.toString())) {
                    sourceEditText.setTextColor(WHITE);
                    sourceBackgroundIsGreen = false;
                    considerButtonsState();

                }
                updateLocationList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        destinationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (destinationBackgroundIsGreen && selectedDestination != null && !selectedDestination.getName().equals(s.toString())) {
                    destinationEditText.setTextColor(WHITE);
                    destinationBackgroundIsGreen = false;
                    considerButtonsState();
                }
                updateLocationList(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    private void updateLocationList(String s) {
        if (!s.isEmpty() && !s.equals("")) {
            bus.post(new Location.LocationRequest(application.getUser().getToken(), s));
            listLoadingContainer.setAlpha(0);
            listLoadingContainer.setVisibility(View.VISIBLE);
            listLoadingContainer.animate().alpha(1).setDuration(Constants.ANIMATION_DURATION).start();
        }
    }


    private void whoHasTheFocus() {
        if (sourceEditText.hasFocus()) isChoosingSource = true;
        else if (destinationEditText.hasFocus()) isChoosingSource = false;
        else {
            Log.e(TAG, "-- No one had the focus");
        }

    }

    private void pickADate() {
        PersianCalendar persianCalendar = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                NewRequestDialogFragment.this,
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );

        datePickerDialog.setMaxDate(new PersianCalendar(Calendar.getInstance().getTimeInMillis() + ONE_MONTH * 2));
        datePickerDialog.setMinDate(persianCalendar);
        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        chosenDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " ";

        PersianCalendar persianCalendar = new PersianCalendar();

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                NewRequestDialogFragment.this,
                persianCalendar.getTime().getHours(),
                persianCalendar.getTime().getMinutes(),
                true
        );


        timePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String chosenDateComplete = chosenDate + hourOfDay + ":" + minute;
        dateText.setText(chosenDateComplete);
        chosenADate = true;
        //TODO: check the chosenDateComplete is in the future
        considerButtonsState();
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

    private void considerButtonsState() {
        boolean state = destinationBackgroundIsGreen && sourceBackgroundIsGreen && chosenADate;
        createServiceButton.setEnabled(state);
        createTripButton.setEnabled(state);
        if (state) {
            bus.post(new Services.ServicesSpecificRequest(application.getUser().getToken(), selectedSource.getId(), selectedDestination.getId(), application.getUser().getGender()));
            listLoadingContainer.setAlpha(0);
            listLoadingContainer.setVisibility(View.VISIBLE);
            listLoadingContainer.animate().alpha(1).setDuration(Constants.ANIMATION_DURATION).start();
        }
    }

    @Subscribe
    public void onServicesRecieved(Services.ServicesSpecificResponse response) {

        if (response.getServices().size() > 0) serviceFragmentContainer.setVisibility(View.VISIBLE);
        serviceFragment.getAdapter().setServices(response.getServices());
        serviceFragment.getAdapter().notifyDataSetChanged();


        listLoadingContainer.animate().alpha(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listLoadingContainer.setVisibility(View.GONE);
                listLoadingContainer.animate().setListener(null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setDuration(Constants.ANIMATION_DURATION).start();
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.dialog_new_request_date) {
            pickADate();
        } else if (itemId == R.id.dialog_new_request_create_trip) {
            fullLoadingContainer.setAlpha(0);
            fullLoadingContainer.setVisibility(View.VISIBLE);
            fullLoadingContainer.animate().alpha(1).setDuration(Constants.ANIMATION_DURATION).start();

            bus.post(new Trips.CreateRequest(selectedSource.getId(), selectedDestination.getId(), dateText.getText().toString(), application.getUser().getToken()));

        } else if (itemId == R.id.dialog_new_request_create_service) {
            NewServiceDialogFragment serviceDialogFragment = NewServiceDialogFragment.getInstance(
                    selectedSource.getName(),
                    selectedDestination.getName(),
                    dateText.getText().toString(),
                    selectedSource.getId(),
                    selectedDestination.getId(),
                    0);
            serviceDialogFragment.show(getFragmentManager(), "serviceDialogFragment");
            dismiss();
        } else if (itemId == R.id.dialog_new_request_btn_cancel) {
            dismiss();
        }
    }

    @Override
    public void collapseEnded() {
        if (destinationBackgroundIsGreen && sourceBackgroundIsGreen)
            pickADate();
    }

    @Subscribe
    public void onLocationRecieved(Location.LocationResponse response) {
        if (response.didSucceed()) {

            ArrayList<ManamMiamLocation> locations = response.getLocations();
            if (selectedDestination != null) {
                for (ManamMiamLocation location : locations) {
                    if (location.getId() == selectedDestination.getId()) {
                        locations.remove(location);
                        break;
                    }

                }
            }

            if (selectedSource != null) {
                for (ManamMiamLocation location : locations) {
                    if (location.getId() == selectedSource.getId()) {
                        locations.remove(location);
                        break;
                    }

                }
            }
            locationAdapter.setLocations(locations);
            locationAdapter.notifyDataSetChanged();
            if (!destinationBackgroundIsGreen && !sourceBackgroundIsGreen)
                if (locationListView.getVisibility() == View.GONE)
                    Utils.expand(locationListView, null);
//            locationListView.setVisibility(View.VISIBLE);
        } else {
            response.showErrorToast(getContext());
            //todo: handle Error
        }

        listLoadingContainer.animate().alpha(0).setDuration(Constants.ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listLoadingContainer.setVisibility(View.GONE);
                listLoadingContainer.animate().setListener(null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    @Subscribe
    public void onCreateTripResposeRecevied(Trips.CreateResponse response) {
        if (response.didSucceed()) {
            Toast.makeText(getContext(), getString(R.string.trip_created), Toast.LENGTH_SHORT).show();
        } else {
            response.showErrorToast(getContext());
            //TODO: handle error
        }
        dismiss();
    }
}

