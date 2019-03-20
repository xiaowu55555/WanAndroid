package com.frame.library.utils;

import android.widget.Toast;

import com.frame.library.Library;

public class ToastUtil {

    private static Toast toast;

    public static void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(Library.getInstance().getContext(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
