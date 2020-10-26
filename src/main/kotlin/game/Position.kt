package game

/**
 * 2D position class.
 *
 * @param x the horizontal axis.
 * @param y the vertical axis.
 */
data class Position(var x: Int, var y: Int) {
    override fun toString(): String {
        return "$x - $y"
    }
}