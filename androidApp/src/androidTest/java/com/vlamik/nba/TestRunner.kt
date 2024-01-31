package com.vlamik.nba

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication
import com.vlamik.core.commons.Log.addLogger
import com.vlamik.nba.utils.AndroidLogger

class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        addLogger(AndroidLogger())
        return super.newApplication(cl, HiltTestApplication::class.java.canonicalName, context)
    }
}
