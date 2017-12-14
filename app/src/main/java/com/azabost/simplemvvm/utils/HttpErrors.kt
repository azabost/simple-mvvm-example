package com.azabost.simplemvvm.utils

import com.azabost.simplemvvm.R
import retrofit2.HttpException

object HttpErrors {
    val DEFAULT_HTTP_ERROR_MESSAGE = R.string.default_error_message

    fun httpExceptionToErrorMessage(exception: HttpException): Int {
        // when (exception.code()) {
        //     404 -> ...
        return DEFAULT_HTTP_ERROR_MESSAGE
    }
}