package com.xdd.networkdemo;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xdd on 2019/3/16
 */
public interface ApiService {

    @GET("meituApi?page=1")
    Observable<JsonObject> getMeiZi();

    @GET("meituApi?page=1")
    Call<okhttp3.ResponseBody> getMeiZi1();
}
