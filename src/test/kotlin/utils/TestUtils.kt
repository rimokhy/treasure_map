package utils

import game.Orientation
import game.Position
import game.entities.Adventurer
import game.entities.Mountain
import game.entities.Treasure
import game.entities.GameMap
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun Position.test(position: Position) {
    assertTrue("position mismatch") { x == position.x && y == position.y }
}

fun Treasure.test(position: Position, quantity: Int) {
    this.position.test(position)
    assertEquals(this.quantity, quantity)
}

fun Adventurer.test(position: Position, name: String, orientation: Orientation, movements: String) {
    this.position.test(position)
    assertEquals(this.name, name)
    assertEquals(this.orientation, orientation)
    assertEquals(movements, movements)
}

fun Mountain.test(position: Position) {
    this.position.test(position)
}

fun GameMap.test(position: Position) {
    this.position.test(position)
}

object TestUtils {
    val classLoader = javaClass.classLoader

    fun loadTestFile(filename: String): String {
        return classLoader.getResource(filename)?.file
                ?: throw IllegalArgumentException("Unknown file: $filename")
    }

}