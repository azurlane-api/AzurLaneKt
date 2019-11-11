package com.github.kurozeropb.api

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kurozeropb.api.entities.Construction
import com.google.gson.Gson
import com.github.kurozeropb.internal.responses.ErrorResponse
import com.github.kurozeropb.internal.responses.ShipResponse
import com.github.kurozeropb.api.entities.Ship
import com.github.kurozeropb.api.entities.SmallShip
import com.github.kurozeropb.internal.responses.ConstructionResponse
import com.github.kurozeropb.internal.responses.ShipsResponse
import com.github.kurozeropb.api.exceptions.HttpException
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.lang.Exception

enum class Order(val string: String) {
    RARITY("rarity"),
    TYPE("type"),
    AFFILIATION("affiliation")
}

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

    fun getShips(order: Order, value: String): List<SmallShip> {
        val (_, _, result) = "/ships".httpGet(parameters = listOf("orderBy" to order.string, order.string to value)).responseString()
        val (data, exception) = result
        when (result) {
            is Result.Success -> {
                if (data != null) {
                    val response = Gson().fromJson(data, ShipsResponse::class.java)
                    return response.ships
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

    fun getBuildInfo(time: String): Construction {
        val (_, _, result) = "/build".httpGet(parameters = listOf("time" to time)).responseString()
        val (data, exception) = result
        when (result) {
            is Result.Success -> {
                if (data != null) {
                    val response = Gson().fromJson(data, ConstructionResponse::class.java)
                    return response.construction
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

}