package com.github.azurlane_api.internal.responses

import com.github.azurlane_api.api.entities.SmallShip

data class ShipsResponse(
    override val statusCode: Int,
    override val statusMessage: String,
    override val message: String,
    val ships: List<SmallShip>
) : IBaseResponse