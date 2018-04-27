package com.gmail.otb.fhd.mobileappcoursework.utills;

import android.text.TextUtils;
import android.widget.EditText;

public class HandlerInput {

    public static boolean isEmpty(EditText etText)
    {
        String text = etText.getText().toString().trim();
        if(TextUtils.isEmpty(text)) {
            etText.setError("The item cannot be empty");
            return true;
        }
        else
            return false;
    }
}
