package com.amir.manammiam.dialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseDialogFragment;
import com.amir.manammiam.infrastructure.customView.EditTextFont;
import com.amir.manammiam.services.Services;
import com.squareup.otto.Subscribe;

public final class ReportDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private static final String SERVICE_ID = "SERVICE_ID";
    private static final String TAG = "ReportDialogFragment";

    private EditTextFont reportText;
    private View loading;
    private long serverId;

    public static ReportDialogFragment newInstance(long serviceId) {

        Bundle args = new Bundle();

        args.putLong(SERVICE_ID, serviceId);

        ReportDialogFragment fragment = new ReportDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_report, container, false);

        loading = view.findViewById(R.id.dialog_report_loading);
        Bundle args = getArguments();
        serverId = args.getLong(SERVICE_ID);

        view.findViewById(R.id.dialog_report_cancel).setOnClickListener(this);

        final View submitButton = view.findViewById(R.id.dialog_report_submit);
        submitButton.setOnClickListener(this);

        reportText = (EditTextFont) view.findViewById(R.id.dialog_report_report_text);
        reportText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submitButton.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
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
        if (itemId == R.id.dialog_report_cancel) {
            dismiss();
        } else if (itemId == R.id.dialog_report_submit) {
            loading.setVisibility(View.VISIBLE);

            bus.post(new Services.ReportServiceRequest(application.getUser().getToken(), reportText.getText().toString(), serverId));
        }
    }

    @Subscribe
    public void onReportSubmitedRespoce(Services.ReportServiceResponse response) {
        if (response.didSucceed()) {
            Toast.makeText(getContext(), getString(R.string.report_submitted), Toast.LENGTH_LONG).show();
        } else {
            response.showErrorToast(getContext());
        }

        dismiss();
    }
}
