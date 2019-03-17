package com.xdd.networklib;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xdd on 2019/3/17
 */
public class HttpUtil {

    public static <T> void request(Observable<T> observable, final NetworAction<T> networAction) {
        if (networAction != null) {
            networAction.onStart();
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        if (networAction != null) {
                            networAction.onSuccess(t);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (networAction != null) {
                            String errorMessage = "";
                            if (e != null) {
                                errorMessage = e.getMessage();
                            }
                            networAction.onError(errorMessage);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (networAction != null) {
                            networAction.onFinish();
                        }
                    }
                });
    }
}
