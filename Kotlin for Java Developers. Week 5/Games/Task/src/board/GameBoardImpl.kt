package board

class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {

    private val cellContents: MutableMap<Cell, T?> = mutableMapOf()

    init { for (cell in getAllCells()) cellContents[cell] = null }

    override fun get(cell: Cell): T? = cellContents[cell]

    override fun set(cell: Cell, value: T?) { cellContents[cell] = value }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = cellContents.filter { predicate(it.value) }.keys

    override fun find(predicate: (T?) -> Boolean): Cell? = filter(predicate).first()

    override fun any(predicate: (T?) -> Boolean): Boolean = contents().any(predicate)

    override fun all(predicate: (T?) -> Boolean): Boolean = contents().all(predicate)

    private fun contents() = cellContents.map { it.value }

}
