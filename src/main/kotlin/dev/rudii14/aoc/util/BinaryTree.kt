package dev.rudii14.aoc.util

import kotlin.math.max

data class Node<T>(
    val value: T,
    val left: Node<T>? = null,
    val right: Node<T>? = null,
    val height: Int = 1,
    val count: Int = 1,
)

fun <T> Node<T>?.getHeight(): Int {
    return this?.height ?: 0
}

fun <T> Node<T>?.getBalance(): Int {
    return if (this == null) 0 else left.getHeight() - right.getHeight()
}

fun <T> Node<T>.rotateRight(): Node<T> {
    val x = left ?: return this
    val y = x.right

    return x.copy(
        right = this.copy(
            left = y,
            height = 1 + max(left.getHeight(), right.getHeight())
        ),
        height = 1 + max(x.left.getHeight(), x.right.getHeight())
    )
}

fun <T> Node<T>.rotateLeft(): Node<T> {
    val x = right ?: return this
    val y = x.left

    return x.copy(
        left = this.copy(
            right = y,
            height = 1 + max(left.getHeight(), right.getHeight())
        ),
        height = 1 + max(x.left.getHeight(), x.right.getHeight())
    )
}

fun <T> insert(node: Node<T>?, value: T): Node<T> where T : Comparable<T> {
    if (node == null) return Node(value)

    return when (node.value compareTo value) {
        -1 -> {
            val left = if (node.left == null) Node(value) else insert(node.left, value)
            node.copy(left = left)
        }
        1 -> {
            val right = if (node.right == null) Node(value) else insert(node.right, value)
            node.copy(right = right)
        }
        else -> node.copy(count = node.count + 1)
    }
}

fun <T> insertBalance(node: Node<T>?, value: T): Node<T> where T : Comparable<T> {
    if (node == null) return Node(value)

    val newNode = when (node.value compareTo value) {
        1 -> node.copy(left = insertBalance(node.left, value))
        -1 -> node.copy(right = insertBalance(node.right, value))
        else -> node.copy(count = node.count + 1)
    }

    return newNode
        .copy(height = 1 + maxOf(newNode.left.getHeight(), newNode.right.getHeight()))
        .let {
            val balance = it.getBalance()

            when {
                balance > 1 && value < it.left?.value!! -> it.rotateRight()
                balance < -1 && value > it.right?.value!! -> it.rotateLeft()
                balance > 1 && value > it.left?.value!! -> {
                    it.copy(left = it.left.rotateLeft())
                        .rotateRight()
                }
                balance < -1 && value < it.right?.value!! -> {
                    it.copy(right = it.right.rotateRight())
                        .rotateLeft()
                }
                else -> it
            }
        }
}
