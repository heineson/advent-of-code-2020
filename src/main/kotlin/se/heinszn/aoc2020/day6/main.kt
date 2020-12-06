package se.heinszn.aoc2020.day6

import se.heinszn.aoc2020.readFileIntoTokens

fun countGroupPart1(g: List<String>): Int {
    return g.reduce { acc, s -> acc + s }.toSet().size
}

fun countGroupPart2(g: List<String>): Int {
    val allAnswers =  g.reduce { acc, s -> acc + s }.toCharArray().toSet()
    val intersection = g.map { it.toList() }.fold(allAnswers) { acc, cs -> acc.intersect(cs) }
    return intersection.size
}

fun main() {
    //val data = readLinesIntoTokens(testData.split("\n"))
    val data = readFileIntoTokens(filename = "./data.txt")
    val count = data.map { countGroupPart1(it) }.sum()
    val count2 = data.map { countGroupPart2(it) }.sum()
    println("Count is: $count")
    println("Count part2 is: $count2")
}

val testData = """
    abc

    a
    b
    c

    ab
    ac

    a
    a
    a
    a

    b
""".trimIndent()