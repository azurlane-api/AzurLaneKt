package com.github.kurozeropb.api.entities

data class Construction(
    val time: String,
    val wikiUrl: String,
    val ships: List<String>
)