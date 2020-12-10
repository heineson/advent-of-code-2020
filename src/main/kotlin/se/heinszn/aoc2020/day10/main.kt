package se.heinszn.aoc2020.day10

import se.heinszn.aoc2020.readFile

data class Adapter(val rating: Int) {
    fun canConnectTo(other: Adapter) = other.rating in rating-3 until rating
}

fun getAllValidFor(a: Adapter, data: List<Adapter>): List<Adapter> = data.filter { it.canConnectTo(a) }

fun getDeviceRating(data: List<Adapter>): Int = 3 + (data.map { it.rating }.maxOrNull() ?: 0)

fun main() {
//    val data = testData.map { Adapter(it.toInt()) }
//    val data = testData2.map { Adapter(it.toInt()) }
    val data = readFile({ Adapter(it.toInt()) })

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

    println("1: $jumps1, 3: $jumps3")
    println("part1: ${jumps1 * jumps3}")
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
