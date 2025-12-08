package dev.rudii14.aoc.util.data

class UnionFind<T>(vertices: Set<T>) {
    private val root = mutableMapOf<T, T>()
    
    init {
        for (v in vertices) {
            root[v] = v
        }
    }
    
    fun findRoot(data: T): T {
        if (root[data] == data) return data
        return root[data]!!
            .let(::findRoot)
            .also { root[data] = it }
    }
    
    fun isSameSet(i: T, j: T): Boolean {
        return findRoot(i) == findRoot(j)
    }
    
    fun union(i: T, j: T) {
        val rootI = findRoot(i)
        val rootJ = findRoot(j)
        if (rootI == rootJ) return

        root[rootI] = rootJ
    }
}