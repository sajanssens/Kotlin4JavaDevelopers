package nicestring

fun main() {
    println("HELLO!")
    val p = Person("Bram")
    println(p)
    p.name
}

data class Person(val name: String)