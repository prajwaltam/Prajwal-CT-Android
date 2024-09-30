package com.clevertap.demo

import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.clevertap.android.sdk.CTInboxListener
import com.clevertap.android.sdk.CTInboxStyleConfig
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.InAppNotificationButtonListener
import com.clevertap.android.sdk.PushPermissionResponseListener
import com.clevertap.android.sdk.displayunits.DisplayUnitListener
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit
import com.clevertap.demo.databinding.ActivityMainBinding
import com.google.android.gms.ads.MobileAds
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject
import java.util.Date


class MainActivity : AppCompatActivity(), PushPermissionResponseListener, CTInboxListener,
    DisplayUnitListener,
    InAppNotificationButtonListener {
    private lateinit var binding: ActivityMainBinding
    private var cleverTapDefaultInstance: CleverTapAPI? = null
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var mp: MixpanelAPI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        firebaseAnalytics = Firebase.analytics


        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "123")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "item-name")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        val trackAutomaticEvents = false;
        // Replace with your Project Token
        mp =
            MixpanelAPI.getInstance(this, "49738094611e9714eef7327d3a92d105", trackAutomaticEvents);

        mp.identify("123456", true)

// Identify must be called before properties are set
        mp.people.set("name", "Jane Doe");
        mp.people.set("email", "jane.doe@example.com");
        mp.people.set("plan", "Premium");


        val props = JSONObject()
        props.put("Gender", "Female")
        props.put("Plan", "Premium")

        mp.track("Plan Selected", props)


        //All initialization
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext)
        CleverTapAPI.setDebugLevel(3)


        //sending CTid To Firebase
        cleverTapDefaultInstance?.let { ins ->
            Log.i(TAG, "setting object id to firebase : ${ins.cleverTapID}")
            FirebaseAnalytics.getInstance(this).setUserProperty("ct_objectId", ins.cleverTapID)
        } ?: run {
            Log.e(TAG, "Uninstall tracking not setup cause of non initialised instance")
        }

        //extra user property
        firebaseAnalytics.setUserProperty("AppName", "ManaYatri");






















        MobileAds.initialize(this) {}
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(this)
        cleverTapDefaultInstance?.registerPushPermissionNotificationResponseListener(this)

        //to capture device information
        cleverTapDefaultInstance?.enableDeviceNetworkInfoReporting(true);


        //init app inbox
        cleverTapDefaultInstance?.apply {
            ctNotificationInboxListener = this@MainActivity
            //Initialize the inbox and wait for callbacks on overridden methods
            initializeInbox()
        }


        //Native display
        cleverTapDefaultInstance?.apply {
            setDisplayUnitListener(this@MainActivity)
        }


        //For events
        binding.BtAddEvent.setOnClickListener {
            cleverTapDefaultInstance?.pushEvent("Product viewed")
        }

        //WebHook events
        binding.BtAddWebHookEvent.setOnClickListener {
            cleverTapDefaultInstance?.pushEvent("WebHook")
        }
        //in events
        binding.BtInAppEvent.setOnClickListener {
            cleverTapDefaultInstance?.pushEvent("InApp")
        }
        //push template
        binding.BtPushTemplateEvent.setOnClickListener {
            val intent = Intent(this@MainActivity, PushTemplateActivity::class.java)
            startActivity(intent)
//            cleverTapDefaultInstance?.pushEvent("PushTemplate")
        }

        //next screen button
        binding.nextScreen.setOnClickListener {
            val intent = Intent(this@MainActivity, PEActivity::class.java)
            startActivity(intent)
        }

        //For events with properties
        binding.BtAddEventWithProperties.setOnClickListener {
            // event with properties
//            val prodViewedAction = HashMap<String, Any>()
//            prodViewedAction["Product Name"] = "Casio Chronograph Watch"
//            prodViewedAction["Category"] = "Mens Accessories"
//            prodViewedAction["Price"] = 59.99
//            prodViewedAction["Date"] = Date()
//
//            cleverTapDefaultInstance?.pushEvent("Product viewed", prodViewedAction)
//
//
//            val colors = arrayOf("red",/* "yellow", "green", "Orange", "Purple", "Pink", "Brown", "Black", "White","Gray"*/)
            val colors = arrayOf("blue",/* "yellow", "green", "Orange", "Purple", "Pink", "Brown", "Black", "White","Gray"*/)

//            val colors = arrayOf("Cyan"/*, "Magenta", "Violet", "Indigo", "Turquoise", "Gold", "Silver", "Maroon", "Beige","Teal", "Lavender","Coral","Navy"*/)
            val randomColor = colors.random()

            //Blue Shoes
       val prodViewedAction = HashMap<String, Any>()
//            prodViewedAction["Product Name"] = "Red Hoodie"
//            prodViewedAction["variant_id"] = "12314234212"
//            prodViewedAction["variant_id"] = "456456456"
//            prodViewedAction["variant_id"] = "786534545"
            prodViewedAction["variant_id"] = "5553434"
//            prodViewedAction["Category"] = "Mens Accessories"
//            prodViewedAction["Price"] = 50
//            prodViewedAction["Color"] = randomColor
//            prodViewedAction["Date"] = Date()

            cleverTapDefaultInstance?.pushEvent("notify_me_requests", prodViewedAction)
        }

        //For user events
        binding.BtOnUserLogin.setOnClickListener {
            val profileUpdate = HashMap<String, Any>()
            profileUpdate["Name"] = binding.etName.text.toString() // String
//            profileUpdate["Identity"] = ""+ cleverTapDefaultInstance!!.cleverTapID;  // String or number
            profileUpdate["Identity"] = binding.etIdentity.text.toString()  // String or number
            profileUpdate["Email"] = binding.etEmail.text.toString() // Email address of the user
            profileUpdate["Phone"] =
                "+91" + binding.etPhone.text.toString() // Phone (with the country code, starting with +)
            profileUpdate["Gender"] = "M" // Can be either M or F
            profileUpdate["DOB"] =
                Date() // Date of Birth. Set the Date object to the appropriate value


            // optional fields. controls whether the user will be sent email, push etc.
            // optional fields. controls whether the user will be sent email, push etc.
            profileUpdate["MSG-email"] = true // Enable email notifications
            profileUpdate["MSG-push"] = true // Enable push notifications
            profileUpdate["MSG-sms"] = true // Disable SMS notifications
            profileUpdate["MSG-whatsapp"] = true // Enable WhatsApp notifications


            //custom profile properties
            val stuff = ArrayList<String>()
            stuff.add("bag")
            stuff.add("shoes")
            profileUpdate["MyStuff"] = stuff //ArrayList of Strings
            val otherStuff = arrayOf("Jeans", "Perfume")
            profileUpdate["MyStuff"] = otherStuff //String Array

            //updating profile information on login
            CleverTapAPI.getDefaultInstance(applicationContext)?.onUserLogin(profileUpdate)

            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
        }


        //Profile push email
        binding.BtProfilePush.setOnClickListener {
            val profileUpdate = HashMap<String, Any>()
            profileUpdate["Email"] = binding.etEmail.text.toString() // Email address of the user
            CleverTapAPI.getDefaultInstance(applicationContext)?.pushProfile(profileUpdate)

            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
        }



        CleverTapAPI.createNotificationChannel(
            applicationContext, "001", "Demo", "This will be a demo notification channel",
            NotificationManager.IMPORTANCE_MAX, true
        )


// How to add a sound file to your app : https://developer.clevertap.com/docs/add-a-sound-file-to-your-android-app


    }

    override fun onPushPermissionResponse(accepted: Boolean) {
        Log.i(TAG, "onPushPermissionResponse :  InApp---> response() called accepted=$accepted")
        if (accepted) {
            CleverTapAPI.createNotificationChannel(
                applicationContext,
                "001",
                "Demo",
                "This will be a demo notification channel",
                NotificationManager.IMPORTANCE_HIGH,
                true
            );
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cleverTapDefaultInstance?.unregisterPushPermissionNotificationResponseListener(this)
    }


    override fun inboxDidInitialize() {
        //default App-InBox
        binding.appInbox.setOnClickListener {
            cleverTapDefaultInstance?.showAppInbox()//Opens Activity with default style config
        }

        //
        binding.appInboxCustom.setOnClickListener {
            val inboxTabs =
                arrayListOf(
                    "First Tab", "Promotions",
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
                cleverTapDefaultInstance?.showAppInbox(this) //Opens activity With Tabs
            }

        }
    }

    override fun inboxMessagesDidUpdate() {
    }

    override fun onDisplayUnitsLoaded(units: java.util.ArrayList<CleverTapDisplayUnit>?) {
        // you will get display units here
        // implement your logic to create your display views using these Display Units here
        for (i in 0 until units!!.size) {
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