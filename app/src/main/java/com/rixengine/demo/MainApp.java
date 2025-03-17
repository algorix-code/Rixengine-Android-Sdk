package com.rixengine.demo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.rixengine.api.AlxAdSDK;
import com.rixengine.api.AlxSdkInitCallback;

import java.util.HashMap;
import java.util.Map;

public class MainApp extends Application {
    private final String TAG = "MainApp";

    private static MainApp mApp;

    public static MainApp getInstance(){
        return mApp;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp=this;
        initAlxAd();
    }

    /**
     * 广告配置
     */
    public void initAlxAd(){
        try {
            //Alx Ad Init
            AlxAdSDK.init(this, AdConfig.ALX_HOST,AdConfig.ALX_TOKEN, AdConfig.ALX_SID, AdConfig.ALX_APP_ID, new AlxSdkInitCallback() {
                @Override
                public void onInit(boolean isOk, String msg) {
                    Log.i(TAG, Thread.currentThread().getName() + ":" + isOk + "-" + msg);
                }
            });
            AlxAdSDK.setDebug(true);


//            用户扩展参数
            Map<String,Object> extras=new HashMap<>();
            extras.put("uid2_token","NewAdvertisingTokenIjb6u6KcMAtd0/4ZIAYkXvFrMdlZVqfb9LNf99B+1ysE/lBzYVt64pxYxjobJMGbh5q/HsKY7KC0Xo5Rb/Vo8HC4dYOoWXyuGUaL7Jmbw4bzh+3pgokelUGyTX19DfArTeIg7n+8cxWQ=");
            AlxAdSDK.setExtraParameters(extras);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
