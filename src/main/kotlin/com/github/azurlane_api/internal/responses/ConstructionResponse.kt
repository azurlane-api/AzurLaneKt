package com.github.azurlane_api.internal.responses

import com.github.azurlane_api.api.entities.Construction

data class ConstructionResponse(
    override val statusCode: Int,
    override val statusMessage: String,
    override val message: String,
    val construction: Construction
) : IBaseResponse