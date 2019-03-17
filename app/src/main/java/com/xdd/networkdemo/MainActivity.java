package com.xdd.networkdemo;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;
import com.xdd.networklib.HttpUtil;
import com.xdd.networklib.NetworAction;
import com.xdd.networklib.Network;
import com.xdd.networklib.OnNetworkListener;

import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onButtonClick(View view) {
        ApiService apiService = Network.getInstance().create(ApiService.class);
        HttpUtil.request(apiService.getMeiZi(), new NetworAction<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (jsonObject != null) {
                    Logger.e(jsonObject.toString());
                } else {
                    Logger.e("jsonObject is null");
                }
            }
        });
    }

    public void onLoggerClick(View view) {
        Logger.d("Hello world");
    }


    private void requestWithoutNeteork() {
        //        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//        Retrofit retrofit1 = new Retrofit.Builder()
//                .client(new OkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl("https://www.apiopen.top/meituApi/")
//                .build();
//        ApiService apiService1 = retrofit1.create(ApiService.class);
//        retrofit2.Call<okhttp3.ResponseBody> call = apiService1.getMeiZi1();
//        call.enqueue(new Callback<okhttp3.ResponseBody>() {
//            @Override
//            public void onResponse(retrofit2.Call<okhttp3.ResponseBody> call, Response<okhttp3.ResponseBody> response) {
//                if (call != null) {
//                    Log.e("测试", "call = " + call.toString());
//                }
//                if (response == null) {
//                    return;
//                }
//                try {
//                    Log.e("测试", "response = " + response.body().toString());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Log.e("测试", "response = " + response.message());
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<okhttp3.ResponseBody> call, Throwable t) {
//                if (t == null) {
//                    return;
//                }
//                Log.e("测试", "Throwable = " + t.getMessage());
//
//            }
//        });
    }
}
