package util

typealias Grid<T> = List<List<T>>
typealias MutableGrid<T> = MutableList<MutableList<T>>

fun <T> createGrid(size: Int, init: (Int) -> T) = List(size) { List(size, init) }
fun <T> createMutableGrid(size: Int, init: (Int) -> T) = MutableList(size) { MutableList(size, init) }
fun <T> createMutableGrid(xSize: Int, ySize: Int, init: (Int) -> T) = MutableList(ySize) { MutableList(xSize, init) }
fun <T> Grid<T>.get(pos: Vector2) = this[pos.y.toInt()][pos.x.toInt()]
fun <T> MutableGrid<T>.set(pos: Vector2, value: T) {
    this[pos.y.toInt()][pos.x.toInt()] = value
}
