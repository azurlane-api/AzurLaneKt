package com.github.azurlane_api.api.entities

data class Construction(
    val time: String,
    val wikiUrl: String,
    val ships: List<String>
)