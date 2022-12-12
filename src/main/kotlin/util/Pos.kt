package util

data class Pos(val v: Int, val h: Int) {
    fun move(m: Pos) = copy(v = v + m.v, h = h + m.h)

    fun top() = move(Pos(-1, 0))
    fun bottom() = move(Pos(1, 0))
    fun left() = move(Pos(0, -1))
    fun right() = move(Pos(0, 1))

    companion object {
        val ZERO = Pos(0, 0)
    }
}
