package parser

import game.Orientation
import game.Position
import game.entities.Adventurer
import game.entities.Mountain
import game.entities.Treasure
import game.entities.GameMap
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import game.exceptions.GameRuleException
import utils.TestUtils.loadTestFile
import utils.test
import java.io.FileNotFoundException
import kotlin.test.assertEquals


internal class MapParserTest {


    @Test
    fun `test valid map`() {
        val gameEntities = MapParser.parseMap(loadTestFile("test_basic_map.txt"))

        assertEquals(6, gameEntities.size)
        val adventurers = gameEntities.filterIsInstance<Adventurer>()
        val mountains = gameEntities.filterIsInstance<Mountain>()
        val treasures = gameEntities.filterIsInstance<Treasure>()
        val map = gameEntities.filterIsInstance<GameMap>()

        assertEquals(1, adventurers.size)
        assertEquals(2, mountains.size)
        assertEquals(2, treasures.size)
        assertEquals(1, map.size)
        map[0].test(Position(3, 4))
        adventurers[0].test(Position(1, 1), "Lara", Orientation.South, "AADADAGGA")
        mountains[0].test(Position(1, 0))
        mountains[1].test(Position(2, 1))
        treasures[0].test(Position(0, 3), 2)
        treasures[1].test(Position(1, 3), 3)
    }

    @Test
    fun `test unknown config file`() {
        assertThrows<FileNotFoundException> { MapParser.parseMap("") }
    }


    @Test
    fun `test incorrect config file - Corrupted file`() {
        assertThrows<GameRuleException> { MapParser.parseMap(loadTestFile("test_corrupted_file.txt")) }
    }

    @Test
    fun `test incorrect config file - No treasure`() {
        assertThrows<GameRuleException> { MapParser.parseMap(loadTestFile("test_no_treasure.txt")) }
    }

    @Test
    fun `test incorrect config file - No map`() {
        assertThrows<GameRuleException> { MapParser.parseMap(loadTestFile("test_no_map.txt")) }
    }

    @Test
    fun `test incorrect config file - No adventurers`() {
        assertThrows<GameRuleException> { MapParser.parseMap(loadTestFile("test_no_adventurers.txt")) }
    }

    @Test
    fun `test multiple adventurers`() {
        val gameEntities = MapParser.parseMap(loadTestFile("test_multiple_adventurers.txt"))
        assertEquals(2, gameEntities.filterIsInstance<Adventurer>().size)
    }
}