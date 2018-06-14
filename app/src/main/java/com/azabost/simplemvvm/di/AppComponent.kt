package com.azabost.simplemvvm.di

import android.content.Context
import com.azabost.simplemvvm.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class,
        AndroidInjectorsModule::class
))
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {
        @BindsInstance abstract fun appContext(appContext: Context): Builder
    }
}