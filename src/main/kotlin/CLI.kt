import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.defaultLazy
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import game.Game
import game.exceptions.GameRuleException
import org.slf4j.LoggerFactory
import parser.MapParser
import java.io.File
import java.io.FileNotFoundException

/**
 * Configure command line arguments using [CliktCommand]
 */
object CLI : CliktCommand() {
    val sourceFile by option("-f", "--input-file", help = "Configure source map configuration file").file(mustExist = true, mustBeReadable = true)
    val outFile by option("-o", "--output-file", help = "Configure output file").file().defaultLazy { File("out.txt") }
    val logger = LoggerFactory.getLogger(Game::class.java)

    override fun run() {
        val default = "treasure_map.txt"
        val classLoader = Thread.currentThread().contextClassLoader

        logger.info("Parsing source file: ${sourceFile?.absolutePath ?: classLoader.getResource(default)}")
        (sourceFile?.inputStream() ?: classLoader.getResourceAsStream(default))?.let { inputStream ->
            try {
                MapParser.parseMap(inputStream).let {
                    val game = Game(mapData = it)
                    game.play()
                    game.writeResult(outFile)
                }
            } catch (e: FileNotFoundException) {
                logger.error(e.message)
            } catch (e: GameRuleException) {
                logger.error(e.message)
            } catch (e: Exception) {
                logger.error("Unknown error", e)
            }
        } ?: logger.error("File ${sourceFile?.name ?: default} was not found in resource path.")
    }
}

fun main(args: Array<String>) = CLI.main(args)