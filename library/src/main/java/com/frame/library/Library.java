package com.frame.library;

import android.app.Application;

import com.frame.library.net.HttpClient;

public class Library {

    private Application context;
    private static Library instance;
    private String base_url;

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

    public Library init(Application app) {
        this.context = app;
        return instance;
    }


    public Application getContext() {
        return context;
    }

    public Library setBaseUrl(String url) {
        base_url = url;
        return instance;
    }

    public <T> T setApiClass(Class<T> service) {
        return HttpClient.getInstance().getApiService(base_url, service);
    }
}
