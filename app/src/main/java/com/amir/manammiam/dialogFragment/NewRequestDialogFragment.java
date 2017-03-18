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
import com.amir.manammiam.infrastructure.CollapseCallback;
import com.amir.manammiam.infrastructure.Utils;
import com.amir.manammiam.infrastructure.customView.EditTextFont;
import com.amir.manammiam.infrastructure.customView.TextViewFont;
import com.amir.manammiam.infrastructure.location.ManamMiamLocation;
import com.amir.manammiam.infrastructure.location.ManamMiamLocationAdapter;
import com.amir.manammiam.infrastructure.post.ManamMiamPost;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;

public class NewRequestDialogFragment extends BaseDialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener, CollapseCallback {

    private static final String TAG = "SrcDestChooserFragment";
    private static final long ONE_MONTH = 2592000000L;
    public static final int WHITE = 0xFFFFFFFF;
    private static int GREEN = 0xFF64DD17;
    //todo: can these be converted to local variable?
    private EditTextFont sourceEditText;
    private EditTextFont destinationEditText;
    private TextViewFont dateText;
    private ManamMiamLocation selectedSource = null;
    private ManamMiamLocation selectedDestination = null;
    private boolean isChoosingSource;
    //    private View destinationContainer;
//    private View sourceContainer;
    private boolean destinationBackgroundIsGreen = false;
    private boolean sourceBackgroundIsGreen = false;
    private String chosenDate;
    private String chosenDateComplete;
    private boolean chosenADate = false;
    private View createServiceButton;
    private View createTripButton;
//    private ChooserFragmentCallbacks listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_request, container, false);
        view.findViewById(R.id.dialog_new_requset_btn_cancel).setOnClickListener(this);
        sourceEditText = (EditTextFont) view.findViewById(R.id.dialog_new_request_et_source);
        sourceEditText.requestFocus();
        destinationEditText = (EditTextFont) view.findViewById(R.id.dialog_new_request_et_destination);
        dateText = (TextViewFont) view.findViewById(R.id.dialog_new_request_date);
        dateText.setOnClickListener(this);

        createServiceButton = view.findViewById(R.id.dialog_new_request_create_service);
        createServiceButton.setOnClickListener(this);
        createTripButton = view.findViewById(R.id.dialog_new_request_create_trip);
        createTripButton.setOnClickListener(this);

        final ListView listView = (ListView) view.findViewById(R.id.dialog_new_request_list);
        final ManamMiamLocationAdapter adapter = new ManamMiamLocationAdapter(getActivity());

        adapter.addLocation(new ManamMiamLocation("Sepah Blv.", "Jahrom - Sepah blv.", 5000));
        adapter.addLocation(new ManamMiamLocation("Pardis", "Jahrom - Khalij-fars blv.", 5000));
        adapter.addLocation(new ManamMiamLocation("Sepah Blv.", "Jahrom - Sepah blv.", 5000));
        adapter.addLocation(new ManamMiamLocation("Self", "Jahrom - Sepah blv.", 5000));
        adapter.addLocation(new ManamMiamLocation("Sepah Blv.", "Jahrom - Sepah blv.", 5000));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                whoHasTheFocus();
                if (isChoosingSource) {
                    selectedSource = adapter.getItem(position);
//                    sourceContainer.setBackgroundResource(R.drawable.round_green_button);
                    sourceEditText.setTextColor(GREEN);
                    sourceBackgroundIsGreen = true;
                    sourceEditText.setText(selectedSource.getName());

                    destinationEditText.requestFocus();

                } else {
                    selectedDestination = adapter.getItem(position);
//                    destinationContainer.setBackgroundResource(R.drawable.round_green_button);
                    destinationEditText.setTextColor(GREEN);
                    destinationBackgroundIsGreen = true;
                    destinationEditText.setText(selectedDestination.getName());

                    destinationEditText.clearFocus();
                    sourceEditText.clearFocus();

                    if (!sourceBackgroundIsGreen) sourceEditText.requestFocus();


                }
                if (destinationBackgroundIsGreen && sourceBackgroundIsGreen) {
                    Utils.collapse(listView, null, NewRequestDialogFragment.this);

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
//                    sourceContainer.setBackgroundResource(0);
                    sourceEditText.setTextColor(WHITE);
                    sourceBackgroundIsGreen = false;
                    considerButtonsState();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                //TODO query for info to put in listView
            }
        });

        destinationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (selectedDestination != null) Log.e(TAG, "selectedDestination was not null " + selectedDestination.getName() + " == " + s.toString());
                if (destinationBackgroundIsGreen && selectedDestination != null && !selectedDestination.getName().equals(s.toString())) {
//                    destinationContainer.setBackgroundResource(0);
                    destinationEditText.setTextColor(WHITE);
                    destinationBackgroundIsGreen = false;
                    considerButtonsState();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO query for info to put in listView
            }
        });

        return view;
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

    private void whoHasTheFocus() {
        if (sourceEditText.hasFocus()) isChoosingSource = true;
        else if (destinationEditText.hasFocus()) isChoosingSource = false;
        else {
            Log.e(TAG, "-- No one had the focus");
        }

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
        chosenDateComplete = chosenDate + hourOfDay + ":" + minute;
        dateText.setText(chosenDateComplete);
        chosenADate = true;
        //todo: check the chosenDateComplete is in the future
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
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.dialog_new_request_date) {
            pickADate();
        } else if (itemId == R.id.dialog_new_request_create_trip) {
            //TODO: implement
        } else if (itemId == R.id.dialog_new_request_create_service) {
            NewServiceDialogFragment serviceDialogFragment = NewServiceDialogFragment.getInstance(
                    new ManamMiamPost(
                            false,
                            ManamMiamPost.DRIVER_ASKING_PASSENGER,
                            selectedSource.getName(),
                            selectedDestination.getName(),
                            null, null, null,
                            chosenDateComplete,
                            null, null, 0, 0, null, 0, false, -1,
                            selectedSource.getId(),
                            selectedDestination.getId()));
            serviceDialogFragment.show(getFragmentManager(), "serviceDialogFragment");
            dismiss();
        } else if (itemId == R.id.dialog_new_requset_btn_cancel) {
            dismiss();
        }
    }

    @Override
    public void collapseEnded() {
        if (destinationBackgroundIsGreen && sourceBackgroundIsGreen)
            pickADate();
    }
}

