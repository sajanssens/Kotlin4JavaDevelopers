package games.gameOfFifteen

import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val allCells = board.getAllCells()
        initializer.initialPermutation.forEachIndexed { index, value ->
            board[allCells.elementAt(index)] = value
        }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        var seqNum = 1
        return board.all { it == null || it == seqNum++ }
    }

    override fun processMove(direction: Direction) {
        // temp scope, in which we can access board (the so called context object) with this.
        // Behaves like a function "inside" the board class, so we dive into the board scope
        // therefore: with(board), do ...
        // like a receiver function
        with(board) {
            // val emptyCell = this.find { it == null }
            // val neighbour = emptyCell?.getNeighbour(direction.reversed())!!
            // this[emptyCell] = this[neighbour]
            // this[neighbour] = null

            // // functional style, with let (return value is Lambda result of outer let, so Unit)
            // since we don't need a return value from processMove, I prefer this variant
            find { it == null }?.let { emptyCell ->
                emptyCell.getNeighbour(direction.reversed())?.let { neighbour ->
                    this[emptyCell] = this[neighbour]
                    this[neighbour] = null
                    // return nothing
                }
                // return nothing
            }

            // // same, but with also instead of let (now return value is contextObject, so emptyCell)
            // val result = find { it == null }?.also { emptyCell ->
            //     emptyCell.getNeighbour(direction.reversed())?.also { neighbour ->
            //         this[emptyCell] = this[neighbour]
            //         this[neighbour] = null
            //     }
            // }
        }
    }

    override fun get(i: Int, j: Int): Int? =
    // board.get(board.getCell(i, j))
            // or with run:
            board.run {  // as if this is a function inside the board, board comes in as "this"
                get(getCell(i, j))
            }

}

