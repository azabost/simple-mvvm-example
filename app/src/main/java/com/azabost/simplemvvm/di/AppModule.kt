package com.azabost.simplemvvm.di

import com.azabost.simplemvvm.net.NetworkModule
import com.azabost.simplemvvm.persistence.DatabaseModule
import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
class AppModule {
    /* You can place something useful here, e.g.:
    @Provides
    fun providesResources(context: Context): Resources = context.resources
    */
}