package com.github.azurlane_api.api.entities

data class Names(
    val en: String? = null,
    val cn: String? = null,
    val jp: String? = null,
    val kr: String? = null
)

data class Skin(
    val title: String? = null,
    val image: String? = null,
    val chibi: String? = null
)

data class Stars(
    val value: String? = null,
    val count: Int
)

data class Stat(
    val name: String? = null,
    val image: String? = null,
    val value: String? = null
)

data class Stats(
    val level100: List<Stat>? = null,
    val level120: List<Stat>? = null,
    val base: List<Stat>? = null,
    val retrofit100: List<Stat>? = null,
    val retrofit120: List<Stat>? = null
)

data class MiscellaneousData(
    val link: String? = null,
    val name: String? = null
)

data class Miscellaneous(
    val artist: MiscellaneousData? = null,
    val web: MiscellaneousData? = null,
    val pixiv: MiscellaneousData? = null,
    val twitter: MiscellaneousData? = null,
    val voiceActress: MiscellaneousData? = null
)

data class SmallShip(
    val id: String,
    val name: String
)

data class Ship(
    val wikiUrl: String,
    val id: String? = null,
    val names: Names,
    val thumbnail: String,
    val skins: List<Skin>,
    val buildTime: String? = null,
    val rarity: String? = null,
    val stars: Stars,
    val `class`: String? = null,
    val nationality: String? = null,
    val nationalityShort: String? = null,
    val hullType: String? = null,
    val stats: Stats,
    val miscellaneous: Miscellaneous
)
