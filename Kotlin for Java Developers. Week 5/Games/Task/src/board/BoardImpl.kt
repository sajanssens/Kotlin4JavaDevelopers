package board

import kotlin.math.abs

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

open class SquareBoardImpl(final override val width: Int) : SquareBoard {

    private val cells: MutableList<MutableList<Cell>> = mutableListOf()
    private val boardRange = 0 until width

    init {
        for (i in 0 until width) {
            cells += mutableListOf<Cell>()
            for (j in 0 until width) {
                cells[i].add(Cell(i + 1, j + 1))
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? = if (inBoardRange(i, j)) cells[i - 1][j - 1] else null

    override fun getCell(i: Int, j: Int): Cell = cells[i - 1][j - 1]

    override fun getAllCells(): Collection<Cell> = cells.flatMap { it.toList() }.toList()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val range = if (abs(jRange.last - jRange.first) > width) 1..width else jRange
        val row = cells[i - 1].filter { it.j in range }
        return if (jRange.step < 0) row.reversed() else row

        // imperative style:
        // val ret = mutableListOf<Cell>()
        // val row = cells[i - 1]
        // for (j in jRange) {
        //     ret.add(row[j - 1])
        // }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val range = if (abs(iRange.last - iRange.first) > width) 1..width else iRange
        val col = cells.flatMap { it.toList() }.filter { it.i in range }.filter { it.j == j }
        return if (iRange.step < 0) col.reversed() else col

        // imperative style:
        // val col = mutableListOf<Cell>()
        // for (i in range) {
        //     val row = cells[i - 1]
        //     col.add(row[j - 1])
        // }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val (deltaI, deltaJ) = direction.delta()

        val newI = this.i + deltaI
        val newJ = this.j + deltaJ

        return if (inBoardRange(newI, newJ)) getCell(newI, newJ) else null
    }

    private fun inBoardRange(i: Int, j: Int) = i - 1 in boardRange && j - 1 in boardRange
}

class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {

    private val cellContents: MutableMap<Cell, T?> = mutableMapOf()

    init {
        for (cell in getAllCells()) cellContents[cell] = null
    }

    override fun get(cell: Cell): T? = cellContents[cell]

    override fun set(cell: Cell, value: T?) {
        cellContents[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = cellContents.filter { predicate(it.value) }.keys

    override fun find(predicate: (T?) -> Boolean): Cell? = filter(predicate).first()

    override fun any(predicate: (T?) -> Boolean): Boolean = contents().any(predicate)

    override fun all(predicate: (T?) -> Boolean): Boolean = contents().all(predicate)

    private fun contents() = cellContents.map { it.value }

}