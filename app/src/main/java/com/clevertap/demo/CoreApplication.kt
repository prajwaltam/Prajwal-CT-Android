package com.clevertap.demo

import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.pushtemplates.TemplateRenderer
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.Application
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.interfaces.NotificationHandler

class CoreApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        CleverTapAPI.setNotificationHandler(PushTemplateNotificationHandler() as NotificationHandler);
        TemplateRenderer.debugLevel = 3

    }


}