package rationals

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(sum == 5 divBy 6)
    println(sum == 10 divBy 12)
    println(117 divBy 1098 == 13 divBy 122)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half >= third)
    println(half < twoThirds)
    println(half <= half)
    println(half >= half)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}

operator fun Rational.plus(r: Rational): Rational = calc(this, r, BigInteger::plus)
operator fun Rational.minus(r: Rational): Rational = calc(this, r, BigInteger::minus)
operator fun Rational.times(r: Rational): Rational = Rational(n * r.n, d * r.d)
operator fun Rational.div(r: Rational): Rational = this * r.swap()
operator fun Rational.unaryMinus(): Rational = Rational(-n, d)
operator fun Rational.rangeTo(r: Rational): ClosedRange<Rational> = RationalRange(this, r)
class RationalRange(override val start: Rational, override val endInclusive: Rational) : ClosedRange<Rational>

private fun calc(t: Rational, r: Rational, op: (BigInteger, BigInteger) -> BigInteger): Rational {
    val drd = t.d * r.d
    return Rational(op(t.n * (drd / t.d), r.n * (drd / r.d)), drd)
}

private fun Rational.swap(): Rational = Rational(d, n)
private fun Rational.toDouble(): Double = n.toDouble() / d.toDouble()

infix fun Int.divBy(i: Int): Rational = Rational(BigInteger.valueOf(this.toLong()), BigInteger.valueOf(i.toLong()))
infix fun Long.divBy(i: Long): Rational = Rational(BigInteger.valueOf(this), BigInteger.valueOf(i))
infix fun BigInteger.divBy(i: BigInteger): Rational = Rational(this, i)

fun String.toRational(): Rational {
    if (!this.contains("/")) return Rational(BigInteger(this), ONE)

    val split = this.split("/")
    val n = BigInteger(split[0])
    val d = BigInteger(split[1])
    return Rational(n, d)
}

class Rational(val n: BigInteger, val d: BigInteger) : Comparable<Rational> {
    init {
        if (d == ZERO) throw IllegalArgumentException("d can't be zero")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        (other as Rational)

        val (nNorm, dNorm) = this.normalize()
        val (noNorm, doNorm) = other.normalize()

        return nNorm == noNorm && dNorm == doNorm
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + d.hashCode()
        return result
    }

    override fun toString(): String {
        val (nNorm, dNorm) = normalize()
        if (dNorm == ONE) return "$nNorm"
        return "$nNorm/$dNorm"
    }

    private operator fun component1(): BigInteger = n

    private operator fun component2(): BigInteger = d

    private fun normalize(): Rational {
        val gcd = n.gcd(d)
        var flip = if (n > ZERO && d < ZERO || n < ZERO && d < ZERO) -ONE else ONE
        return Rational(flip * n / gcd, flip * d / gcd)
    }

    override fun compareTo(other: Rational): Int {
        val diff = this - other
        if (diff.n == ZERO) return 0
        if (diff.toDouble() > 0) return 1
        return -1
    }
}