package com.vlamik.nba

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp
import com.vlamik.core.commons.Log.addLogger
import com.vlamik.nba.utils.AndroidLogger

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        setupLogger()
        setupStrictMode()
    }

    private fun setupStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }

    private fun setupLogger() {
        addLogger(AndroidLogger())
    }
}
