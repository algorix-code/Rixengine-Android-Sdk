package com.anythink.custom.adapter;

import android.content.Context;
import android.util.Log;

import com.anythink.core.api.ATInitMediation;
import com.anythink.core.api.MediationInitCallback;
import com.rixengine.api.AlxAdSDK;
import com.rixengine.api.AlxSdkInitCallback;

import java.util.Map;

public class AlxSdkInitManager extends ATInitMediation {

    private volatile static AlxSdkInitManager sInstance;
    private boolean hasCallInit;
    Boolean success = false;

    private AlxSdkInitManager() {

    }

    public static AlxSdkInitManager getInstance() {
        if (sInstance == null) {
            synchronized (AlxSdkInitManager.class) {
                if (sInstance == null)
                    sInstance = new AlxSdkInitManager();
            }
        }
        return sInstance;
    }

    public synchronized void initSDK(Context context, Map<String, Object> serviceExtras) {
        initSDK(context, serviceExtras, null);
    }

    @Override
    public void initSDK(Context context, Map<String, Object> serviceExtras, MediationInitCallback mediationInitCallback) {
        String appid = getStringFromMap(serviceExtras, "appid");
        String sid = getStringFromMap(serviceExtras, "sid");
        String token = getStringFromMap(serviceExtras, "token");
        String host = AlxMetaInf.ADAPTER_SDK_HOST_URL;

        try {
            AlxAdSDK.init(context, host, token, sid, appid, new AlxSdkInitCallback() {
                @Override
                public void onInit(boolean isOk, String msg) {
                    Log.d("xxxxx", "alx sdk init success");
                    success = true;

                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (mediationInitCallback != null) {
            if (success) {
                mediationInitCallback.onSuccess();
            } else {
                mediationInitCallback.onFail("AlxSdk initSDK failed.");
            }
        }

    }

}

