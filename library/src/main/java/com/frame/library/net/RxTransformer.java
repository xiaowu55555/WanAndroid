package com.frame.library.net;


import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>
 * 控制操作线程的辅助类
 */
public class RxTransformer {

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<IApiResult<T>, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> {
                    if (result.isSuccess()){
                        return createData(result.getData());
                    }else {
                        throw new ApiException(result);
                    }
                });
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T t) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

}
