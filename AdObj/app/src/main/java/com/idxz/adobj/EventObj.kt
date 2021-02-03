package com.idxz.adobj

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.appsflyer.AppsFlyerLib
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.analytics.FirebaseAnalytics
import io.branch.referral.util.BranchEvent
import java.util.HashMap

object EventObj {

    val REGIST = AppEventsConstants.EVENT_NAME_CONTACT
    val APPLY = AppEventsConstants.EVENT_NAME_ADDED_TO_CART
    val LOADSU = AppEventsConstants.EVENT_NAME_ADDED_TO_WISHLIST
    val BLACK = AppEventsConstants.EVENT_NAME_SEARCHED
    val REFUSE = AppEventsConstants.EVENT_NAME_START_TRIAL

    @SuppressLint("MissingPermission")
    fun setEvent(
        content: Context,
        customerId: String,
        branchEventName: String,
        firebaseEventName: String,
        faceBookEventName:String,
        appsFlyerEventName:String
    ) {

        val logger = AppEventsLogger.newLogger(content)
        val firebaseAnalytics = FirebaseAnalytics.getInstance(content)

        val b = Bundle()
        b.putString("customer_id", customerId)

        AppsFlyerLib.getInstance().setCustomerUserId(customerId)
        BranchEvent(branchEventName)
            .addCustomDataProperty("customer_id", customerId)
            .logEvent(content)

        b.putLong("click_time", System.currentTimeMillis())
        firebaseAnalytics.logEvent(firebaseEventName, b)
        logger.logEvent(faceBookEventName, b)

        val mapData = HashMap<String, Any>()
        mapData.put(appsFlyerEventName, System.currentTimeMillis())
        AppsFlyerLib.getInstance()
            .trackEvent(content, appsFlyerEventName, mapData)
    }

}