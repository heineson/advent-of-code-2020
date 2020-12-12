package se.heinszn.aoc2020.day12

import se.heinszn.aoc2020.Coord
import se.heinszn.aoc2020.Rotation
import se.heinszn.aoc2020.Vect
import se.heinszn.aoc2020.readFile

enum class Direction(val v: Vect) {
    NORTH(Vect(0, 1)), EAST(Vect(1, 0)), SOUTH(Vect(0, -1)), WEST(Vect(-1, 0))
}
data class Pos(val c: Coord, val d: Direction) {
    fun turnLeft(): Pos = when(d) {
        Direction.NORTH -> this.copy(d = Direction.WEST)
        Direction.EAST -> this.copy(d = Direction.NORTH)
        Direction.SOUTH -> this.copy(d = Direction.EAST)
        Direction.WEST -> this.copy(d = Direction.SOUTH)
    }

    fun turnRight(): Pos = when(d) {
        Direction.NORTH -> this.copy(d = Direction.EAST)
        Direction.EAST -> this.copy(d = Direction.SOUTH)
        Direction.SOUTH -> this.copy(d = Direction.WEST)
        Direction.WEST -> this.copy(d = Direction.NORTH)
    }
}

fun turnL(i: Int, p: Pos): Pos {
    var pos = p
    var c = 0
    while (c < i) {
        pos = pos.turnLeft()
        c++
    }
    return pos
}

fun turnR(i: Int, p: Pos): Pos {
    var pos = p
    var c = 0
    while (c < i) {
        pos = pos.turnRight()
        c++
    }
    return pos
}

val lineRegex = """^([NSEWLRF])(.*)$""".toRegex()
fun lineParser(l: String): Pair<String, Int> {
    val (s, i) = lineRegex.find(l)!!.destructured
    return Pair(s, i.toInt())
}

fun stepPart1(p: Pos, step: Pair<String, Int>): Pos {
    val value = step.second
    val turnSteps = value / 90
    return when (step.first) {
        "N" -> p.copy(c = p.c + Vect(0, value))
        "S" -> p.copy(c = p.c + Vect(0, -value))
        "E" -> p.copy(c = p.c + Vect(value, 0))
        "W" -> p.copy(c = p.c + Vect(-value, 0))
        "L" -> turnL(turnSteps, p)
        "R" -> turnR(turnSteps, p)
        "F" -> p.copy(c = p.c + (p.d.v * value))
        else -> p
    }
}

fun stepPart2(pW: Pair<Coord, Vect>, step: Pair<String, Int>): Pair<Coord, Vect> {
    val value = step.second
    val turnSteps = value / 90
    return when (step.first) {
        "N" -> pW.copy(second = pW.second + Vect(0, value))
        "S" -> pW.copy(second = pW.second + Vect(0, -value))
        "E" -> pW.copy(second = pW.second + Vect(value, 0))
        "W" -> pW.copy(second = pW.second + Vect(-value, 0))
        "L" -> pW.copy(second = pW.second.rotate90(Rotation.CCW, turnSteps))
        "R" -> pW.copy(second = pW.second.rotate90(Rotation.CW, turnSteps))
        "F" -> pW.copy(first = pW.first + (pW.second * value))
        else -> pW
    }
}

fun main() {
//    val data = testData.map { lineParser(it) }
    val data = readFile({ lineParser(it) })

    val start = Coord(0,0)

    var curr = Pos(start, Direction.EAST)
    data.forEach { curr = stepPart1(curr, it) }
    println("${start.manhattan(curr.c)}")

    var currPW = Pair(start, Vect(10, 1))
    data.forEach {
        currPW = stepPart2(currPW, it)
        println("$it: $currPW")
    }
    println("${start.manhattan(currPW.first)}")
}

val testData = """
    F10
    N3
    F7
    R90
    F11
""".trimIndent().split("\n")
