package com.github.azurlane_api.internal.responses

import com.github.azurlane_api.api.entities.Ship

data class ShipResponse(
    override val statusCode: Int,
    override val statusMessage: String,
    override val message: String,
    val ship: Ship
) : IBaseResponse