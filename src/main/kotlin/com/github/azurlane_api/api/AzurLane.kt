package com.github.azurlane_api.api

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.azurlane_api.api.entities.Construction
import com.github.azurlane_api.api.entities.Ship
import com.github.azurlane_api.api.entities.SmallShip
import com.github.azurlane_api.internal.exceptions.ApiException
import com.github.azurlane_api.internal.exceptions.HttpException
import com.github.azurlane_api.internal.responses.ErrorResponse
import com.github.azurlane_api.internal.responses.ShipResponse
import com.github.azurlane_api.internal.responses.ConstructionResponse
import com.github.azurlane_api.internal.responses.ShipsResponse
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

enum class Order(val string: String) {
    RARITY("rarity"),
    TYPE("type"),
    AFFILIATION("affiliation")
}

/**
 * Class with functions to make api requests
 *
 * @property userAgent the user-agent you want to set
 * @constructor creates a new azurlane class and sets the user-agent
 */
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
                throw ApiException("No data returned")
            }
            is Result.Failure -> {
                if (exception != null) {
                    val input = InputStreamReader(ByteArrayInputStream(exception.response.data))
                    val error = Gson().fromJson(input, ErrorResponse::class.java)
                    throw HttpException(error)
                }
                throw ApiException("No data returned")
            }
        }
    }

    /**
     * @since 1.0.0
     *
     * Get information about a ship by name
     *
     * @param name the name of the ship
     * @return the ship data
     * @throws HttpException
     * @throws ApiException
     */
    fun getShipByName(name: String): Ship {
        val (_, _, result) = "/ship".httpGet(parameters = listOf("name" to name)).responseString()
        return handleShipResult(result)
    }

    /**
     * @since 1.0.0
     *
     * Get information about a ship by id
     *
     * @param id the id of the ship
     * @return the ship data
     * @throws HttpException
     * @throws ApiException
     */
    fun getShipById(id: String): Ship {
        val (_, _, result) = "/ship".httpGet(parameters = listOf("id" to id)).responseString()
        return handleShipResult(result)
    }

    /**
     * @since 1.1.2
     *
     * Get a list of ships from rarity, type or affiliation
     *
     * @param order the order
     * @param value value depends on what order is used, e.g. if `Order.RARITY` is used value can be `Super Rare`
     * @return a list of ship objects containing name and id
     * @throws HttpException
     * @throws ApiException
     */
    fun getShips(order: Order, value: String): List<SmallShip> {
        val (_, _, result) = "/ships".httpGet(parameters = listOf("orderBy" to order.string, order.string to value)).responseString()
        val (data, exception) = result
        when (result) {
            is Result.Success -> {
                if (data != null) {
                    val response = Gson().fromJson(data, ShipsResponse::class.java)
                    return response.ships
                }
                throw ApiException("No data returned")
            }
            is Result.Failure -> {
                if (exception != null) {
                    val input = InputStreamReader(ByteArrayInputStream(exception.response.data))
                    val error = Gson().fromJson(input, ErrorResponse::class.java)
                    throw HttpException(error)
                }
                throw ApiException("No data returned")
            }
        }
    }

    /**
     * @since 1.1.2
     *
     * Get ship names matching the given construction time
     *
     * @param time the construction time
     * @return the construction data
     * @throws HttpException
     * @throws ApiException
     */
    fun getBuildInfo(time: String): Construction {
        val (_, _, result) = "/build".httpGet(parameters = listOf("time" to time)).responseString()
        val (data, exception) = result
        when (result) {
            is Result.Success -> {
                if (data != null) {
                    val response = Gson().fromJson(data, ConstructionResponse::class.java)
                    return response.construction
                }
                throw ApiException("No data returned")
            }
            is Result.Failure -> {
                if (exception != null) {
                    val input = InputStreamReader(ByteArrayInputStream(exception.response.data))
                    val error = Gson().fromJson(input, ErrorResponse::class.java)
                    throw HttpException(error)
                }
                throw ApiException("No data returned")
            }
        }
    }

}