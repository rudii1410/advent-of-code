package dev.rudii14.aoc.util.data

data class Point2(val x: Int, val y: Int) {
    operator fun minus(other: Point2) = Point2(x - other.x, y - other.y)
    operator fun plus(other: Point2) = Point2(x + other.x, y + other.y)
    
    fun withinBound(size: Int): Boolean {
        return withinBound(size, size)
    }
    
    fun withinBound(xSize: Int, ySize: Int): Boolean {
        return x >= 0 && y >= 0 && x < xSize && y < ySize
    }
}
