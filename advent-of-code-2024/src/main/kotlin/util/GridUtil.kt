package util

typealias Grid<T> = List<List<T>>
typealias MutableGrid<T> = MutableList<MutableList<T>>

fun <T> createGrid(size: Int, init: (Int) -> T) = List(size) { List(size, init) }
fun <T> createMutableGrid(size: Int, init: (Int) -> T) = MutableList(size) { MutableList(size, init) }
