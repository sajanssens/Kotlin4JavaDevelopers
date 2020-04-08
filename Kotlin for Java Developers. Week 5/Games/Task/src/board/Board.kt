package board

data class Cell(val i: Int, val j: Int) {
    override fun toString() = "($i, $j)"
}

enum class Direction {
    UP, DOWN, RIGHT, LEFT;

    fun reversed() = when (this) {
        UP -> DOWN
        DOWN -> UP
        RIGHT -> LEFT
        LEFT -> RIGHT
    }

    fun delta() =
            when (this) {
                board.Direction.UP -> -1 to 0
                board.Direction.DOWN -> 1 to 0
                board.Direction.LEFT -> 0 to -1
                board.Direction.RIGHT -> 0 to 1
            }

}

interface SquareBoard {
    val width: Int

    fun getCellOrNull(i: Int, j: Int): Cell?
    fun getCell(i: Int, j: Int): Cell

    fun getAllCells(): Collection<Cell>

    fun getRow(i: Int, jRange: IntProgression): List<Cell>
    fun getColumn(iRange: IntProgression, j: Int): List<Cell>

    fun Cell.getNeighbour(direction: Direction): Cell?
}

interface GameBoard<T> : SquareBoard {

    operator fun get(cell: Cell): T?
    operator fun set(cell: Cell, value: T?)

    fun filter(predicate: (T?) -> Boolean): Collection<Cell>
    fun find(predicate: (T?) -> Boolean): Cell?
    fun any(predicate: (T?) -> Boolean): Boolean
    fun all(predicate: (T?) -> Boolean): Boolean
}