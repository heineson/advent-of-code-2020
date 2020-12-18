package se.heinszn.aoc2020.day18

import java.lang.IllegalArgumentException

enum class Op { PLUS, TIMES }
data class Expr(val l: Expr? = null, val r: Expr? = null, val op: Op? = null, val k: Long? = null) {
    override fun toString(): String {
        var res = ""
        if (k != null) {
            res +=  "$k"
        }
        if (op != null) {
            res += when (op) {
                Op.PLUS -> "($l + $r)"
                Op.TIMES -> "($l * $r)"
            }
        }
        return res
    }
}

fun parser(e: String): Expr {
    val tokens = e.split(" ")
    val right = tokens.last()

    return parseTokens(tokens.dropLast(1), Expr(k = right.toLong()))
}

fun parseTokens(ts: List<String>, right: Expr): Expr {
    if (ts.isEmpty()) {
        return right
    }
    val elem = ts.last()
    val rest = ts.dropLast(1)

    if (elem.matches("""\d+""".toRegex())) {
        return parseTokens(rest, Expr(k = elem.toLong()))
    }
    return when (elem) {
        "+" -> Expr(parseTokens(rest, right), right, Op.PLUS, null)
        "*" -> Expr(parseTokens(rest, right), right, Op.TIMES, null)
        else -> throw IllegalArgumentException("Should not get here, value: ${elem}")
    }
}

fun eval(e: Expr): Long {
    if (e.k != null) {
        return e.k
    }
//    if (e.op != null) {
        return when(e.op!!) {
            Op.PLUS -> {
                println("Eval: ${e.l} + ${e.r}")
                eval(e.l!!) + eval(e.r!!)
            }
            Op.TIMES -> {
                println("Eval: ${e.l} * ${e.r}")
                eval(e.l!!) * eval(e.r!!)
            }
        }
//    }
}

fun main() {
    val data = testData.take(1).map { parser(it) }
    println(data)
    println(eval(data[0]))
}

val testData = """
    1 + 2 * 3 + 4 * 5 + 6
    2 * 3 + (4 * 5)
    ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
""".trimIndent().split("\n")
