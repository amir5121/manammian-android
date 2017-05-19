package com.amir.manammiam.infrastructure.car;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.amir.manammiam.infrastructure.Utils;
import com.amir.manammiam.infrastructure.customView.EditTextFont;

public class NumberPlateTextWatcher implements TextWatcher {
    private static final String TAG = "NumberPlateTextWatcher";

    private final EditTextFont textView;
    private int oldStart;

    public NumberPlateTextWatcher(EditTextFont textView) {
        this.textView = textView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textView.removeTextChangedListener(this);
        textView.setText(String.format("%s%s%s",
                s.toString().substring(0, start),
                Utils.getPersianNumberEquivalent(s.toString().substring(start, start + count)),
                s.toString().substring(start + count, s.length())));
        textView.addTextChangedListener(this);
        try {
            textView.setSelection(start + count);
        } catch (IndexOutOfBoundsException e) {

        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
