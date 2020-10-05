package com.joshua.mvvmwithflow

import android.content.Context
import androidx.multidex.MultiDexApplication
import timber.log.Timber

class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
        if ((BuildConfig.DEBUG) && Timber.treeCount() == 0) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        // It is safe to save application context to a static variable
        // https://stackoverflow.com/questions/10258507/is-it-safe-to-save-the-app-context-to-a-static-variable-in-android
        lateinit var appContext: Context
    }
}
