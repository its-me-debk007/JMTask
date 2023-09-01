package com.example.jmtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.jmtask.databinding.ActivityMainBinding
import com.example.jmtask.util.ConnectivityStateManager
import com.example.jmtask.util.changeBottomNavVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.findNavController()

        setupWithNavController(binding.bottomNavigationView, navController)

        lifecycleScope.launch(Dispatchers.Main) {
            ConnectivityStateManager.observeNetworkState(this@MainActivity)
                .collect { isInternetAvailable ->
                    binding.noInternetTxt.isVisible = !isInternetAvailable
                    changeBottomNavVisibility(
                        isVisible = !isInternetAvailable,
                        binding.noInternetTxt,
                        this@MainActivity
                    )
                }
        }
    }
}