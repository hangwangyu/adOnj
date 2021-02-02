package com.idxz.adobj

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.NonNull
import com.facebook.ads.NativeBannerAd
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object AdObj : Handler.Callback {

    private lateinit var rewardedAd: RewardedAd
    private lateinit var mAdView: AdView
    private var handler = Handler(this)
    private lateinit var activity: Activity
    private lateinit var relativeLayout: RelativeLayout
    private var nativeBannerAd: NativeBannerAd? = null

    val adCallback = object : RewardedAdCallback() {
        override fun onRewardedAdOpened() {
            // Ad opened.
        }

        override fun onRewardedAdClosed() {
            // Ad closed.
        }

        override fun onUserEarnedReward(@NonNull reward: RewardItem) {
            // User earned reward.
        }

        override fun onRewardedAdFailedToShow(adError: AdError) {
            // Ad failed to display.
        }
    }

    @SuppressLint("MissingPermission")
    fun loadRewardedAd(activity: Activity, adUrl: String, relativeLayout: RelativeLayout) {
        this.activity = activity
        this.relativeLayout = relativeLayout
        relativeLayout.visibility = View.VISIBLE
        rewardedAd = RewardedAd(activity, adUrl)
        val adLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            override fun onRewardedAdFailedToLoad(adError: LoadAdError) {
                // Ad failed to load.
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
        showLoadRewardedAd(relativeLayout)
    }

    private fun showLoadRewardedAd(relativeLayout: RelativeLayout) {
        setClickListenner()
        if (rewardedAd.isLoaded) {
            relativeLayout.visibility = View.VISIBLE
        } else {
            handler.sendEmptyMessageDelayed(11, 3000)
            relativeLayout.visibility = View.GONE
        }
    }

    override fun handleMessage(msg: Message): Boolean {
        when (msg.what) {
            11 -> {
                showLoadRewardedAd(relativeLayout)
            }
        }
        return false
    }

    private fun setClickListenner() {
        relativeLayout.setOnClickListener {
            rewardedAd.show(activity, adCallback)
        }
    }

    @SuppressLint("MissingPermission")
    fun loadAdView(activity: Activity, adUrl: String, layoutId: AdView) {
        this.activity = activity
        layoutId.visibility = View.VISIBLE
        val adView = AdView(activity)

        adView.adSize = AdSize.BANNER

        adView.adUnitId = adUrl

        mAdView = layoutId
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }

    fun loadNativeBannerAd(activity: Activity, adUrl: String) {
        nativeBannerAd = NativeBannerAd(activity, adUrl)
    }

}
