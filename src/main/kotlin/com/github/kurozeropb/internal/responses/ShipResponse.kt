package com.github.kurozeropb.internal.responses

import com.github.kurozeropb.api.entities.Ship

data class ShipResponse(
    override val statusCode: Int,
    override val statusMessage: String,
    override val message: String,
    val ship: Ship
) : IBaseResponse