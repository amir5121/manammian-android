package com.amir.manammiam.dialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseDialogFragment;
import com.amir.manammiam.infrastructure.car.CarAdapter;
import com.amir.manammiam.infrastructure.customView.EditTextFont;
import com.amir.manammiam.infrastructure.customView.TextViewFont;
import com.amir.manammiam.services.Account;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class NewServiceDialogFragment extends BaseDialogFragment implements AdapterView.OnItemClickListener {
    private TextViewFont sourceTextView;
    private TextViewFont destinationTextView;
    private TextViewFont dateTextView;
    private EditTextFont priceEditText;
    private EditTextFont capacityEditText;
    private LinearLayout carsContainer;
    private View carsLoading;
    private View submitLoading;
    private View submitButton;
    private ArrayList<View> carsView;
    private CarAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_service, container, false);
        sourceTextView = ((TextViewFont) view.findViewById(R.id.dialog_new_service_text_source));
        destinationTextView = ((TextViewFont) view.findViewById(R.id.include_src_dest_text_destination));
        dateTextView = ((TextViewFont) view.findViewById(R.id.dialog_new_service_text_date));
        priceEditText = ((EditTextFont) view.findViewById(R.id.dialog_new_service_input_price));
        capacityEditText = ((EditTextFont) view.findViewById(R.id.dialog_new_service_input_capacity));
        carsLoading = view.findViewById(R.id.dialog_new_service_cars_progress_bar);
        submitLoading = view.findViewById(R.id.dialog_new_service_submit_progress);
        submitButton = view.findViewById(R.id.dialog_new_service_btn_submit);
        adapter = new CarAdapter(getActivity());
        ListView listView = (ListView) view.findViewById(R.id.dialog_new_service_cars_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        //todo: add cancel button

        bus.post(new Account.CarsRequest(application.getUser().getToken()));

        return view;
    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
////        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//        return dialog;
//    }


    @Subscribe
    public void onCarsRecieved(Account.CarsResponse response) {
        if (response.didSucceed()) {
            adapter.setCars(response.getCars());
            adapter.notifyDataSetChanged();
        } else {
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

    }
}

