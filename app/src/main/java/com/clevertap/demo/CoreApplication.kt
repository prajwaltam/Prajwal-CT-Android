package com.clevertap.demo

import android.util.Log
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.pushtemplates.TemplateRenderer
import com.clevertap.android.sdk.Application
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.interfaces.NotificationHandler
import com.clevertap.android.signedcall.fcm.SignedCallNotificationHandler


class CoreApplication: Application() {
     private var cleverTapDefaultInstance: CleverTapAPI? = null

    override fun onCreate() {
        super.onCreate()

        Log.d("ttt", "onCreate: application onCreate called")

        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext,)
        CleverTapAPI.setDebugLevel(3)

        CleverTapAPI.setNotificationHandler(PushTemplateNotificationHandler() as NotificationHandler);

        CleverTapAPI.setSignedCallNotificationHandler(SignedCallNotificationHandler())

        TemplateRenderer.debugLevel = 3

    }





}