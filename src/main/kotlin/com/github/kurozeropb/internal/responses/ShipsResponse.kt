package com.github.kurozeropb.internal.responses

import com.github.kurozeropb.api.entities.SmallShip

data class ShipsResponse(
    override val statusCode: Int,
    override val statusMessage: String,
    override val message: String,
    val ships: List<SmallShip>
) : IBaseResponse