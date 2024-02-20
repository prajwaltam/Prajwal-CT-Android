package com.clevertap.demo

import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.clevertap.android.sdk.CTInboxListener
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.CleverTapInstanceConfig
import com.clevertap.android.sdk.InAppNotificationButtonListener
import com.clevertap.android.sdk.PushPermissionResponseListener
import com.clevertap.android.sdk.displayunits.DisplayUnitListener
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit
import com.clevertap.demo.databinding.SecondActivityBinding
import com.google.android.gms.ads.MobileAds
import java.util.Date


class SecondActivity : AppCompatActivity() , PushPermissionResponseListener,CTInboxListener, DisplayUnitListener,
    InAppNotificationButtonListener {
    private lateinit var binding: SecondActivityBinding
    private var clevertapAdditionalInstance: CleverTapAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //All initialization
        val clevertapAdditionalInstanceConfig = CleverTapInstanceConfig.createInstance(
            this,
            "TEST-56R-747-WW7Z",
            "TEST-a4a-265"
        )

        clevertapAdditionalInstanceConfig.setDebugLevel(CleverTapAPI.LogLevel.DEBUG); // default is CleverTapAPI.LogLevel.INFO

        clevertapAdditionalInstanceConfig.isAnalyticsOnly = false; // disables the user engagement features of the instance, default is false

        clevertapAdditionalInstanceConfig.useGoogleAdId(false); // enables the collection of the Google ADID by the instance, default is false

        clevertapAdditionalInstanceConfig.enablePersonalization(true);
        clevertapAdditionalInstance = CleverTapAPI.instanceWithConfig(applicationContext,clevertapAdditionalInstanceConfig)





        clevertapAdditionalInstance?.registerPushPermissionNotificationResponseListener(this)


        //init app inbox
        clevertapAdditionalInstance?.apply {
            ctNotificationInboxListener = this@SecondActivity
            //Initialize the inbox and wait for callbacks on overridden methods
            initializeInbox()
        }


        //Native display
        clevertapAdditionalInstance?.apply {
            setDisplayUnitListener(this@SecondActivity)
        }




        //For events
        binding.BtAddEvent.setOnClickListener {
            clevertapAdditionalInstance?.pushEvent("Product viewed second")
        }

        //WebHook events
        binding.BtAddWebHookEvent.setOnClickListener {
            clevertapAdditionalInstance?.pushEvent("WebHook second")
        }
        //in events
        binding.BtInAppEvent.setOnClickListener {
            clevertapAdditionalInstance?.pushEvent("InApp second")
        }

        //For events with properties
        binding.BtAddEventWithProperties.setOnClickListener {
            // event with properties
            val prodViewedAction = HashMap<String, Any>()
            prodViewedAction["Product Name"] = "Casio Chronograph Watch"
            prodViewedAction["Category"] = "Mens Accessories"
            prodViewedAction["Price"] = 59.99
            prodViewedAction["Date"] = Date()

            clevertapAdditionalInstance?.pushEvent("Product viewed second", prodViewedAction)
        }

        //For user events
        binding.BtOnUserLogin.setOnClickListener {
            val profileUpdate = HashMap<String, Any>()
            profileUpdate["Name"] = binding.etName.text.toString() // String
//            profileUpdate["Identity"] = ""+ cleverTapDefaultInstance!!.cleverTapID;  // String or number
            profileUpdate["Identity"] = binding.etIdentity.text.toString()  // String or number
            profileUpdate["Email"] = binding.etEmail.text.toString() // Email address of the user
            profileUpdate["Phone"] =
                "+91"+binding.etPhone.text.toString() // Phone (with the country code, starting with +)
            profileUpdate["Gender"] = "M" // Can be either M or F
            profileUpdate["DOB"] =
                Date() // Date of Birth. Set the Date object to the appropriate value


            // optional fields. controls whether the user will be sent email, push etc.
            // optional fields. controls whether the user will be sent email, push etc.
            profileUpdate["MSG-email"] = false // Disable email notifications
            profileUpdate["MSG-push"] = true // Enable push notifications
            profileUpdate["MSG-sms"] = false // Disable SMS notifications
            profileUpdate["MSG-whatsapp"] = true // Enable WhatsApp notifications


            //custom profile properties
            val stuff = ArrayList<String>()
            stuff.add("bag")
            stuff.add("shoes")
            profileUpdate["MyStuff"] = stuff //ArrayList of Strings
            val otherStuff = arrayOf("Jeans", "Perfume")
            profileUpdate["MyStuff"] = otherStuff //String Array

            //updating profile information on login
            CleverTapAPI.instanceWithConfig(applicationContext,clevertapAdditionalInstanceConfig)?.onUserLogin(profileUpdate)

            Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()
        }






        CleverTapAPI.createNotificationChannel(
            applicationContext,"001","Demo","This will be a demo notification channel",
            NotificationManager.IMPORTANCE_MAX,true)


// How to add a sound file to your app : https://developer.clevertap.com/docs/add-a-sound-file-to-your-android-app


    }

    override fun onPushPermissionResponse(accepted: Boolean) {
        Log.i(TAG, "onPushPermissionResponse :  InApp---> response() called accepted=$accepted")
        if (accepted) {
            CleverTapAPI.createNotificationChannel(
                applicationContext, "001", "Demo",
                "This will be a demo notification channel", NotificationManager.IMPORTANCE_HIGH, true);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clevertapAdditionalInstance?.unregisterPushPermissionNotificationResponseListener(this)
    }

    override fun inboxDidInitialize()  {
        binding.appInbox.setOnClickListener {
/*            val inboxTabs =
                arrayListOf(
                    "Promotions",
                    "Offers",
                    "Others"
                )//Anything after the first 2 will be ignored
            CTInboxStyleConfig().apply {
                tabs = inboxTabs //Do not use this if you don't want to use tabs
                tabBackgroundColor = "#FF0000"
                selectedTabIndicatorColor = "#0000FF"
                selectedTabColor = "#000000"
                unselectedTabColor = "#FFFFFF"
                backButtonColor = "#FF0000"
                navBarTitleColor = "#FF0000"
                navBarTitle = "MY INBOX"
                navBarColor = "#FFFFFF"
                inboxBackgroundColor = "#00FF00"
                firstTabTitle = "First Tab"
                cleverTapDefaultInstance?.showAppInbox(this) //Opens activity With Tabs
            }*/
            //OR
            clevertapAdditionalInstance?.showAppInbox()//Opens Activity with default style config
        }
    }

    override fun inboxMessagesDidUpdate() {
    }

    override fun onDisplayUnitsLoaded(units: java.util.ArrayList<CleverTapDisplayUnit>?) {
        // you will get display units here
        // implement your logic to create your display views using these Display Units here
        for (i in 0 until units!!.size)
        {
            val unit = units[i]

            //???
            //prepareDisplayView(unit)
        }

        Log.d(TAG, "onDisplayUnitsLoaded: ${units[0].jsonObject.toString()}")
    }

    override fun onInAppButtonClick(payload: java.util.HashMap<String, String>?) {
        Log.d(TAG, "onInAppButtonClick: ${payload.toString()}")
    }

}