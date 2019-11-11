package com.github.kurozeropb.api.exceptions

import com.github.kurozeropb.internal.responses.ErrorResponse
import java.lang.Exception

class HttpException(private val response: ErrorResponse) : Exception("HTTP Exception ${response.statusCode} ${response.statusMessage}") {
    val statusCode = response.statusCode
    val statusMessage = response.statusMessage
    val error = response.error
}