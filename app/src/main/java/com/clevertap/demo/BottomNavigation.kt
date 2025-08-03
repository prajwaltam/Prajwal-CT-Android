package com.clevertap.demo

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.clevertap.demo.databinding.ActivityBottomNavigationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigation : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_navigation)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_custom1,
                R.id.navigation_custom2
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)



        // Dynamically update configurable menu items
        loadMenuIconFromUrl(R.id.navigation_custom1, "https://images.a23games.in/a23_floating_footer_banners/RIOActive.png", "Inbox")
        loadMenuIconFromUrl(R.id.navigation_custom2, "https://images.a23games.in/a23_floating_footer_banners/rionewfooter.png", "Settings")
    }

    private fun loadMenuIconFromUrl(menuItemId: Int, url: String, title: String) {
        Glide.with(this)
            .load(url)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    val menuItem = binding.navView.menu.findItem(menuItemId)
                    menuItem.icon = resource
                    menuItem.title = title
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // no-op
                }
            })
    }
}
