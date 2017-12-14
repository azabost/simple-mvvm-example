package com.azabost.simplemvvm.utils

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

fun HttpErrors.getHttpException(
        code: Int = 404,
        contentType: MediaType? = null,
        content: String = ""): HttpException {
    return HttpException(Response.error<Unit>(code, ResponseBody.create(contentType, content)))
}