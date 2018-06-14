package com.azabost.simplemvvm.utils

import com.azabost.simplemvvm.R
import retrofit2.HttpException

typealias HttpErrorsMapper = (HttpException) -> Int?

object HttpErrors {
    const val DEFAULT_HTTP_ERROR_MESSAGE = R.string.default_error_message

    fun httpExceptionToErrorMessage(
        exception: HttpException,
        httpErrorsMapper: HttpErrorsMapper? = this::defaultHttpErrorsMapper
    ): Int {
        return httpErrorsMapper?.invoke(exception) ?: defaultHttpErrorsMapper(exception)
    }

    private fun defaultHttpErrorsMapper(exception: HttpException): Int {
        return DEFAULT_HTTP_ERROR_MESSAGE
    }
}