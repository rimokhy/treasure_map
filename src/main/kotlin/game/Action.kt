package game

import game.Orientation.*
import game.entities.*
import org.slf4j.LoggerFactory

/**
 * Defines an action that a [MovableEntity] would perform.
 */
typealias Action = (entity: MovableEntity, map: MappedData) -> MovableEntity?

/**
 * Holds action configurations.
 */
object ActionManager {
    val logger = LoggerFactory.getLogger(ActionManager::class.java)

    /**
     * Map defined actions. Map keys corresponds to potential entries in adventurer's sequence of movement.
     */
    val actions: Map<Char, Action> = mapOf(
            'A' to ActionManager::moveForward,
            'G' to ActionManager::rotateLeft,
            'D' to ActionManager::rotateRight
    )

    /**
     * The [entity] moves [MovableEntity.velocity] tile forward its [MovableEntity.orientation].
     * If the destination tile holds is a restricted tile (mountain, other player), the action will fail.
     * If the destination tile holds a treasure, the [entity] will [MovableEntity.pickup] 1 treasure.
     *
     * @param entity the [MovableEntity] that executes the action.
     * @param map the [MappedData] map that holds entities position.
     * @return [entity], null if something went wrong.
     */
    fun moveForward(entity: MovableEntity, map: MappedData): MovableEntity? {
        val nextPos = entity.run {
            when (orientation) {
                North -> Position(position.x, position.y - velocity)
                South -> Position(position.x, position.y + velocity)
                East -> Position(position.x + velocity, position.y)
                West -> Position(position.x - velocity, position.y)
            }
        }
        if (!(nextPos.x >= 0 && nextPos.y >= 0 && nextPos.y < map.size && nextPos.x < map[0].size)) {
            logger.info("[${(entity as? Adventurer)?.name ?: entity.identifier}] action failed: Destination out of map")
            return null
        }
        if (entity.isRestrictedTile(map[nextPos.y][nextPos.x])) {
            logger.info("[${(entity as? Adventurer)?.name ?: entity.identifier}] action failed: Cannot access this tile")
            return null
        }

        entity.run {
            map[position.y][position.x] = currentTile
            position.apply {
                x = nextPos.x
                y = nextPos.y
            }
            currentTile = map[position.y][position.x]
            map[position.y][position.x] = this
            if (canPickup(currentTile)) {
                pickup(currentTile)
            }
        }
        return entity
    }

    /**
     * Rotates the entity on the left.
     *
     * @param entity the [MovableEntity] that executes the action.
     * @param map the [MappedData] map that holds entities position.
     * @return [entity]
     */
    fun rotateLeft(entity: MovableEntity, map: MappedData): MovableEntity {
        entity.run {
            orientation = when (orientation) {
                North -> West
                South -> East
                East -> North
                West -> South
            }
        }
        return entity
    }

    /**
     * Rotates the entity on the right.
     *
     * @param entity the [MovableEntity] that executes the action.
     * @param map the [MappedData] map that holds entities position.
     * @return [entity], null if something went wrong.
     */
    fun rotateRight(entity: MovableEntity, map: MappedData): MovableEntity {
        entity.run {
            orientation = when (orientation) {
                North -> East
                South -> West
                East -> South
                West -> North
            }
        }
        return entity
    }
}