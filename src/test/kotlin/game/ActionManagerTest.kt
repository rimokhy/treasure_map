package game

import game.entities.MovableEntity
import game.entities.GameMap
import org.junit.jupiter.api.Test
import parser.MapParser
import utils.TestUtils
import kotlin.test.assertEquals

internal class ActionManagerTest {
    @Test
    fun `test move forward - North`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.North
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.moveForward(adventurer, map.mapArray)
        assertEquals(adventurer, map.mapArray[0][1])
    }

    @Test
    fun `test move forward - South`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.South
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.moveForward(adventurer, map.mapArray)
        assertEquals(adventurer, map.mapArray[2][1])
    }


    @Test
    fun `test move forward - East`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.East
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.moveForward(adventurer, map.mapArray)
        assertEquals(adventurer, map.mapArray[1][2])
    }

    @Test
    fun `test move forward - West`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.West
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.moveForward(adventurer, map.mapArray)
        assertEquals(adventurer, map.mapArray[1][0])
    }

    @Test
    fun `rotate left - North`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.North
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.rotateLeft(adventurer, map.mapArray)
        assertEquals(Orientation.West, adventurer.orientation)
    }

    @Test
    fun `rotate left - South`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.South
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.rotateLeft(adventurer, map.mapArray)
        assertEquals(Orientation.East, adventurer.orientation)
    }

    @Test
    fun `rotate left - East`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.East
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.rotateLeft(adventurer, map.mapArray)
        assertEquals(Orientation.North, adventurer.orientation)
    }

    @Test
    fun `rotate left - West`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.West
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.rotateLeft(adventurer, map.mapArray)
        assertEquals(Orientation.South, adventurer.orientation)
    }

    @Test
    fun `rotate right - North`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.North
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.rotateRight(adventurer, map.mapArray)
        assertEquals(Orientation.East, adventurer.orientation)
    }

    @Test
    fun `rotate right - South`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.South
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.rotateRight(adventurer, map.mapArray)
        assertEquals(Orientation.West, adventurer.orientation)
    }

    @Test
    fun `rotate right - East`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.East
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.rotateRight(adventurer, map.mapArray)
        assertEquals(Orientation.South, adventurer.orientation)
    }

    @Test
    fun `rotate right - West`() {
        val gameEntities = MapParser.parseMap(TestUtils.loadTestFile("test_blank_map.txt"))
        val map = gameEntities.filterIsInstance<GameMap>()[0]
        val adventurer = gameEntities.filterIsInstance<MovableEntity>()[0]

        adventurer.orientation = Orientation.West
        map.loadEntities(gameEntities.filterNot { it is GameMap })
        ActionManager.rotateRight(adventurer, map.mapArray)
        assertEquals(Orientation.North, adventurer.orientation)
    }
}