package util

data class Pos2(val x: Int, val y: Int) {
    fun get(m: Pos2) = copy(x = x + m.x, y = y + m.y)

    fun getTop() = get(TOP)
    fun getBottom() = get(BOTTOM)
    fun getLeft() = get(LEFT)
    fun getRight() = get(RIGHT)

    companion object {
        val ZERO = Pos2(0, 0)

        val TOP = Pos2(-1, 0)
        val BOTTOM = Pos2(1, 0)
        val LEFT = Pos2(0, -1)
        val RIGHT = Pos2(0, 1)
    }
}
