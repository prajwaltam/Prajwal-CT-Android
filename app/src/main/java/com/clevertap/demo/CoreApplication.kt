package com.clevertap.demo

import android.util.Log
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.pushtemplates.TemplateRenderer
import com.clevertap.android.sdk.Application
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.CleverTapInstanceConfig
import com.clevertap.android.sdk.inapp.customtemplates.CustomTemplate
import com.clevertap.android.sdk.interfaces.NotificationHandler
import com.clevertap.android.signedcall.fcm.SignedCallNotificationHandler
import com.clevertap.android.sdk.inapp.customtemplates.function
import com.clevertap.android.sdk.inapp.customtemplates.template


class CoreApplication: Application() {
     private var cleverTapDefaultInstance: CleverTapAPI? = null

    override fun onCreate() {
        super.onCreate()

        Log.d("ttt", "onCreate: application onCreate called")

        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext)
        CleverTapAPI.setDebugLevel(3)

        CleverTapAPI.setNotificationHandler(PushTemplateNotificationHandler() as NotificationHandler);

        CleverTapAPI.setSignedCallNotificationHandler(SignedCallNotificationHandler())

        TemplateRenderer.debugLevel = 3





        // custom code in App template

        CleverTapAPI.registerCustomInAppTemplates {
            setOf(
                template {
                    name("Bottom_navigation")
                    presenter(MyTemplatePresenter())
                    stringArgument("icon1", "https://images.a23games.in/a23_floating_footer_banners/rionewfooter.png")
                    stringArgument("icon2", "https://images.a23games.in/a23_floating_footer_banners/RIOActive.png")
                },

            )
        }

        if (cleverTapDefaultInstance != null) {
            // To sync the templates, mark the user profile as test profile
            cleverTapDefaultInstance!!.syncRegisteredInAppTemplates()
            Log.d("inApp", "onCreate: syncRegisteredInAppTemplates => success")
        }


    }





}