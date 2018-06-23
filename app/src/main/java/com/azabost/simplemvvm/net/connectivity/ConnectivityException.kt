package com.azabost.simplemvvm.net.connectivity

import android.support.annotation.StringRes
import com.azabost.simplemvvm.R

class ConnectivityException(
    @StringRes val reason: Int = R.string.error_no_internet_connection
) : Exception("No internet connection")
