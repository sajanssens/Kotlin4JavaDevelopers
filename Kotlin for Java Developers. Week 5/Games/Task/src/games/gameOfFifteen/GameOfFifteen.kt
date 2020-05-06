package games.gameOfFifteen

import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameOfFifteenInitializer()): Game =
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
        // Behaves like a function "inside" the board class
        with(board) {
            val emptyCell = this.find { it == null }
            emptyCell?.also { cell ->
                cell.getNeighbour(direction.reversed())?.also { neighbour ->
                    this[cell] = this[neighbour]
                    this[neighbour] = null
                }
            }
        }

    }

    override fun get(i: Int, j: Int): Int? =
            board.run { get(getCell(i, j)) }

}


