package com.amir.manammiam.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

public abstract class ServiceResponse {
    private static final String TAG = "ServiceResponse";

    private String operationError;
    private HashMap<String, String> propertyErrors;
    private boolean isCritical;

    public ServiceResponse() {
        propertyErrors = new HashMap<>();
    }

    public ServiceResponse(String operationError) {
        this.operationError = operationError;
    }

    public ServiceResponse(String operationError, boolean isCritical) {
        this.operationError = operationError;
        this.isCritical = isCritical;
    }

    public String getOperationError() {
        return operationError;
    }

    public void setOperationError(String operationError) {
        this.operationError = operationError;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public void propertyError(String key, String value) {
        propertyErrors.put(key, value);
    }

    public String getPropertyError(String key) {
        return propertyErrors.get(key);
    }

    public boolean didSucceed() {
        return (operationError == null || operationError.isEmpty()) && (propertyErrors.size() == 0);
    }

    public void showErrorToast (Context context) {
        if (context == null || operationError == null || operationError.isEmpty()) return;

        try {
            Toast.makeText(context, operationError, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Can't create error toast", e);
        }
    }
}
