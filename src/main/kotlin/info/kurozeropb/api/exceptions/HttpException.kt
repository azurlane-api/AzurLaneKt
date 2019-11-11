package info.kurozeropb.api.exceptions

import info.kurozeropb.api.entities.ErrorResponse
import java.lang.Exception

class HttpException(val response: ErrorResponse) : Exception("HTTP Exception ${response.statusCode} ${response.statusMessage}") {
    val statusCode = response.statusCode
    val statusMessage = response.statusMessage
    val error = response.error
}