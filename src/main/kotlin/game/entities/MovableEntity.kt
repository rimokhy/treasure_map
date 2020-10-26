package game.entities

import game.ActionManager
import game.Game
import game.Orientation
import game.Position
import org.slf4j.LoggerFactory

/**
 * [GameEntity] implementation for a movable entity.
 * Provides utilities to move and pickup items on the map.
 *
 * @param identifier the entity identifier.
 * @param position the entity's position on the map.
 * @param orientation the entity's orientation on the map.
 * @param movements the entity's sequence of movements.
 */
abstract class MovableEntity(override val identifier: String, override val position: Position, open var orientation: Orientation, open val movements: String) : GameEntity(identifier, position) {
    /**
     * Number of tile that a movable entity can cover in one turn.
     */
    open val velocity = 1
    /**
     * Definition of which [GameEntity] a [MovableEntity] can pickup
     */
    open val pickableEntities = listOf(Treasure::class)
    /**
     * Index in [movements] string.
     */
    open var movement = 0
    /**
     * Maximum movements an entity can execute
     */
    open val maxMovements = Int.MAX_VALUE
    /**
     * The tile the [MovableEntity] occupies.
     */
    open var currentTile: GameEntity = Tile()
    /**
     * Entity current action.
     */
    val currentAction
        get() = movements[movement]
    /**
     * Whether or not the entity has movement left.
     */
    val hasMovement
        get() = movement < maxMovements && movement < movements.length
    /**
     * Number of treasure the entity carries.
     */
    var treasure: Int = 0
    val logger = LoggerFactory.getLogger(Game::class.java)

    /**
     * Execute the [currentAction].
     *
     * @param map the game map.
     */
    fun executeAction(map: MappedData) {
        (ActionManager.actions[currentAction]?.invoke(this, map)
                ?: logger.info("[${(this as? Adventurer)?.name ?: this.identifier}] Action failed: ${movements[movement]}"))
        movement += 1
    }

    /**
     * Whether or not the entity can pickup the object [gameEntity].
     *
     * @param gameEntity the object to pickup.
     * @return true if the [gameEntity] can be picked up, false otherwise.
     */
    fun canPickup(gameEntity: GameEntity) = gameEntity::class in pickableEntities

    /**
     * Pickup a [gameEntity].
     *
     * @param gameEntity the object to pickup.
     * @return null if pickup failed.
     */
    fun pickup(gameEntity: GameEntity) = when (gameEntity) {
        is Treasure -> {
            gameEntity.takeIf { gameEntity.quantity > 0 }?.quantity?.let {
                logger.info("[${(this as? Adventurer)?.name ?: this.identifier}] picked up ${gameEntity.identifier}")
                gameEntity.quantity -= 1
                treasure += 1
            }
        }
        else -> null
    }

    /**
     * Whether or not the entity can move to the [GameEntity] tile.
     *
     * @param gameEntity the object to pickup.
     * @return true if [gameEntity] is a restricted tile, false otherwise.
     */
    open fun isRestrictedTile(gameEntity: GameEntity): Boolean {
        return gameEntity is Mountain || gameEntity is MovableEntity
    }

    override fun toString() = "$identifier - $position - $orientation - $treasure"
}