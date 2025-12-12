package dev.rudii14.aoc.util.data

data class Point3(val x: Int, val y: Int, val z: Int) {
    operator fun minus(other: Point3) = Point3(x - other.x, y - other.y, z - other.z)
    operator fun times(other: Point3) = Point3(x * other.x, y * other.y, z * other.z)
    
    companion object {
        fun from(data: List<Int>): Point3 = Point3(data[0], data[1], data[2])
    }
}
