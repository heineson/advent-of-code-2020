package se.heinszn.aoc2020.day18

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

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
        "+" -> Expr(parseTokens(rest, right), right, Op.PLUS)
        "*" -> Expr(parseTokens(rest, right), right, Op.TIMES)
        else -> {
            if (elem.endsWith(")")) {
                val el = elem.removeSuffix(")")
                var pCount = elem.takeLastWhile { it == ')' }.count()

                var itemCount = 0
                while (pCount > 0) {
                    itemCount++
                    val item = rest.takeLast(itemCount).first()
                    if (item.startsWith("(")) {
                        pCount -= item.takeWhile { it == '(' }.count()
                    }
                    if (item.endsWith(")")) {
                        pCount += item.takeLastWhile { it == ')' }.count()
                    }
                }

                //val itemCount = rest.takeLastWhile { !it.startsWith("(") }.count() + 1
                val its = rest.takeLast(itemCount)
                println("Test: $itemCount $its")
                val items = listOf(its.first().removePrefix("(")) + its.drop(1) + el


                val l = rest.dropLast(itemCount + 1)
                val op = rest.dropLast(itemCount).lastOrNull()
                println("Expr(parseTokens($l), parseTokens(${items})), $op")
                return Expr(parseTokens(l, right), parseTokens(items, right), if (op == null) null else parseOp(op) )
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
    throw IllegalStateException("No op and no number: ${e.l}, ${e.r}")
}

fun main() {
    val data = testData.map { parser(it) }
    data.forEach { println(it) }
    println()
    data.forEach { println(eval(it)) }
    println()
    println("Part 1: ${data.map { eval(it) }.sum()}")
}

val testData = """
    1 + 2 * 3 + 4 * 5 + 6
    2 * 3 + (4 * 5)
    5 + (8 * 3 + 9 + 3 * 4 * 3)
    5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
    ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
""".trimIndent().split("\n")
