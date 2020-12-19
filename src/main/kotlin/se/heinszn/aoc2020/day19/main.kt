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

fun hasValue(index: Int, l: List<List<Elem>>): String? {
    if (index == 8) {
        if (!l.all { l2 -> l2.all { it.value != null || it.pointer == 8 } }) {
            return null
        }

        val v:String = l.joinToString("|") { it.joinToString("") }
        return (if(v.contains("|")) "($v)" else v).replace("8", "+")
    }

    if (index == 11) {
        if (!l.all { l2 -> l2.all { it.value != null || it.pointer == 11 } }) {
            return null
        }

        if (l.size == 2) {
            val r42 = l[1][0]
            val r31 = l[1][2]
            var cnt = 0
            var regex = "($r42$r31|${r42}X$r31)"
            while (cnt < 9) {
                regex = regex.replace("X", regex)
                cnt++
            }
            println(regex)
            return regex.replace("X", "")
        }
    }

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
            if (it.pointer != null && hasValue(it.pointer, rule!!) != null)
                Elem(null, hasValue(it.pointer, rule))
            else it
        } }.map { list ->
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

    var ruleMap2 = mutableMapOf<Int, List<List<Elem>>>()
    //testRules2.map { parseRule(it, ruleMap2) }
    readFile({ parseRule(it, ruleMap2) }, "./rules2.txt")
    println("RuleMap2: $ruleMap2\n")
    while (ruleMap2[0]!!.size != 1 || ruleMap2[0]!![0].size != 1 || ruleMap2[0]!![0][0].value == null) {
        ruleMap2 = subst(ruleMap2)
        println(ruleMap2)
    }
    val regex2 = ruleMap2[0]!![0][0].value!!.toRegex()
    println("Regex2: $regex2")
    //println(testMsgs2.filter { regex2.matches(it) }.count())
    println(readFile({ it }, "./msgs.txt").filter{ regex2.matches(it) }.count())
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

val testRules2 = """
    42: 9 14 | 10 1
    9: 14 27 | 1 26
    10: 23 14 | 28 1
    1: "a"
    11: 42 31 | 42 11 31
    5: 1 14 | 15 1
    19: 14 1 | 14 14
    12: 24 14 | 19 1
    16: 15 1 | 14 14
    31: 14 17 | 1 13
    6: 14 14 | 1 14
    2: 1 24 | 14 4
    0: 8 11
    13: 14 3 | 1 12
    15: 1 | 14
    17: 14 2 | 1 7
    23: 25 1 | 22 14
    28: 16 1
    4: 1 1
    20: 14 14 | 1 15
    3: 5 14 | 16 1
    27: 1 6 | 14 18
    14: "b"
    21: 14 1 | 1 14
    25: 1 1 | 1 14
    22: 14 14
    8: 42 | 42 8
    26: 14 22 | 1 20
    18: 15 15
    7: 14 5 | 1 21
    24: 14 1
""".trimIndent().split("\n")

val testMsgs2 = """
    abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
    bbabbbbaabaabba
    babbbbaabbbbbabbbbbbaabaaabaaa
    aaabbbbbbaaaabaababaabababbabaaabbababababaaa
    bbbbbbbaaaabbbbaaabbabaaa
    bbbababbbbaaaaaaaabbababaaababaabab
    ababaaaaaabaaab
    ababaaaaabbbaba
    baabbaaaabbaaaababbaababb
    abbbbabbbbaaaababbbbbbaaaababb
    aaaaabbaabaaaaababaa
    aaaabbaaaabbaaa
    aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
    babaaabbbaaabaababbaabababaaab
    aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
""".trimIndent().split("\n")