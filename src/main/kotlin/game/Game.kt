package game

import game.entities.Adventurer
import game.entities.GameEntity
import game.entities.GameMap
import game.entities.MovableEntity
import org.slf4j.LoggerFactory
import java.io.File

/**
 * Game behavior class.
 */
class Game(mapData: List<GameEntity>) {
    /**
     * The game map.
     */
    val treasureMap: GameMap = mapData.filterIsInstance<GameMap>()[0]
    /**
     * All fixed position entities (mountain, treasures).
     */
    val gameEntities: List<GameEntity> = mapData.filterNot { it is GameMap || it is MovableEntity }
    /**
     * All movable entities (adventurers).
     */
    val movableEntities: List<MovableEntity> = mapData.filterIsInstance<MovableEntity>()
    val logger = LoggerFactory.getLogger(Game::class.java)

    init {
        treasureMap.loadEntities(gameEntities)
        treasureMap.loadEntities(movableEntities)
    }

    /**
     * Execute [movableEntities] turns while they still have movements.
     */
    fun play() {
        logger.info("Game starts")
        while (movableEntities.any { it.hasMovement }) {
            movableEntities.filter { it.hasMovement }.forEach {
                logger.info("${(it as? Adventurer)?.name ?: it.identifier} plays [${it.currentAction}]")
                it.executeAction(treasureMap.mapArray)
                logger.info(it.toString())
            }
        }
        logger.info("Game ends")
        movableEntities.forEach {
            logger.info(it.toString())
        }
    }

    /**
     * Writes game result to [outFile].
     *
     * @param outFile the output file.
     */
    fun writeResult(outFile: File) {
        logger.info("Writing output to file: ${outFile.absolutePath}")
        val bufferedWriter = outFile.apply { createNewFile() }.bufferedWriter()
        bufferedWriter.append(treasureMap.toString())
                .append("\n")
                .also { writer -> gameEntities.forEach { writer.append(it.toString()).append("\n") } }
                .also { writer -> movableEntities.forEach { writer.append(it.toString()).append("\n") } }
        bufferedWriter.close()
    }
}
