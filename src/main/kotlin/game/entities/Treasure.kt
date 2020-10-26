package game.entities

import game.Position

/**
 * [GameEntity] implementation for a treasure.
 *
 * @param position the position of the treasure.
 * @param quantity the number of treasures held.
 */
class Treasure(override val position: Position, var quantity: Int) : GameEntity("T", position) {
    override fun toString(): String {
        return "${super.toString()} - $quantity"
    }
}