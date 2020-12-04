package se.heinszn.aoc2020

import java.io.File
import kotlin.math.abs
import kotlin.math.sqrt

/****** Input data utils *******/

fun <T> parseLines(lineParser: (line: String) -> T, lines: List<String>): List<T> {
    return lines.map { lineParser(it) }
}

fun <T> readFile(lineParser: (line: String) -> T, filename: String = "./data.txt"): List<T> {
    return parseLines(lineParser, File(filename).readLines())
}

fun readLinesIntoTokens(lines: List<String>, groupSeparatorLinePattern: String = "", tokenSeparator: String = " "): List<List<String>> {
    val groups = mutableListOf<List<String>>()
    var group = mutableListOf<String>()
    lines.forEach { s ->
        run {
            if (s == groupSeparatorLinePattern) {
                groups.add(group)
                group = mutableListOf()
            } else {
                group.addAll(s.split(tokenSeparator))
                // In case final line is not a "separator line"
                if (s == lines.last()) {
                    groups.add(group)
                }
            }
        }
    }

    return groups
}

fun readFileIntoTokens(groupSeparatorLinePattern: String = "", tokenSeparator: String = " ", filename: String = "./data.txt"): List<List<String>> {
    return readLinesIntoTokens(File(filename).readLines(), groupSeparatorLinePattern, tokenSeparator)
}

/****** 2d map utils ********/

class Coord(val x: Int, val y: Int) {
    operator fun plus(v: Vect): Coord = Coord(this.x + v.dx, this.y + v.dy)

    fun manhattan(other: Coord): Int {
        return abs(this.x - other.x) + abs(this.y - other.y)
    }
}

class Vect(val dx: Int, val dy: Int) {
    operator fun plus(other: Vect): Vect = Vect(this.dx + other.dx, this.dy + other.dy)
    fun length(): Double = sqrt(this.dx.toDouble()*this.dx + this.dy*this.dy)
}
