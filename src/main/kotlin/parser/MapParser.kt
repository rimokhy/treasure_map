package parser

import game.Position
import game.entities.*
import game.exceptions.GameRuleException
import game.toOrientation
import java.io.BufferedReader
import java.io.File
import java.io.InputStream

/**
 * Defines a map of regex patterns associated with [EntityInitiator] handles
 */
typealias EntityMatchers = Map<Regex, EntityInitiator>

/**
 * Defines a function type to parse lines that matched with associated regex pattern.
 *
 * @param match [MatchResult] that matched with specified pattern.
 * @return created [GameEntity], null if something went wrong.
 */
typealias EntityInitiator = (match: MatchResult) -> GameEntity?

/**
 * Function of type [EntityInitiator] to parse lines that matched with mountain or map regex pattern.
 */
val commonEntityParser: EntityInitiator = { match: MatchResult ->
    val identifier = match.groups["identifier"]!!.value

    when (identifier) {
        "C" -> GameMap(Position(match.groups["x"]!!.value.toInt(), match.groups["y"]!!.value.toInt()))
        "M" -> Mountain(Position(match.groups["x"]!!.value.toInt(), match.groups["y"]!!.value.toInt()))
        else -> null
    }
}

/**
 * Function of type [EntityInitiator] to parse lines that matched with treasure regex pattern.
 */
val treasureParser: EntityInitiator = { match: MatchResult ->
    Treasure(Position(match.groups["x"]!!.value.toInt(),
            match.groups["y"]!!.value.toInt()),
            match.groups["quantity"]!!.value.toInt())
}

/**
 * Function of type [EntityInitiator] to parse lines that matched with adventurer regex pattern.
 */
val adventurerParser: EntityInitiator = { match: MatchResult ->
    Adventurer(match.groups["name"]!!.value,
            Position(match.groups["x"]!!.value.toInt(), match.groups["y"]!!.value.toInt()),
            match.groups["orientation"]!!.value.toOrientation(),
            match.groups["movements"]!!.value)
}

/**
 * Provide parsing utilities to compute configured entities.
 */
object MapParser {
    /**
     * Stores parsed game entities.
     */
    private val gameEntities = mutableListOf<GameEntity>()

    /**
     * Define pattern matchers [EntityMatchers] associated with their handler [EntityInitiator].
     */
    private val parsingMatchers: EntityMatchers = mapOf(
            "^(?<identifier>[A-Z]).*?-.*?(?<x>[0-9]+).*?-.*?(?<y>[0-9]+)\$".toRegex() to commonEntityParser,
            "^T.*?-.*?(?<x>[0-9]+).*?-.*?(?<y>[0-9]+).*?-.*?(?<quantity>[0-9]+)\$".toRegex() to treasureParser,
            "^A.*?-.*?(?<name>[a-zA-Z]+).*?-.*?(?<x>[0-9]+).*?-.*?(?<y>[0-9]+).*?-.*?(?<orientation>[N|S|E|W]).*?-.*?(?<movements>[a-zA-Z]+)\$".toRegex() to adventurerParser
    )

    /**
     * Validates game configuration.
     *
     * @throws GameRuleException if a configuration error is detected.
     */
    private fun validateGameConfiguration() {
        gameEntities.filterIsInstance<GameMap>().takeIf { it.size == 1 }
                ?: throw GameRuleException("One map is required to start game.")
        gameEntities.filterIsInstance<Treasure>().takeIf { it.isNotEmpty() }
                ?: throw GameRuleException("The game configuration requires at least one treasure.")
        gameEntities.filterIsInstance<Adventurer>().takeIf { it.isNotEmpty() }
                ?: throw GameRuleException("The game configuration requires at least one adventurer.")
    }

    /**
     * Checks if a [line] matches with [parsingMatchers] pattern. If so, add the [EntityInitiator] result to [GameEntity].
     *
     * @param line a configuration line.
     */
    fun parseLine(line: String) {
        line.trim().takeUnless { it.startsWith("#") }?.let { it ->
            parsingMatchers.forEach { (regex, initializer) ->
                regex.matchEntire(it)?.run { initializer(this) }?.let {
                    gameEntities.add(it)
                }
            }
        }
    }

    /**
     * Parses game configuration from a [BufferedReader]. The function iterate the reader line by line and delegates behavior to [parseLine].
     * At the end, calls [validateGameConfiguration].
     *
     * @param reader the map configuration file as [BufferedReader].
     * @throws GameRuleException if the game configuration is not valid.
     * @return a list of generated game entities [GameEntity].
     */
    fun parseMap(reader: BufferedReader): List<GameEntity> {
        gameEntities.clear()
        reader.forEachLine {
            parseLine(it)
        }
        validateGameConfiguration()
        return gameEntities
    }

    /**
     * Delegates to [parseMap] with a buffered reader instanciated from [stream].
     *
     * @param stream the map configuration file as [InputStream].
     * @throws GameRuleException if the game configuration is not valid
     * @return a list of generated game entities [GameEntity].
     */
    fun parseMap(stream: InputStream): List<GameEntity> = parseMap(stream.bufferedReader())

    /**
     * Delegates to [parseMap] with a buffered reader instanciated from [file].
     *
     * @param file the map configuration file as [File].
     * @throws GameRuleException if the game configuration is not valid.
     * @return a list of generated game entities [GameEntity].
     */
    fun parseMap(file: File): List<GameEntity> = parseMap(file.bufferedReader())

    /**
     * Delegates to [parseMap] with a file instanciated from [filename].
     *
     * @param filename the map configuration file name.
     * @throws GameRuleException if the game configuration is not valid.
     * @return a list of generated game entities [GameEntity].
     */
    fun parseMap(filename: String): List<GameEntity> = parseMap(File(filename))
}