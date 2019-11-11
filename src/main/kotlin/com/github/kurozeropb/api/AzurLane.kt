package com.github.kurozeropb.api

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.github.kurozeropb.api.entities.ErrorResponse
import com.github.kurozeropb.api.entities.Ship
import com.github.kurozeropb.api.entities.ShipResponse
import com.github.kurozeropb.api.exceptions.HttpException
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.lang.Exception

class AzurLane(private val userAgent: String? = null) {

    init {
        FuelManager.instance.basePath = ALInfo.baseUrl
        FuelManager.instance.baseHeaders = if (userAgent != null) {
            mapOf("User-Agent" to userAgent)
        } else {
            mapOf("User-Agent" to ALInfo.userAgent)
        }
    }

    private fun handleShipResult(result: Result<String, FuelError>): Ship {
        val (data, exception) = result
        when (result) {
            is Result.Success -> {
                if (data != null) {
                    val response = Gson().fromJson(data, ShipResponse::class.java)
                    return response.ship
                }
                throw Exception("No data returned")
            }
            is Result.Failure -> {
                if (exception != null) {
                    val input = InputStreamReader(ByteArrayInputStream(exception.response.data))
                    val error = Gson().fromJson(input, ErrorResponse::class.java)
                    throw HttpException(error)
                }
                throw Exception("No data returned")
            }
        }
    }

    fun getShipByName(name: String): Ship {
        val (_, _, result) = "/ship".httpGet(parameters = listOf("name" to name)).responseString()
        return handleShipResult(result)
    }

    fun getShipById(id: String): Ship {
        val (_, _, result) = "/ship".httpGet(parameters = listOf("id" to id)).responseString()
        return handleShipResult(result)
    }

}