package com.xdd.networklib;

/**
 * Created by xiangpengfei on 2018/12/20.
 */
public interface OnNetworkListener<T> {

    void onStart();

    void onSuccess(T t);

    void onError(String errorMessage);

    void onFinish();

}
