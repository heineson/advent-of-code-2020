package se.heinszn.aoc2020.day23

typealias Cups = List<Int>
fun move(cups: Cups, currentCupIndex: Int): Pair<Cups, Int> {
    println("cups: ${cups.mapIndexed { index, c -> if (index == currentCupIndex) "($c)" else "$c"}.joinToString(" ")}")

    val threeCups = (cups + cups).drop(currentCupIndex + 1).take(3)
    val remainingCups = cups.filter { it !in threeCups }
    println("pick up: $threeCups")

    val currentCupLabel = cups[currentCupIndex]
    var destinationLabel = currentCupLabel - 1
    while (destinationLabel in threeCups || destinationLabel <= 0) {
        if (destinationLabel <= 0) {
            destinationLabel = cups.maxOrNull() ?: error("No max value")
        } else {
            destinationLabel--
        }
    }
    println("destination $destinationLabel")
    val destIndex = remainingCups.indexOf(destinationLabel)

    val newCups = remainingCups.subList(0, destIndex + 1) + threeCups + remainingCups.subList(destIndex + 1, remainingCups.size)

    return Pair(newCups, (newCups.indexOf(currentCupLabel) + 1) % cups.size)
}

fun main() {
    println(testData)

//    var cups = testData
    var cups = realData
    var currentCupIndex = 0
    for (i in 1..100) {
        println("-- move $i --")
        val (cs, index) = move(cups, currentCupIndex)
        cups = cs
        currentCupIndex = index
        println()
    }
    val indexOne = cups.indexOf(1)
    println("Part1: ${(cups + cups).subList(indexOne + 1, indexOne + cups.size).joinToString("")}")
}

val testData = "389125467".map { it.toString().toInt() }
val realData = "716892543".map { it.toString().toInt() }
