package com.azabost.simplemvvm.di

import com.azabost.simplemvvm.net.NetworkModule
import dagger.Module

@Module(includes = [
    NetworkModule::class
])
class AppModule {
    /* You can place something useful here, e.g.:
    @Provides
    fun providesResources(context: Context): Resources = context.resources
    */
}