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

fun treesInSlope(dx: Int, dy: Int, lines: Int, treeCoords: List<Pair<Int, Int>>): Int {
    var x = 0
    var y = 0
    var trees = 0
    while (y < lines) {
        if (isCoordTree(Pair(x, y), treeCoords)) {
            trees++
        }
        x += dx
        y += dy
    }
    return trees
}

fun main() {
    val data = readFile({ line ->  lineParser(line) })
    // val data = testData.lines().map { s -> lineParser(s) }
    val lines = data.size
    val treeCoords = data.flatMapIndexed { index, list: List<Int> -> list.map { Pair(it, index) } }
    val r1 = treesInSlope(1, 1, lines, treeCoords).toLong()
    val r2 = treesInSlope(3, 1, lines, treeCoords).toLong()
    val r3 = treesInSlope(5, 1, lines, treeCoords).toLong()
    val r4 = treesInSlope(7, 1, lines, treeCoords).toLong()
    val r5 = treesInSlope(1, 2, lines, treeCoords).toLong()
    val result: Long = r1 * r2 * r3 * r4 * r5
    println("S1: $r1")
    println("S2: $r2")
    println("S3: $r3")
    println("S4: $r4")
    println("S5: $r5")
    println("Trees: $result")
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
