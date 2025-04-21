package com.clevertap.demo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.inapp.CTLocalInApp
import com.clevertap.android.signedcall.exception.CallException
import com.clevertap.android.signedcall.exception.InitException
import com.clevertap.android.signedcall.init.SignedCallAPI
import com.clevertap.android.signedcall.init.SignedCallInitConfiguration
import com.clevertap.android.signedcall.interfaces.OutgoingCallResponse
import com.clevertap.android.signedcall.interfaces.SignedCallInitResponse
import com.clevertap.demo.databinding.ActivitySignedCallPpBinding
import org.json.JSONException
import org.json.JSONObject

class SignedCallPP : AppCompatActivity() {


    private var cleverTapAPI: CleverTapAPI? = null
    private lateinit var binding:ActivitySignedCallPpBinding

    private var cuid = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignedCallPpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //All initialization
        cleverTapAPI = CleverTapAPI.getDefaultInstance(applicationContext)
        CleverTapAPI.setDebugLevel(3)

        val signedCallInitListener: SignedCallInitResponse = object : SignedCallInitResponse {
            override fun onSuccess() {
                //App is notified on the main thread when the Signed Call SDK is initialized
                Toast.makeText(applicationContext, "init success", Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(initException: InitException) {
                //App is notified on the main thread when the initialization is failed
                Log.d(
                    "SignedCall: ", "error code: " + initException.errorCode
                            + "\n error message: " + initException.message
                            + "\n error explanation: " + initException.explanation
                )

                if (initException.errorCode == InitException.SdkNotInitializedException.errorCode) {
                    Toast.makeText(applicationContext, "init failed => "+initException.errorCode, Toast.LENGTH_SHORT).show()

                }
            }
        }



        binding.btnRegister.setOnClickListener(){


            cuid = binding.inputCuid.text.toString()
           if (isValidCuid(this,cuid)) {
               initSDK(signedCallInitListener)
            }else{
               cuid = ""
            }
        }








        val outgoingCallResponseListener: OutgoingCallResponse = object : OutgoingCallResponse {
            override fun onSuccess() {
                //App is notified on the main thread when the call-request is accepted and being processed by the signalling channel
            }

            override fun onFailure(callException: CallException) {
                //App is notified on the main thread when the call is failed
                Log.d(
                    "SignedCall: ", "error code: ${callException.errorCode}"
                            + "\n error message: ${callException.message}"
                            + "\n error explanation: ${callException.explanation}"
                )
            }
        }


        SignedCallAPI.getInstance().call(
            applicationContext,
            "receiverCuid",
            "contextOfCall",
            JSONObject(),
            outgoingCallResponseListener
        )

    }

    private fun initSDK(signedCallInitListener: SignedCallInitResponse) {
        val initOptions = JSONObject();
        try {
            initOptions.put("accountId", "67a9ead27be487e18d1681ed");
            initOptions.put("apiKey", "M9eULHgg2CgJP4wJ53jKpCUYQMu14FemJLXH4WLuQvN35u3VRxuUDW8zP8SEZRJV");
            initOptions.put("cuid", cuid);
            initOptions.put("name", "SignedCall");
        } catch (e: JSONException) {
            e.printStackTrace();
        }


        //Creates push primer config using Half-Interstitial template


//Creates push primer config using Alert template
        val jsonObjectConfig = CTLocalInApp.builder()
            .setInAppType(CTLocalInApp.InAppType.ALERT)
            .setTitleText("Get Notified")
            .setMessageText("Enable Notification permission")
            .followDeviceOrientation(true)
            .setPositiveBtnText("Allow")
            .setNegativeBtnText("Cancel")
            .build()


        val initConfiguration = SignedCallInitConfiguration.Builder(initOptions, true)
            .setNotificationPermissionRequired(true)
            .promptReceiverReadPhoneStatePermission(true)
            .promptPushPrimer(jsonObjectConfig)
            .build()


        SignedCallAPI.getInstance()
            .init(applicationContext, initConfiguration, cleverTapAPI, signedCallInitListener)

        val status = SignedCallAPI.getInstance().isInitialized(applicationContext)
        Log.d("status", "Initialization status: $status")
//Create a Builder instance of SignedCallInitConfiguration and pass it inside the init() method


    }


    private fun isValidCuid(context: Context, cuid: String): Boolean {
        if (cuid.length !in 5..50) {
            Toast.makeText(context, "CUID must be between 5 and 50 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!cuid[0].isLetterOrDigit()) {
            Toast.makeText(context, "CUID must start with a letter or number", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!cuid.any { it.isLetter() } || !cuid.all { it.isLetterOrDigit() || it == '_' }) {
            Toast.makeText(context, "CUID must be alphanumeric and can contain '_'", Toast.LENGTH_SHORT).show()
            return false
        }
        if (Regex("\\d+_\\d+").matches(cuid)) {
            Toast.makeText(context, "CUID cannot be in number-number format", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }



}