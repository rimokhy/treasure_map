package game.entities

import game.Orientation
import game.Position

/**
 * [GameEntity] implementation for an adventurer.
 *
 * @param name the adventurer's name.
 * @param position the adventurer's position on the map.
 * @param orientation the adventurer's orientation on the map.
 * @param movements the adventurer's sequence of movements.
 */
class Adventurer(val name: String, override val position: Position, override var orientation: Orientation, override val movements: String) : MovableEntity("A", position, orientation, movements) {
    override fun toString() = "$identifier - $name - $position - $orientation - $treasure"
}