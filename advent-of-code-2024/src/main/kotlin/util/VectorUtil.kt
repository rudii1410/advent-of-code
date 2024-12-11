package util

val TOP_LEFT = Vector2(-1, -1)
val TOP = Vector2(0, -1)
val TOP_RIGHT = Vector2(1, -1)
val LEFT = Vector2(-1, 0)
val RIGHT = Vector2(1, 0)
val BOTTOM_LEFT = Vector2(-1, 1)
val BOTTOM = Vector2(0, 1)
val BOTTOM_RIGHT = Vector2(1, 1)

val ALL_AXIS_DIRECTION = listOf(
    TOP_LEFT, TOP, TOP_RIGHT,
    LEFT, RIGHT,
    BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT
)

val FOUR_DIRECTION = listOf(
    TOP, RIGHT, BOTTOM, LEFT
)
val FOUR_DIRECTION_MAP = mapOf(
    TOP to 0, RIGHT to 1, BOTTOM to 2, LEFT to 3
)

fun Vector2.withinBound(size: Int): Boolean {
    return withinBound(size, size)
}

fun Vector2.withinBound(xSize: Int, ySize: Int): Boolean {
    return x >= 0 && y >= 0 && x < xSize && y < ySize
}