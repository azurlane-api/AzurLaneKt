package com.github.azurlane_api.api.handlers

import com.github.azurlane_api.api.entities.SmallShip
import com.github.azurlane_api.internal.exceptions.ApiException
import com.github.azurlane_api.internal.responses.ErrorResponse
import com.github.azurlane_api.internal.responses.ShipsResponse
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

class ShipsHandler(private val requestUrl: String, private val parameters: Parameters) {

    private fun handleResult(result: Result<String, FuelError>): Result<List<SmallShip>, ApiException> {
        val (data, exception) = result
        when (result) {
            is Result.Success -> {
                if (data != null) {
                    val response = Gson().fromJson(data, ShipsResponse::class.java)
                    return Result.success(response.ships)
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

    fun complete(): Result<List<SmallShip>, ApiException> {
        val (_, _, result) = requestUrl.httpGet(parameters = parameters).responseString()
        return handleResult(result)
    }

    fun complete(completion: (Result<List<SmallShip>, ApiException>) -> Unit) {
        requestUrl.httpGet(parameters = parameters).responseString { _, _, result ->
            completion(handleResult(result))
        }
    }

}