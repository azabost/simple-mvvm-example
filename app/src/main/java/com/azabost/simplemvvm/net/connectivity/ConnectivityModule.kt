package com.azabost.simplemvvm.net.connectivity

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConnectivityModule {

    @Provides
    @Singleton
    fun providesConnectivity(context: Context): Connectivity {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return AndroidConnectivity(cm)
    }
}