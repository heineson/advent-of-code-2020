package se.heinszn.aoc2020.day13

import kotlin.math.ceil

fun part1(arrival: Int, buses: List<Int>) {
    val multipliers = buses.map { ceil(arrival / it.toDouble()).toInt() }
    val departures = buses.zip(multipliers) { a, b -> Pair(a, a * b) }.sortedBy { it.second }

    val (id, departureTime) = departures.first()

    print("Part1: ")
    println(id * (departureTime - arrival))
}

fun part2(buses: List<String>) {
    val withIndex = buses
            .mapIndexed { index, id -> Pair(index, id) }
            .filter { it.second != "x" }
            .map { Pair(it.first, it.second.toInt()) }
    var step: Long = withIndex.first().second.toLong()
    var ts: Long = 0L
    for (pair in withIndex.drop(1)) {
        while ((ts + pair.first) % pair.second != 0L) {
            ts += step
        }
        step *= pair.second
    }
    println("Part2: $ts")
}

fun main() {
//    val arrival = testData[0].toInt()
//    val buses = testData[1].split(",").filter { it != "x" }.map { it.toInt() }
    val arrival = realData[0].toInt()
    val buses = realData[1].split(",")

    part1(arrival, buses.filter { it != "x" }.map { it.toInt() })
//    part2("""1789,37,47,1889""".split(","))
    part2(realData[1].split(","))
}

val testData = """
    939
    7,13,x,x,59,x,31,19
""".trimIndent().split("\n")

val realData = """
    1000510
    19,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,523,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,17,13,x,x,x,x,x,x,x,x,x,x,29,x,853,x,x,x,x,x,37,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,23
""".trimIndent().split("\n")
