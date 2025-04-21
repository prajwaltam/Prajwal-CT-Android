package com.clevertap.demo

import android.util.Log
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.pushtemplates.TemplateRenderer
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.Application
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.interfaces.NotificationHandler
import com.google.firebase.messaging.FirebaseMessaging

class CoreApplication: Application() {
     private var cleverTapDefaultInstance: CleverTapAPI? = null

    override fun onCreate() {
        super.onCreate()

        Log.d("ttt", "onCreate: application onCreate called")

        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext)
        CleverTapAPI.setDebugLevel(3)

        CleverTapAPI.setNotificationHandler(PushTemplateNotificationHandler() as NotificationHandler);
        TemplateRenderer.debugLevel = 3

    }





}