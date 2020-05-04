package games.game2048

// https://github.com/slobanov/kotlin-for-java-dev/blob/master/src/main/kotlin/ru/amai/study/coursera/kotlin/week5/game2048/Game2048Helper.kt
/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
    // imperative style
    /*    val nonNulls = filterNotNull().toMutableList()

        with(nonNulls) {
            var i = 1
            while(i < this.size){
                if (this[i] == this[i - 1]) {
                    this[i - 1] = merge(this[i])
                    this.removeAt(i)
                }
                ++i
            }
        }

        return nonNulls*/

    // functional style
    return fold(listOf()) { list, element -> // reduce: start met listOf(); elke stap: bekijk element: als ... dan nieuwe tussenlijst maken
        when (element) {
            null -> list                                            // element is null, skip, dus lijst wijzigt niet
            list.lastOrNull() -> list.replaceLast(merge(element))   // element is gelijk aan vorige (laatste in de tussenlijst): vervang vorige element met merged
            else -> list + element                                  // element is niet null en anders dan vorige: append
        }
    }
}

private fun <T : Any> List<T>.replaceLast(element: T) = dropLast(1) + element