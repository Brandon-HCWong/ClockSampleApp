package com.brandonhc.clocksampleapp

import android.app.Application
import android.util.Log
import com.brandonhc.clocksampleapp.data.di.KoinModules

class ClockSampleApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
        KoinModules.initKoin(this)
    }

    companion object {
        private const val TAG = "ClockSampleApplication"
    }
}