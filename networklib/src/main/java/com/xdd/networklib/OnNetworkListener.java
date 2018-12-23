package com.xdd.networklib;

/**
 * Created by xiangpengfei on 2018/12/20.
 */
public interface OnNetworkListener {

    void onStart();

    void onSuccess(Object response,int state);

    void onError();

    void onFinish();

}
