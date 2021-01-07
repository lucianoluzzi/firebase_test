package com.lucianoluzzi.firebase_test

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.lucianoluzzi.firebase_test.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        startKoin {
            androidContext(this@MainApplication)
            modules(
                appModule
            )
        }
    }
}