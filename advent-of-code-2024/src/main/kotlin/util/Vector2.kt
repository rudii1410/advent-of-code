package util

data class Vector2(val x: Int, val y: Int) {
    operator fun minus(other: Vector2): Vector2 {
        return this.copy(
            x = x - other.x,
            y = y - other.y
        )
    }
    operator fun plus(other: Vector2): Vector2 {
        return this.copy(
            x = x + other.x,
            y = y + other.y
        )
    }
    fun abs(): Vector2 {
        return this.copy(
            x = kotlin.math.abs(x), y = kotlin.math.abs(y)
        )
    }
}