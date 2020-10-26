package game.entities

import game.Position

/**
 * Defines basis for a game entity.
 *
 * @param identifier identifier to be parsed in configuration file.
 * @param position position to be parsed in configuration file.
 */
abstract class GameEntity(open val identifier: String, open val position: Position) {
    override fun toString(): String {
        return "$identifier - $position"
    }
}