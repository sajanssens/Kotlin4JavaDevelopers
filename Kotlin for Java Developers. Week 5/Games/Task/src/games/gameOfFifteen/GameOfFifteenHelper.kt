package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    // calculate how many steps are needed to sort this permutation back to its natural ordering
    /*var count = 0
    for (i in 0 until permutation.size - 1) {
        for (j in (i + 1) until permutation.size) {
            if (permutation[i] > permutation[j]) count++
        }
    }
    return (count % 2) == 0*/

    // idem, functional style
    /*return (0 until permutation.size).map { i ->
        (0 until permutation.size)
                .filter { j -> i < j }
                .count { j -> permutation[i] > permutation[j] }
    }.sum().isEven()*/

    val visited = mutableSetOf<Int>()
    val normalizedPermutation = permutation.map { it - (permutation.min()!!) }

    fun nextCycleLength(startIndex: Int): Int {
        var currentIndex = startIndex
        var currentCycleLength = 0

        while (currentIndex !in visited) {
            visited += currentIndex
            currentCycleLength += 1
            currentIndex = normalizedPermutation[currentIndex]
        }

        return currentCycleLength
    }

    // uses the cycle method, see https://en.wikipedia.org/wiki/Permutation#Cycle_notation
    // calculates the length of each cycle; filters out cycles of length 1,
    // then, the parity of each cycle is defined as the inverse of the parity of the cycle length, i.e. cycles of length odd are even, of length even are odd.
    // because you can count the number of transpositions, i.e. (xy)-cycles
    // e.g. (3421) = (34)(42)(21) = odd
    // e.g. (34210) = (34)(42)(21)(10) = even
    // see also https://youtu.be/MpKG6FmcIHk and https://youtu.be/RQJloa1tQYg
    // finally, sum the parity's and check if this sum is even, yielding the parity of the total permutation
    return permutation.indices
            .map(::nextCycleLength)
            .filter { it > 1 }
            .map { (it - 1) % 2 }
            .sum() % 2 == 0
}

fun Int.isEven(): Boolean = this % 2 == 0
