package se.heinszn.aoc2020.day9

import se.heinszn.aoc2020.readFile

fun validSums(numbers: List<Long>): Set<Long> {
    val set = mutableSetOf<Long>()
    numbers.forEachIndexed { index, l -> run {
        numbers.drop(index).map { it + l }.forEach { run { set.add(it) } }
    } }
    return set
}

fun findViolatingNumber(windowSize: Int, data: List<Long>): Long {
    val mismatches = data.windowed(windowSize + 1).filter { !validSums(it.dropLast(1)).contains(it.last()) }
    return mismatches.first().last()
}

fun findWeakness(violating: Long, data: List<Long>): Long {
    val range: List<List<Long>> = data.mapIndexed { index, _ ->
        var sum: Long = 0
        data.drop(index).takeWhile { l -> sum += l; println(sum); sum <= violating }
    }.filter { it.sum() == violating && it.size > 1 }

    return (range.first().minOrNull() ?: 0) + (range.first().maxOrNull() ?: 0)
}

fun main() {
//    val data = testData.map { it.toLong() }
    val data = readFile({ it.toLong() })
//    println("${findViolatingNumber(5, data)}")
//    println("${findWeakness(findViolatingNumber(5, data), data)}")
    println("${findViolatingNumber(25, data)}")
    println("${findWeakness(findViolatingNumber(25, data), data)}")
}

val testData = """
    35
    20
    15
    25
    47
    40
    62
    55
    65
    95
    102
    117
    150
    182
    127
    219
    299
    277
    309
    576
""".trimIndent().split("\n")
