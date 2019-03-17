package com.xdd.networklib;

import android.support.annotation.CallSuper;

import java.util.Observable;

/**
 * Created by xdd on 2019/3/17
 */
public abstract class NetworAction<T> {

    public void onStart() {

    }
    public abstract void onSuccess(T t);

    public void onError(String errorMessage) {

    }

    public void onFinish() {

    }
}
