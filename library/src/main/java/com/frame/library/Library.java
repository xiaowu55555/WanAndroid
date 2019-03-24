package com.frame.library;

import android.app.Application;

public class Library {

    private Application context;
    private static Library instance;

    private Library() {
    }

    public static Library getInstance() {
        if (instance == null) {
            synchronized (Library.class) {
                if (instance == null) {
                    instance = new Library();
                }
            }
        }
        return instance;
    }

    public void init(Application app) {
        this.context = app;
    }

    public Application getContext() {
        return context;
    }

}
