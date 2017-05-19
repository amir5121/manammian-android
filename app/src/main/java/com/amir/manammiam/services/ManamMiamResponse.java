package com.amir.manammiam.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

public abstract class ManamMiamResponse {
    private static final String TAG = "ManamMiamResponse";

    private String operationError;
    private HashMap<String, String> propertyErrors;
    private boolean isCritical;

    public ManamMiamResponse() {
        propertyErrors = new HashMap<>();
    }

    public ManamMiamResponse(String operationError) {
        this.operationError = operationError;
        propertyErrors = new HashMap<>();
    }

    public ManamMiamResponse(String operationError, boolean isCritical) {
        this.operationError = operationError;
        this.isCritical = isCritical;
        propertyErrors = new HashMap<>();
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
        if (propertyErrors == null) propertyErrors = new HashMap<>();
//        if (operationError == null) Log.e(TAG, "didSucceed: operation error was null what the fuck");
        return (operationError == null ||
                operationError.isEmpty())
                && (propertyErrors.size() == 0);
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
