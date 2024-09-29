package com.clevertap.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.demo.databinding.ActivityMainBinding
import com.clevertap.demo.databinding.ActivityPushTemplateBinding
import com.google.android.gms.ads.MobileAds

class PushTemplateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPushTemplateBinding
    private var cleverTapDefaultInstance: CleverTapAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushTemplateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext)
        CleverTapAPI.setDebugLevel(3)
        MobileAds.initialize(this) {}
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(this)


        //push template
        binding.BtPushTemplateEvent.setOnClickListener {
            cleverTapDefaultInstance?.pushEvent("PushTemplate")
        }

        binding.BtAutoCarouselTemplate.setOnClickListener {
            cleverTapDefaultInstance?.pushEvent("Auto Carousel Template")
        }

        binding.BtRatingTemplate.setOnClickListener {
            cleverTapDefaultInstance?.pushEvent("Rating Template")
        }
        binding.BtFiveIconsTemplate.setOnClickListener {
            cleverTapDefaultInstance?.pushEvent("Five Icons Template")
        }
        binding.BtInputBoxTemplate.setOnClickListener {
            cleverTapDefaultInstance?.pushEvent("InputBoxTemplate")
        }

    }
}