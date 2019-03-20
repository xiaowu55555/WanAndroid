package com.frame.library.event;

import android.arch.lifecycle.MutableLiveData;

public interface IViewModelAction {

    MutableLiveData<ActionEvent> getAction();

    void showLoading(String message);

    void hideLoading();

    void showToast(String message);

    void showError(String message);

    void needLogin();
}
