package com.frame.library.event;

import android.support.annotation.NonNull;

public class ActionEvent {
    private int action;
    private String message;

    public static final int SHOW_LOADING = 1;
    public static final int HIDE_LOADING = 2;
    public static final int SHOW_TOAST = 3;
    public static final int NEED_LOGIN = 4;
    public static final int SHOW_ERROR = 5;
    public static final int SHOW_LOAD_MORE_ERROR = 6;

    public ActionEvent(int action, String message) {
        this.action = action;
        this.message = message;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
