package com.github.azurlane_api

import com.github.azurlane_api.api.ALInfo
import com.github.azurlane_api.api.AzurLane
import com.github.azurlane_api.api.Category
import com.github.azurlane_api.api.entities.SmallShip
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AzurLaneTest {
    private val testUserAgent = "AzurLaneKt_Test_Suite/v${ALInfo.version} (https://github.com/KurozeroPB/AzurLaneKt)"
    private val azurlane = AzurLane(testUserAgent)

    @Test
    fun `request ship by name`() {
        val name = "Prinz Eugen"
        val ship = azurlane.getShipByName(name)
        assertEquals(ship.names.en, name)
    }

    @Test
    fun `request ship by id`() {
        val id = "244"
        val ship = azurlane.getShipById(id)
        assertEquals(ship.id, id)
    }

    @Test
    fun `list of ships ordered by rarity`() {
        val ships = azurlane.getShips(Category.RARITY, "Super Rare")
        assert(ships.contains(SmallShip("244", "Prinz Eugen")))
    }

    @Test
    fun `list of ships ordered by type`() {
        val ships = azurlane.getShips(Category.TYPE, "Destroyer")
        assert(ships.contains(SmallShip("103", "Vampire")))
    }

    @Test
    fun `list of ships ordered by affiliation`() {
        val ships = azurlane.getShips(Category.AFFILIATION, "Sardegna Empire")
        assert(ships.contains(SmallShip("413", "Zara")))
    }

    @Test
    fun `list of ship names with construction time 00-12-00`() {
        val time = "00:12:00"
        val construction = azurlane.getBuildInfo(time)
        assertEquals(construction.time, time)
        assert(construction.ships.contains("U-101"))
    }

}