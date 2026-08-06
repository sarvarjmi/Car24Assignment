package com.noorheroes.car24assignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject
import com.noorheroes.car24assignment.startup.StartupManager

@HiltAndroidApp
class SDUIApplication : Application() {

    @Inject
    lateinit var startupManager: StartupManager

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startupManager.onAppStart()
    }
}
