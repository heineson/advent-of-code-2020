package se.heinszn.aoc2020.day19

import se.heinszn.aoc2020.readFile

data class Elem(val pointer: Int?, val value: String?) {
    override fun toString(): String {
        return pointer?.toString() ?: value ?: "X"
    }
}
fun parseRule(line: String, ruleMap: MutableMap<Int, List<List<Elem>>>) {
    val parts = line.split(':')
    val index = parts[0].toInt()
    val rule = parts[1].trim()

    if (rule.startsWith("\"")) {
        ruleMap[index] = listOf(listOf(Elem(null, rule.removeSurrounding("\""))))
    } else {

        val alternatives = rule.split("|")
            .map { it.trim() }
            .map { pointers -> pointers.split(" ").map { Elem(it.toInt(), null) } }
        ruleMap[index] = alternatives
    }
}

fun hasValue(l: List<List<Elem>>): String? {
    if (!l.all { l2 -> l2.all { it.value != null } }) {
        return null
    }

    val v:String = l.joinToString("|") { it.joinToString("") }
    return if(v.contains("|")) "($v)" else v
}

fun subst(ruleMap: MutableMap<Int, List<List<Elem>>>): MutableMap<Int, List<List<Elem>>> {
    val newMap: MutableMap<Int, List<List<Elem>>> = mutableMapOf()
    ruleMap.forEach { entry ->
        newMap[entry.key] = entry.value.map { l2: List<Elem> -> l2.map {
            val rule = ruleMap[it.pointer]
            if (it.pointer != null && hasValue(rule!!) != null)
                Elem(null, hasValue(rule))
            else it
        } }.map { list ->
            println("Mapf ${entry.key}: $list -> ${
                if (list.all { it.value != null })
                    listOf(Elem(null, list.map { it.value }.joinToString("")))
                else listOf()
            }")
            if (list.all { it.value != null })
                listOf(Elem(null, list.map { it.value }.joinToString("")))
            else list
        }
    }

    return newMap
}

fun main() {
    var ruleMap = mutableMapOf<Int, List<List<Elem>>>()
    //testRules.map { parseRule(it, ruleMap) }
    readFile({ parseRule(it, ruleMap) }, "./rules.txt")
    println("RuleMap: $ruleMap\n")
    while (ruleMap[0]!!.size != 1 || ruleMap[0]!![0].size != 1 || ruleMap[0]!![0][0].value == null) {
        ruleMap = subst(ruleMap)
    }
    val regex = ruleMap[0]!![0][0].value!!.toRegex()
    println("Regex: $regex")

    //println(testMsgs.filter { regex.matches(it) }.count())
    println(readFile({ it }, "./msgs.txt").filter{ regex.matches(it) }.count())
}

val testRules = """
    0: 4 1 5
    1: 2 3 | 3 2
    2: 4 4 | 5 5
    3: 4 5 | 5 4
    4: "a"
    5: "b"
""".trimIndent().split("\n")

val testMsgs = """
    ababbb
    bababa
    abbbab
    aaabbb
    aaaabbb
""".trimIndent().split("\n")
