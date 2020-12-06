package se.heinszn.aoc2020

import java.io.File
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt

/****** Input data utils *******/

fun <T> parseLines(lineParser: (line: String) -> T, lines: List<String>): List<T> {
    return lines.map { lineParser(it) }
}

fun <T> readFile(lineParser: (line: String) -> T, filename: String = "./data.txt"): List<T> {
    return parseLines(lineParser, File(filename).readLines())
}

fun readLinesIntoTokens(lines: List<String>, groupSeparatorLine: String = "", tokenSeparator: String = " "): List<List<String>> {
    val groups = mutableListOf<List<String>>()
    var group = mutableListOf<String>()
    lines.forEachIndexed { i, s ->
        run {
            if (s == groupSeparatorLine) {
                groups.add(group)
                group = mutableListOf()
            } else {
                group.addAll(s.split(tokenSeparator))
                // In case final line is not a "separator line"
                if (i == lines.size - 1) {
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

fun toDegrees(rad: Double): Double = (rad * 180) / PI
fun toRadians(deg: Double): Double = (deg * PI) / 180

data class Coord(val x: Int, val y: Int) {
    operator fun plus(v: Vect): Coord = Coord(x + v.dx, y + v.dy)

    fun up(): Coord = Coord(x, y + 1)
    fun right(): Coord = Coord(x + 1, y)
    fun down(): Coord = Coord(x, y - 1)
    fun left(): Coord = Coord(x - 1, y)

    fun manhattan(other: Coord): Int {
        return abs(x - other.x) + abs(y - other.y)
    }

    fun cardinalNeighbors(): List<Coord> = listOf(up(), left(), down(), right())
    fun surroundingNeighbors(): List<Coord> = listOf(
        up(),
        Coord(x + 1, y + 1),
        right(),
        Coord(x + 1, y - 1),
        down(),
        Coord(x - 1, y - 1),
        left(),
        Coord(x - 1, y + 1)
    )

    override fun toString(): String {
        return "($x, $y)"
    }
}

data class Vect(val dx: Int, val dy: Int) {
    operator fun plus(other: Vect): Vect = Vect(this.dx + other.dx, this.dy + other.dy)
    fun length(): Double = sqrt(this.dx.toDouble()*this.dx + this.dy*this.dy)
}
