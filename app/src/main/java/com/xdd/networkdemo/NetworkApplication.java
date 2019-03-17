package com.xdd.networkdemo;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.xdd.networklib.Network;

/**
 * Created by xdd on 2019/3/16
 */
public class NetworkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Network.getInstance().init(getApplicationContext(),"https://www.apiopen.top/");
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
