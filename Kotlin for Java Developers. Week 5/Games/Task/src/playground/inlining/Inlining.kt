package playground.inlining

// Write the code that the Kotlin compiler will generate while playground.inlining the filter function
// (instead of calling it). Note that the compiler generates Java bytecode, but for
// simplicity, write the similar code in Kotlin. The simplified declaration of 'filter' is given below.

fun filterNonZero(list: List<Int>) = list.filter { it != 0 }

fun filterNonZeroGenerated(list: List<Int>): List<Int> {
    val res = mutableListOf<Int>()
    for (i in list) {
        if (i != 0) res.add(i)
    }
    return res
}

fun main() {
    val list = listOf(1, 2, 3)

    filterNonZero(list).toString() eq "[1, 2, 3]"
    filterNonZeroGenerated(list).toString() eq "[1, 2, 3]"
}

infix fun String.eq(s: String) = println(if (this == s) "OK" else "NOK $this vs $s")

inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
    val destination = ArrayList<T>()
    for (element in this) {
        if (predicate(element)) {
            destination.add(element)
        }
    }
    return destination
}
