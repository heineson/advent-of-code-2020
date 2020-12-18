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

    return parseTokens(tokens, null)
}

fun parseOp(s: String): Op {
    return when (s) {
        "+" -> Op.PLUS
        "*" -> Op.TIMES
        else -> throw IllegalArgumentException("No operator: $s")
    }
}

fun parseTokens(ts: List<String>, right: Expr?): Expr {
    if (ts.isEmpty()) {
        return right!!
    }
    val elem = ts.last()
    val rest = ts.dropLast(1)

    if (elem.matches("""\d+""".toRegex())) {
        return parseTokens(rest, Expr(k = elem.toLong()))
    }
    return when (elem) {
        "+" -> Expr(parseTokens(rest, right), right, Op.PLUS, null)
        "*" -> Expr(parseTokens(rest, right), right, Op.TIMES, null)
        else -> {
            if (elem.endsWith(")")) {
                val itemCount = rest.takeLastWhile { !it.startsWith("(") }.count() + 1
                val items = (rest.takeLast(itemCount) + elem).map { it.removePrefix("(").removeSuffix(")") }
                println("Expr(parseTokens(${rest.dropLast(itemCount + 1)}, right), parseTokens(${items}, right)), ${rest.dropLast(itemCount).last()}")
                return Expr(parseTokens(rest.dropLast(itemCount + 1), right), parseTokens(items, right), parseOp(rest.dropLast(itemCount).last()))
            }
            throw IllegalArgumentException("Should not get here, value: $elem")
        }
    }
}

fun eval(e: Expr): Long {
    if (e.k != null) {
        return e.k
    }
    if (e.op != null) {
        return when (e.op) {
            Op.PLUS -> {
                println("Eval: ${e.l} + ${e.r}")
                eval(e.l!!) + eval(e.r!!)
            }
            Op.TIMES -> {
                println("Eval: ${e.l} * ${e.r}")
                eval(e.l!!) * eval(e.r!!)
            }
        }
    }
    return -1

}

fun main() {
    val data = testData.take(2).map { parser(it) }
    data.forEach { println(it) }
    println()
    data.forEach { println(eval(it)) }
    println()
    println("Part 1: ${data.map { eval(it) }.sum()}")
}

val testData = """
    1 + 2 * 3 + 4 * 5 + 6
    2 * 3 + (4 * 5)
    ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
""".trimIndent().split("\n")
