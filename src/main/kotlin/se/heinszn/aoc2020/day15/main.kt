package se.heinszn.aoc2020.day15

typealias Numbers = MutableMap<Int, Pair<Int, Int>>

fun step(numbers: Numbers, turn: Int, last: Int): Pair<Numbers, Int> {
    val occurrences = numbers[last]!!
    val diff = if (occurrences.second == -1 && occurrences.first == turn - 1) 0 else occurrences.first - occurrences.second

    numbers[diff] = Pair(turn, (numbers[diff] ?: Pair(-1, -1)).first)
    return Pair(numbers, diff)
}

fun main() {
    val data = realData

    var numbers: Numbers = mutableMapOf()
    var turn = 1
    var lastNumber = 0
    for (i in data) {
        numbers[i] = Pair(turn, -1)
        lastNumber = i
        turn++
    }

    while (turn <= 30000000) {
        val (n, l) = step(numbers, turn, lastNumber)
        lastNumber = l
        numbers = n
        turn++
        if (turn % 100000 == 0) {
            println("Turn $turn/30000000, ${30000000 - turn} to go")
        }
    }

    println("Last number spoken before turn $turn: $lastNumber")
}

val testData = listOf(0, 3, 6)
val realData = listOf(9,6,0,10,18,2,1)
