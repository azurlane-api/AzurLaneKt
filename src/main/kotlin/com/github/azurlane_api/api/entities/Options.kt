package com.github.azurlane_api.api.entities

import com.github.azurlane_api.api.ALInfo

data class Options(
    val token: String,
    val userAgent: String = ALInfo.userAgent
)