package com.github.azurlane_api.api.handlers

import com.github.azurlane_api.api.entities.Ship
import com.github.azurlane_api.internal.exceptions.ApiException
import com.github.azurlane_api.internal.responses.ErrorResponse
import com.github.azurlane_api.internal.responses.ShipResponse
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

class ShipHandler(private val requestUrl: String, private val parameters: Parameters) {

    private fun handleResult(result: Result<String, FuelError>): Result<Ship, ApiException> {
        val (data, exception) = result
        when (result) {
            is Result.Success -> {
                if (data != null) {
                    val response = Gson().fromJson(data, ShipResponse::class.java)
                    return Result.success(response.ship)
                }
                return Result.error(ApiException(ErrorResponse(
                    statusCode = 404,
                    statusMessage = "Not Found",
                    message = "No data returned"
                )))
            }
            is Result.Failure -> {
                if (exception != null) {
                    val input = InputStreamReader(ByteArrayInputStream(exception.response.data))
                    val error = Gson().fromJson(input, ErrorResponse::class.java)
                    return Result.error(ApiException(error))
                }
                return Result.error(ApiException(ErrorResponse(
                    statusCode = 404,
                    statusMessage = "Not Found",
                    message = "No data returned"
                )))
            }
        }
    }

    fun complete(): Result<Ship, ApiException> {
        val (_, _, result) = requestUrl.httpGet(parameters = parameters).responseString()
        return handleResult(result)
    }

    fun complete(completion: (Result<Ship, ApiException>) -> Unit) {
        requestUrl.httpGet(parameters = parameters).responseString { _, _, result ->
            completion(handleResult(result))
        }
    }

}