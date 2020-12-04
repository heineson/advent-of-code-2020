package se.heinszn.aoc2020

import java.io.File
import kotlin.math.abs

/****** Input data utils *******/

fun <T> readFile(lineParser: (line: String) -> T, filename: String = "./data.txt"): List<T> {
    return File(filename).readLines().map { lineParser(it) }
}

fun readFileIntoTokens(groupSeparatorLinePattern: String = "", tokenSeparator: String = " ", filename: String = "./data.txt"): List<List<String>> {
    val lines = File(filename).readLines()
    val groups = mutableListOf<List<String>>()
    var group = mutableListOf<String>()
// TODO misses last passport
    lines.forEach { s ->
        run {
            if (s == groupSeparatorLinePattern) {
                groups.add(group)
                group = mutableListOf()
            } else {
                group.addAll(s.split(tokenSeparator))
            }
        }
    }

    return groups
}

/****** 2d coord utils ********/

data class Coord(val x: Int, val y: Int)

fun Coord.manhattan(other: Coord): Int {
    return abs(this.x - other.x) + abs(this.y - other.y)
}
