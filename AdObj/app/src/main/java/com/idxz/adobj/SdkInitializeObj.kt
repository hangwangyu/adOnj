package com.idxz.adobj

import android.app.Application
import android.content.Context
import android.provider.Settings.System.putString
import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.FacebookSdk
import com.facebook.ads.AudienceNetworkAds
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.FirebaseApp
import io.branch.referral.Branch

object SdkInitializeObj {

    fun initSdk(context: Application, appPackage: String, afKey: String) {
        AudienceNetworkAds.initialize(context)

        Branch.getAutoInstance(context)
        FacebookSdk.sdkInitialize(context)

        AppEventsLogger.activateApp(context)

        FirebaseApp.initializeApp(context)

        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {

                context.getSharedPreferences(appPackage + "_preferences", Context.MODE_PRIVATE)
                    .edit().putString("afData", dataToJson(data))
                Log.e("----------",dataToJson(data))
            }

            override fun onConversionDataFail(error: String?) {

            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {

            }

            override fun onAttributionFailure(error: String?) {

            }
        }
        AppsFlyerLib.getInstance()
            .init(afKey, conversionDataListener, context)
        AppsFlyerLib.getInstance().startTracking(context)

    }

    private fun <T> dataToJson(data: T): String {
        try {
            return JSONObject.toJSONString(data)
        } catch (e: Exception) {
            e.message
        }
        return ""
    }
}