package game.entities

import game.Position
import game.exceptions.GameRuleException

/**
 * Defines a 2D map type able to hold [GameEntity].
 */
typealias MappedData = List<MutableList<GameEntity>>

/**
 * [GameEntity] implementation of a map.
 * Generates a 2D [MappedData] map of size [coordinates].
 *
 * @param coordinates the size of the map.
 */
class GameMap(val coordinates: Position) : GameEntity("C", coordinates) {
    /**
     * 2D map.
     */
    val mapArray: MappedData = List(coordinates.y) { MutableList<GameEntity>(coordinates.x) { Tile() } }

    /**
     * Loads [entities] at their position on the map.
     *
     * @param entities the entities to load.
     * @throws GameRuleException if an entity is out of map, or overlaps with another entity.
     */
    fun loadEntities(entities: List<GameEntity>) {
        val movableEntity = entities.filterIsInstance<MovableEntity>()

        entities.forEach {
            if (it.position.x < mapArray[0].size && it.position.y < mapArray.size)
                mapArray[it.position.y][it.position.x] = it
            else
                throw GameRuleException("[$it] is out of bounds")
        }
        movableEntity.forEach {
            val entityOnMap = mapArray[it.position.y][it.position.x]
            if (entityOnMap != it && it.isRestrictedTile(entityOnMap)) {
                throw GameRuleException("[$it] overlaps $entityOnMap")
            }
        }

    }
}