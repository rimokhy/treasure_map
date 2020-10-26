package game

import game.entities.Adventurer
import game.entities.Mountain
import game.entities.Treasure
import game.entities.GameMap
import org.junit.jupiter.api.Test
import utils.test
import kotlin.test.assertEquals

internal class GameTest {

    @Test
    fun `test valid scenario`() {
        val adventurer = Adventurer("Lara", Position(1, 1), Orientation.South, "AADADAGGA")
        val treasure1 = Treasure(Position(0, 3), 2)
        val treasure2 = Treasure(Position(1, 3), 3)
        val map = GameMap(Position(3, 4))

        Game(listOf(
                Mountain(Position(1, 0)),
                Mountain(Position(2, 1)),
                map,
                adventurer,
                treasure1,
                treasure2
        )).play()
        assertEquals(adventurer, map.mapArray[adventurer.position.y][adventurer.position.x])
        assertEquals(false, adventurer.hasMovement)
        assertEquals(3, adventurer.treasure)
        assertEquals(0, treasure1.quantity)
        assertEquals(2, treasure2.quantity)
        adventurer.test(Position(0, 3), "Lara", Orientation.South, "AADADAGGA")
    }

    @Test
    fun `test multiple adventurers valid scenario`() {
        val lara = Adventurer("Lara", Position(1, 0), Orientation.East, "ADAAAADA")
        val indiana = Adventurer("Indiana", Position(0, 1), Orientation.South, "GAAADAAADAAAAAAA")
        val treasure1 = Treasure(Position(2, 3), 5)
        val treasure2 = Treasure(Position(1, 4), 6)
        val map = GameMap(Position(4, 5))

        Game(listOf(
                map,
                lara,
                indiana,
                treasure1,
                treasure2
        )).play()
        assertEquals(lara, map.mapArray[4][1])
        lara.test(Position(1, 4), "Lara", Orientation.West, "ADAAAADA")
        assertEquals(2, lara.treasure)
        assertEquals(indiana, map.mapArray[4][2])
        indiana.test(Position(2, 4), "Indiana", Orientation.West, "GAAAD")
        assertEquals(1, indiana.treasure)
        assertEquals(3, treasure1.quantity)
        assertEquals(5, treasure2.quantity)
    }

    @Test
    fun `test unresolvable scenario`() {
        val lara = Adventurer("Lara", Position(2, 0), Orientation.South, "AAAAAAAAAAAAAAAAAAAAAA")
        val treasure1 = Treasure(Position(2, 4), 5)
        val map = GameMap(Position(4, 5))

        Game(listOf(
                map,
                lara,
                treasure1,
                Mountain(Position(0, 3)),
                Mountain(Position(1, 3)),
                Mountain(Position(2, 3)),
                Mountain(Position(3, 3))
        )).play()
        assertEquals(0, lara.treasure)
        assertEquals(5, treasure1.quantity)
        assertEquals(lara, map.mapArray[2][2])
    }
}