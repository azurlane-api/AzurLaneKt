package com.github.kurozeropb

import com.github.kurozeropb.api.ALInfo
import com.github.kurozeropb.api.AzurLane
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

}