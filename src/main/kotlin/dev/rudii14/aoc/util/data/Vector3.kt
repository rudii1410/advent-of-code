package dev.rudii14.aoc.util.data

data class Vector3(val x: Double, val y: Double, val z: Double) {
    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble())
    
    operator fun minus(other: Vector3): Vector3 = Vector3(x - other.x, y - other.y, z - other.z)
    
    operator fun times(other: Vector3): Vector3 = Vector3(x * other.x, y * other.y, z * other.z)
    
    companion object {
        fun from(data: List<Double>) = Vector3(data[0], data[1], data[2])
    }
}