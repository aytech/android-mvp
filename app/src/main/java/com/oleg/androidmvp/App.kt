package com.oleg.androidmvp

import android.app.Application
import timber.log.Timber

@Suppress("unused")
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}