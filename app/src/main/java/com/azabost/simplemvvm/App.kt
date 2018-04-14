package com.azabost.simplemvvm

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.azabost.simplemvvm.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import pl.brightinventions.slf4android.LogcatHandler
import pl.brightinventions.slf4android.LoggerConfiguration
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .builder()
            .appContext(this)
            .create(this)
            .inject(this)

        val logConfig = LoggerConfiguration.configuration()
        logConfig.removeRootLogcatHandler()
        logConfig.addHandlerToRootLogger(
            LogcatHandler(logConfig.compiler.compile("[%thread] %message"))
        )
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}