package games.gameOfFifteen

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val shuffled = (1 .. 15).shuffled().toMutableList()
        if (!isEven(shuffled)) shuffled.swap(0, 1)
        shuffled
    }
}

private fun MutableList<Int>.swap(i: Int, j: Int) {
    // i becomes j and j becomes i
    this[i] = this[j].also { this[j] = this[i] }
}

