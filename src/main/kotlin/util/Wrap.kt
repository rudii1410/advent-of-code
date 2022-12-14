package util

fun <R, T1, T2> wrap(a: T1, b: T2, block: (T1, T2) -> R): R {
    return block(a, b)
}