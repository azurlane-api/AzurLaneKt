package com.github.azurlane_api.api

import com.github.azurlane_api.api.entities.Options
import com.github.kittinunf.fuel.core.FuelManager
import com.github.azurlane_api.api.handlers.ConstructionHandler
import com.github.azurlane_api.api.handlers.ShipHandler
import com.github.azurlane_api.api.handlers.ShipsHandler

enum class Category(val string: String) {
    RARITY("rarity"),
    TYPE("type"),
    AFFILIATION("affiliation")
}

/**
 * Class with functions to make api requests
 *
 * @property options header options
 * @constructor creates a new azurlane class and sets the user-agent
 */
class AzurLane(private val options: Options) {

    init {
        FuelManager.instance.basePath = ALInfo.baseUrl
        FuelManager.instance.baseHeaders = mapOf("Authorization" to options.token, "User-Agent" to options.userAgent)
    }

    /**
     * @since 1.0.0
     *
     * Get information about a ship by name
     *
     * @param name the name of the ship
     * @return the ship data
     */
    fun getShipByName(name: String): ShipHandler {
        return ShipHandler("/ship", listOf("name" to name))
    }

    /**
     * @since 1.0.0
     *
     * Get information about a ship by id
     *
     * @param id the id of the ship
     * @return the ship data
     */
    fun getShipById(id: String): ShipHandler {
        return ShipHandler("/ship", listOf("id" to id))
    }

    /**
     * @since 1.1.2
     *
     * Get a list of ships from rarity, type or affiliation
     *
     * @param category the category
     * @param value value depends on what order is used, e.g. if `Order.RARITY` is used value can be `Super Rare`
     * @return a list of ship objects containing name and id
     */
    fun getShips(category: Category, value: String): ShipsHandler {
        return ShipsHandler("/ships", listOf("category" to category.string, category.string to value))
    }

    /**
     * @since 1.1.2
     *
     * Get ship names matching the given construction time
     *
     * @param time the construction time
     * @return the construction data
     */
    fun getBuildInfo(time: String): ConstructionHandler {
        return ConstructionHandler("/build", listOf("time" to time))
    }

}