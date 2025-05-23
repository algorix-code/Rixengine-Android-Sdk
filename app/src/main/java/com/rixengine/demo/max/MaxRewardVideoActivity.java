package com.rixengine.demo.max;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rixengine.demo.AdConfig;
import com.rixengine.demo.R;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;


public class MaxRewardVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "MaxRewardVideoActivity";

    private TextView mTvTip;
    private TextView mTvShow;
    private long startTime;

    private MaxRewardedAd mAdObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_and_show);
        initView();
    }

    private void initView() {
        TextView tv_video_load = findViewById(R.id.tv_load);
        mTvShow = findViewById(R.id.tv_show);
        mTvTip = findViewById(R.id.tv_tip);
        mTvShow.setEnabled(false);
        tv_video_load.setOnClickListener(this);
        mTvShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_load) {
            bnLoad();
        } else if (id == R.id.tv_show) {
            bnShow();
        }
    }

    private void bnLoad() {
        mTvTip.setText(R.string.loading);
        startTime = System.currentTimeMillis();
        mTvShow.setEnabled(false);

        mAdObject = MaxRewardedAd.getInstance(AdConfig.MAX_REWARD_VIDEO_AD, this);
        mAdObject.setListener(mMaxRewardedAdListener);
        mAdObject.loadAd();
    }

    private void bnShow() {
        if (mAdObject == null) {
            Toast.makeText(this, getString(R.string.show_ad_no_load), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mAdObject.isReady()) {
            mAdObject.showAd();
        } else {
            Toast.makeText(this, "isReady()==false", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdObject != null) {
            mAdObject.destroy();
        }
    }

    private MaxRewardedAdListener mMaxRewardedAdListener = new MaxRewardedAdListener() {

        @Override
        public void onAdLoaded(MaxAd ad) {
            Log.d(TAG, "onAdLoaded");
            mTvTip.setText(getString(R.string.format_load_success, (System.currentTimeMillis() - startTime) / 1000));
            mTvShow.setEnabled(true);
        }

        @Override
        public void onAdLoadFailed(String adUnitId, MaxError error) {
            Log.d(TAG, "onAdLoadFailed:" + error.getCode() + " " + error.getMessage());
            Toast.makeText(getBaseContext(), getString(R.string.load_failed), Toast.LENGTH_SHORT).show();
            mTvTip.setText(getString(R.string.format_load_failed, error.getMessage()));
            mTvShow.setEnabled(false);
        }

        @Override
        public void onAdDisplayed(MaxAd ad) {
            Log.d(TAG, "onAdDisplayed");
        }

        @Override
        public void onAdHidden(MaxAd ad) {
            Log.d(TAG, "onAdHidden");
        }

        @Override
        public void onAdClicked(MaxAd ad) {
            Log.d(TAG, "onAdClicked");
        }

        @Override
        public void onAdDisplayFailed(MaxAd ad, MaxError error) {
            Log.d(TAG, "onAdDisplayFailed:" + error.getCode() + ";" + error.getMessage());
        }

        @Override
        public void onUserRewarded(MaxAd ad, MaxReward reward) {
            Log.d(TAG, "onUserRewarded:" + reward.getLabel());
        }
    };

}