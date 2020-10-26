package game.entities

import game.exceptions.GameRuleException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.MapParser
import utils.TestUtils

internal class TreasureMapTest {
    @Test
    fun `test treasure map`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<Adventurer>()[0]
        val treasures = gameEntities.filterIsInstance<Treasure>()

        map.loadEntities(gameEntities.filterNot { it is GameMap })
        assertEquals(4, map.mapArray.size)
        assertEquals(3, map.mapArray[0].size)
        assertEquals(adventurer, map.mapArray[1][1])
        assertEquals(treasures[0], map.mapArray[3][0])
        assertEquals(treasures[1], map.mapArray[3][1])
    }

    @Test
    fun `test treasure map - Invalid position`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_invalid_position.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]

        assertThrows<GameRuleException> { map.loadEntities(gameEntities.filterNot { it is GameMap }) }
    }

    @Test
    fun `test treasure map - Position overlapping`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_position_overlapping.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]

        assertThrows<GameRuleException> { map.loadEntities(gameEntities.filterNot { it is GameMap }) }
    }
}