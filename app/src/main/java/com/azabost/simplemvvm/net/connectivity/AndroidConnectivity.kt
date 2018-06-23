package com.azabost.simplemvvm.net.connectivity

import android.net.ConnectivityManager
import android.net.NetworkInfo

class AndroidConnectivity(private val connectivityManager: ConnectivityManager) : Connectivity {

    override fun hasInternetAccess(): Boolean {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }
}