package com.github.kurozeropb.internal.responses

import com.github.kurozeropb.api.entities.Construction

data class ConstructionResponse(
    override val statusCode: Int,
    override val statusMessage: String,
    override val message: String,
    val construction: Construction
) : IBaseResponse