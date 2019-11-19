package com.github.azurlane_api

import com.github.azurlane_api.api.ALInfo
import com.github.azurlane_api.api.AzurLane
import com.github.azurlane_api.api.Category
import com.github.azurlane_api.api.entities.Options
import com.github.azurlane_api.api.entities.SmallShip
import com.github.kittinunf.result.Result
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.FileInputStream
import java.util.*

class AzurLaneTest {
    private val properties = Properties()
    private val inputStream = FileInputStream("./options.properties")

    init {
        properties.load(inputStream)
    }

    private val testUserAgent = "AzurLaneKt_Test_Suite/v${ALInfo.version} (https://github.com/KurozeroPB/AzurLaneKt)"
    private val azurlane = AzurLane(Options(properties.getProperty("token", ""), testUserAgent))

    @Test
    fun `request ship by name`() {
        val name = "Prinz Eugen"
        val result = azurlane.getShipByName(name).complete()
        val (ship, exception) = result
        when (result) {
            is Result.Success -> {
                assertEquals(ship?.names?.en, name)
            }
            is Result.Failure -> {
                assert(exception?.statusCode in 400..599)
            }
        }
    }

    @Test
    fun `request ship by id`() {
        val id = "244"
        val result = azurlane.getShipById(id).complete()
        val (ship, exception) = result
        when (result) {
            is Result.Success -> {
                assertEquals(ship?.id, id)
            }
            is Result.Failure -> {
                assert(exception?.statusCode in 400..599)
            }
        }
    }

    @Test
    fun `list of ships ordered by rarity`() {
        val result = azurlane.getShips(Category.RARITY, "Super Rare").complete()
        val (ships, exception) = result
        when (result) {
            is Result.Success -> {
                if (!ships.isNullOrEmpty())
                    assert(ships.contains(SmallShip("244", "Prinz Eugen")))
                else
                    assert(false)
            }
            is Result.Failure -> {
                assert(exception?.statusCode in 400..599)
            }
        }
    }

    @Test
    fun `list of ships ordered by type`() {
        val result = azurlane.getShips(Category.TYPE, "Destroyer").complete()
        val (ships, exception) = result
        when (result) {
            is Result.Success -> {
                if (!ships.isNullOrEmpty())
                    assert(ships.contains(SmallShip("103", "Vampire")))
                else
                    assert(false)
            }
            is Result.Failure -> {
                assert(exception?.statusCode in 400..599)
            }
        }
    }

    @Test
    fun `list of ships ordered by affiliation`() {
        val result = azurlane.getShips(Category.AFFILIATION, "Sardegna Empire").complete()
        val (ships, exception) = result
        when (result) {
            is Result.Success -> {
                if (!ships.isNullOrEmpty())
                    assert(ships.contains(SmallShip("413", "Zara")))
                else
                    assert(false)
            }
            is Result.Failure -> {
                assert(exception?.statusCode in 400..599)
            }
        }
    }

    @Test
    fun `list of ship names with construction time 00-12-00`() {
        val time = "00:12:00"
        val result = azurlane.getBuildInfo(time).complete()
        val (construction, exception) = result
        when (result) {
            is Result.Success -> {
                if (construction != null) {
                    assertEquals(construction.time, time)
                    assert(construction.ships.contains("U-101"))
                } else {
                    assert(false)
                }
            }
            is Result.Failure -> {
                assert(exception?.statusCode in 400..599)
            }
        }
    }

}