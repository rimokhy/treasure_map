package game

import game.exceptions.GameRuleException

/**
 * Cardinal points enum.
 */
enum class Orientation {
    North,
    South,
    East,
    West
}

/**
 * Parse an [Orientation] from a string identifier [this].
 *
 * @return an [Orientation] if found.
 * @throws GameRuleException if the string identifier is unknown.
 */
fun String.toOrientation(): Orientation {
    return when (this) {
        "N" -> Orientation.North
        "S" -> Orientation.South
        "E" -> Orientation.East
        "W" -> Orientation.West
        else -> throw GameRuleException("$this is not a cardinal point")
    }
}
