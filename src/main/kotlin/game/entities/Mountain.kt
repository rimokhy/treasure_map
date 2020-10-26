package game.entities

import game.Position

/**
 * [GameEntity] implementation for a mountain.
 *
 * @param position the mountain position.
 */
class Mountain(override val position: Position) : GameEntity("M", position)