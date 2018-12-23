package com.xdd.networklib;

import java.util.HashMap;

/**
 * Created by xiangpengfei on 2018/12/19.
 */
public class Network {
    private String url;
    private HashMap<String, Object> params;
    private OnNetworkListener onNetworkListener;


    private Network() {
    }

    public Network(Builder builder) {
        this.url = builder.url;
        this.params = builder.params;
        this.onNetworkListener = builder.onNetworkListener;
    }

    public void requestPost(){

    }

    public void requestGet(){

    }


    public static class Builder {
        private String url;
        private HashMap<String, Object> params;
        private OnNetworkListener onNetworkListener;

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setParams(HashMap<String, Object> params) {
            this.params = params;
            return this;
        }

        public Builder setOnNetworkListener(OnNetworkListener onNetworkListener) {
            this.onNetworkListener = onNetworkListener;
            return this;
        }

        public Network build() {
            return new Network(this);
        }

    }

}
