package nicestring

fun String.isNice(): Boolean = listOf(one(), two(), three()).count { it } >= 2

private fun String.one() = listOf("bu", "ba", "be").none { contains(it) }
private fun String.two() = count { it in "aeiou" } >= 3
private fun String.three() = zipWithNext().any { (f, s) -> f == s }