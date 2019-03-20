package com.frame.library.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.frame.library.event.ActionEvent;
import com.frame.library.event.IViewModelAction;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends AndroidViewModel implements IViewModelAction {

    private MutableLiveData<ActionEvent> actionEventMutableLiveData;
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        actionEventMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public MutableLiveData<ActionEvent> getAction() {
        return actionEventMutableLiveData;
    }

    @Override
    public void showLoading(String message) {
        actionEventMutableLiveData.postValue(new ActionEvent(ActionEvent.SHOW_LOADING, message));
    }

    @Override
    public void hideLoading() {
        actionEventMutableLiveData.postValue(new ActionEvent(ActionEvent.HIDE_LOADING, null));
    }

    @Override
    public void showToast(String message) {
        actionEventMutableLiveData.postValue(new ActionEvent(ActionEvent.SHOW_TOAST, message));
    }

    @Override
    public void showError(String message) {
        actionEventMutableLiveData.postValue(new ActionEvent(ActionEvent.SHOW_ERROR, message));
    }

    @Override
    public void needLogin() {
        actionEventMutableLiveData.postValue(new ActionEvent(ActionEvent.NEED_LOGIN, null));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        unDisposable();
    }

    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    private void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }
}
