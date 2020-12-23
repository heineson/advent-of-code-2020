package se.heinszn.aoc2020.day23

typealias Cups = List<Int>
fun move(cups: Cups, currentCupIndex: Int): Pair<Cups, Int> {
    if (cups.size < 100) println("cups: ${cups.mapIndexed { index, c -> if (index == currentCupIndex) "($c)" else "$c"}.joinToString(" ")}")

    val threeCups = (cups + cups.take(4)).slice(currentCupIndex + 1..currentCupIndex + 3)
    val s = System.currentTimeMillis()
    val longList = cups + cups
    val part1 = longList.subList(0, currentCupIndex + 1)
    if (cups.size < 100) println(part1)
    val remainingCups = part1 + longList.drop(currentCupIndex + 4).take(cups.size - part1.size - threeCups.size)
    if (cups.size < 100) println(remainingCups)
//    val remainingCups = cups.filter { it !in threeCups }
//    println("Filter time : ${System.currentTimeMillis() - s}")
//    println("pick up: $threeCups")

    val currentCupLabel = cups[currentCupIndex]
    var destinationLabel = currentCupLabel - 1
    while (destinationLabel in threeCups || destinationLabel <= 0) {
        if (destinationLabel <= 0) {
            destinationLabel = cups.size
        } else {
            destinationLabel--
        }
    }
//    println("destination $destinationLabel")
    val destIndex = remainingCups.indexOf(destinationLabel)

    val newCups = remainingCups.subList(0, destIndex + 1) + threeCups + remainingCups.subList(destIndex + 1, remainingCups.size)

    return Pair(newCups, (newCups.indexOf(currentCupLabel) + 1) % cups.size)
}

fun part1() {
    var cups = testData
//    var cups = realData
    var currentCupIndex = 0
    for (i in 1..100) {
//        println("-- move $i --")
        val (cs, index) = move(cups, currentCupIndex)
        cups = cs
        currentCupIndex = index
//        println()
    }
    val indexOne = cups.indexOf(1)
    println("Part1: ${(cups + cups).subList(indexOne + 1, indexOne + cups.size).joinToString("")}")
}

fun part2() {
    var cups = testData + (10..1_000_000)
//    var cups = realData + (10..1_000_000)
    println("Cups size: ${cups.size}")
    val startingCups = cups
    var timestamp = System.currentTimeMillis()
    var currentCupIndex = 0
    for (i in 1..10_000_000) {
        val (cs, index) = move(cups, currentCupIndex)
        cups = cs
        currentCupIndex = index
        if (i % 1000 == 0) {
            println("Round $i, time ${System.currentTimeMillis() - timestamp}")
            timestamp = System.currentTimeMillis()
        }
    }
    val indexOne = cups.indexOf(1)
    val starCups = Pair(cups[(indexOne + 1) % cups.size], cups[(indexOne + 2) % cups.size])
    println("Part2: ${starCups.first * starCups.second}")
}

fun main() {
    println(testData)

    part1()
    part2()
}

val testData = "389125467".map { it.toString().toInt() }
val realData = "716892543".map { it.toString().toInt() }
