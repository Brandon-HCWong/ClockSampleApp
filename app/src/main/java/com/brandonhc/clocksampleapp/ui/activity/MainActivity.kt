package com.brandonhc.clocksampleapp.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.brandonhc.clocksampleapp.R
import com.brandonhc.clocksampleapp.databinding.ActivityMainBinding
import com.brandonhc.clocksampleapp.manager.LanguageManager


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageManager.wrapContextWithLanguage(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            initViews()
        }
    }

    private fun ActivityMainBinding.initViews() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fcv_host_main
        ) as NavHostFragment
        bnvMain.setupWithNavController(navHostFragment.navController)
    }
}