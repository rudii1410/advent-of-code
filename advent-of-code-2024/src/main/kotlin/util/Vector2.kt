package util

data class Vector2(val x: Double, val y: Double) {
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

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
    operator fun times(multiplier: Int): Vector2 {
        return this.copy(
            x = x * multiplier,
            y = y * multiplier
        )
    }

    override fun toString(): String {
        return "$x,$y"
    }

    fun abs(): Vector2 {
        return this.copy(
            x = kotlin.math.abs(x),
            y = kotlin.math.abs(y)
        )
    }

    fun isInside(rect: Rectangle): Boolean {
        return x >= rect.x.x && x <= rect.y.x && y >= rect.x.y && y <= rect.y.y
    }

    companion object {
        val ZERO = Vector2(0.0, 0.0)
    }
}
