package pl.brightinventions.myparkinson.net.connectivity

import com.azabost.simplemvvm.net.connectivity.Connectivity

class FakeConnectivity(var isConnected: Boolean = true) : Connectivity {
    override fun hasInternetAccess(): Boolean = isConnected
}