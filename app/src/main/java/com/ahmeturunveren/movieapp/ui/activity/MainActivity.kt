package com.ahmeturunveren.movieapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ahmeturunveren.movieapp.R
import com.ahmeturunveren.movieapp.databinding.ActivityMainBinding
import com.ahmeturunveren.movieapp.util.extensions.gone
import com.ahmeturunveren.movieapp.util.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashScreen,
                R.id.loginScreen-> {
                    binding.bottomNavView.gone()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.light_blue)
                }
                R.id.detailScreen,
                R.id.loginWebView->{
                    binding.bottomNavView.gone()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.light_grey)
                }
                else -> {
                    binding.bottomNavView.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.light_grey)
                }
            }
        }
    }
}