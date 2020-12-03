package se.heinszn.aoc2020.day3

import se.heinszn.aoc2020.readFile

fun lineParser(line: String): List<Int> {
    val list = mutableListOf<Int>()
    line.forEachIndexed { index, c -> run { if (c == '#') list += index } }
    return list
}

const val LINE_WIDTH = 31

fun isCoordTree(coord: Pair<Int, Int>, trees: List<Pair<Int, Int>>): Boolean {
    val x = coord.first % LINE_WIDTH
    return trees.contains(Pair(x, coord.second))
}

fun main() {
    val data = readFile({ line ->  lineParser(line) })
    // val data = testData.lines().map { s -> lineParser(s) }
    val lines = data.size
    val treeCoords = data.flatMapIndexed { index, list: List<Int> -> list.map { Pair(it, index) } }
    var x = 0
    var y = 0
    var trees = 0
    while (y < lines) {
        if (isCoordTree(Pair(x, y), treeCoords)) {
            trees++
        }
        x += 3
        y += 1
    }
    println("Trees: $trees")
}

val testData = """
..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#
""".trimIndent()
