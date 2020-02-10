package mastermind

import kotlin.math.min

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val right = right(secret, guess)
    val wrong = wrong(secret, guess)
    return Evaluation(right, wrong - right)
}

private fun right(secret: String, guess: String): Int {
    var right = 0
    for ((i, g) in guess.withIndex()) {
        if (secret[i] == g) {
            right++
        }
    }
    return right
}

private fun wrong(secret: String, guess: String): Int {
    var wrong = 0
    var eval = secret
    for (g in guess) {
        if (eval.contains(g)) {
            wrong++
            eval = eval.replaceFirst(g, ' ')
        }
    }
    return wrong
}

fun evaluateGuessF(secret: String, guess: String): Evaluation {
    val right = secret.zip(guess).count { it.first == it.second }
    val wrong = "ABCDEF".sumBy { c ->
        min(secret.count { s -> s == c }, guess.count { g -> g == c })
    }
    return Evaluation(right, wrong - right)
}


infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}


fun List<Int>.allNonZero() = all { it != 0 }
fun List<Int>.allNonZero1() = none { it == 0 }
fun List<Int>.allNonZero2() = !any { it == 0 }

fun List<Int>.containsZero() = any { it == 0 }
fun List<Int>.containsZero1() = !all { it != 0 }
fun List<Int>.containsZero2() = !none { it == 0 }

fun main(args: Array<String>) {
    val list1 = listOf(1, 2, 3)
    list1.allNonZero() eq true
    list1.allNonZero1() eq true
    list1.allNonZero2() eq true

    list1.containsZero() eq false
    list1.containsZero1() eq false
    list1.containsZero2() eq false

    val list2 = listOf(0, 1, 2)
    list2.allNonZero() eq false
    list2.allNonZero1() eq false
    list2.allNonZero2() eq false

    list2.containsZero() eq true
    list2.containsZero1() eq true
    list2.containsZero2() eq true
}
