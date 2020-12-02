package se.heinszn.aoc2020.day2

import se.heinszn.aoc2020.readFile

data class Policy(val letter: String, val min: Int, val max: Int)
val lineRegex = """(\d+)-(\d+) (.): (.*)""".toRegex();
fun lineParser(line: String): Pair<Policy, String> {
    val (min, max, letter, password) = lineRegex.find(line)!!.destructured;
    return Pair(Policy(letter, min.toInt(), max.toInt()), password)
}

fun validate(policy: Policy, pwd: String): Boolean {
    val count = pwd.count { it.toString() == policy.letter }
    if (count >= policy.min && count <= policy.max) {
        return true
    }
    return false
}

fun main() {
    val data = readFile({ lineParser(it) })
    val valid = data.map { validate(it.first, it.second) }.count { it }
    println("Valid pwds: $valid")
}

val testData = listOf(
        "1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc"
)
