package se.heinszn.aoc2020.day10

import se.heinszn.aoc2020.readFile

data class Adapter(val rating: Int) {
    fun canConnectTo(other: Adapter) = other.rating in rating-3 until rating
}

fun getAllValidFor(a: Adapter, data: List<Adapter>): List<Adapter> = data.filter { it.canConnectTo(a) }

fun useAll(data: List<Adapter>): Pair<Int, Int> {
    val maxRating = data.map { it.rating }.maxOrNull() ?: 0
    var current = Adapter(0)
    var jumps1 = 0
    var jumps3 = 0

    while (current.rating < maxRating) {
        val new = getAllValidFor(current, data).minByOrNull { it.rating - current.rating }!!
        when (new.rating - current.rating) {
            1 -> jumps1++
            3 -> jumps3++
        }
        current = new
    }
    jumps3++ // for the device, always 3

    return Pair(jumps1, jumps3)
}

// TODO never stops counting, ditch this!
fun findAllCombinations(data: List<Adapter>): Long {
    val curr = Adapter(0)
    var paths: Long = 1

    return countPaths(curr, paths, data)
}

fun countPaths(current: Adapter, paths: Long, data: List<Adapter>): Long {
    val allNew = getAllValidFor(current, data)
    if (allNew.isNotEmpty()) {
        val newPaths = paths + allNew.size - 1
        return newPaths + allNew.map { countPaths(it, 0, data) }.sum()
    }
    return paths
}

fun findAllCombinations2(data: List<Adapter>): Long? {
    val sorted = data.sortedBy { it.rating }.map { it.rating }
    val allSorted = sorted + (sorted[sorted.size - 1] + 3)

    val counter = mutableMapOf<Int, Long>()
    counter[0] = 1

    for (jolt in allSorted) {
        counter[jolt] = (counter[jolt - 1] ?: 0) + (counter[jolt - 2] ?: 0) + (counter[jolt - 3] ?: 0)
    }

    return counter[allSorted[allSorted.size - 1]]
}

fun main() {
//    val data = testData.map { Adapter(it.toInt()) }
//    val data = testData2.map { Adapter(it.toInt()) }
    val data = readFile({ Adapter(it.toInt()) })

    val (jumps1, jumps3) = useAll(data)
    println("1: $jumps1, 3: $jumps3")
    println("part1: ${jumps1 * jumps3}")

    println("Combinations: ${findAllCombinations2(data)}")
}

val testData = """
    16
    10
    15
    5
    1
    11
    7
    19
    6
    12
    4
""".trimIndent().split("\n")

val testData2 = """
    28
    33
    18
    42
    31
    14
    46
    20
    48
    47
    24
    23
    49
    45
    19
    38
    39
    11
    1
    32
    25
    35
    8
    17
    7
    9
    4
    2
    34
    10
    3
""".trimIndent().split("\n")
