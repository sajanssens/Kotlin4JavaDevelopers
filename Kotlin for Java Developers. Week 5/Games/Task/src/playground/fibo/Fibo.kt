package playground.fibo

import playground.inlining.eq

fun fibonacci(): Sequence<Int> = sequence {
    var start = 0 to 1
    while (true) {
        yield(start.first)
        start = start.second to start.first + start.second
    }
}

fun main() {
    fibonacci().take(4).toList().toString() eq "[0, 1, 1, 2]"
    fibonacci().take(10).toList().toString() eq "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34]"
}