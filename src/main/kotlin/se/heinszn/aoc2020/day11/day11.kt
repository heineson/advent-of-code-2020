package se.heinszn.aoc2020.day11

import se.heinszn.aoc2020.Coord
import se.heinszn.aoc2020.Vect
import se.heinszn.aoc2020.readFile

enum class SeatState { FLOOR, EMPTY, OCCUPIED }
data class Seat(val coord: Coord, var state: SeatState)

var lineCounter = 0
fun lineParser(line: String): List<Seat> {
    val seats = line.toList().mapIndexed { index, c ->
        when(c) {
            '.' -> Seat(Coord(index, lineCounter), SeatState.FLOOR)
            'L' -> Seat(Coord(index, lineCounter), SeatState.EMPTY)
            '#' -> Seat(Coord(index, lineCounter), SeatState.OCCUPIED)
            else -> throw IllegalArgumentException()
        }
    }
    lineCounter++
    return seats
}

fun findStableState(seats: List<Seat>): List<Seat> {
    val nextState = seats.map { mapper2(it, seats) }
    if (nextState.containsAll(seats)) {
        return seats
    }
    return findStableState(nextState)
}

fun mapper(s: Seat, data: List<Seat>): Seat {
    if (s.state == SeatState.EMPTY) {
        val ns = s.coord.surroundingNeighbors().mapNotNull { n -> data.find { d -> d.coord == n } }
        //println("$s, surrounding: $ns")
        if(ns.count { it.state == SeatState.OCCUPIED } == 0) {
            return s.copy(state = SeatState.OCCUPIED)
        }
    }
    if (s.state == SeatState.OCCUPIED) {
        val ns = s.coord.surroundingNeighbors().mapNotNull { n -> data.find { d -> d.coord == n } }
        if(ns.count { it.state == SeatState.OCCUPIED } >= 4) {
            return s.copy(state = SeatState.EMPTY)
        }
    }
    return s
}

fun mapper2(s: Seat, data: List<Seat>): Seat {
    if (s.state == SeatState.EMPTY) {
        val ns = getVisible(s, data)
        //println("$s, surrounding: $ns")
        if(ns.count { it.state == SeatState.OCCUPIED } == 0) {
            return s.copy(state = SeatState.OCCUPIED)
        }
    }
    if (s.state == SeatState.OCCUPIED) {
        val ns = getVisible(s, data)
        if(ns.count { it.state == SeatState.OCCUPIED } >= 5) {
            return s.copy(state = SeatState.EMPTY)
        }
    }
    return s
}

fun getVisible(s: Seat, data: List<Seat>): List<Seat> {
    val ns = s.coord.surroundingNeighbors().mapNotNull { n -> data.find { d -> d.coord == n } }
    val result = mutableListOf<Seat>()
    result.addAll(ns.filter { it.state != SeatState.FLOOR })

    val floors = ns.filter { it.state == SeatState.FLOOR }
    floors.forEach { f -> run {
        val d = Vect(f.coord.x - s.coord.x, f.coord.y - s.coord.y)
        val whileCondition = fun(c: Coord): Boolean {
            val v = data.find { it.coord == c }
            return v != null && v.state == SeatState.FLOOR
        }
        //println(f.coord.inDirectionWhile(d) { whileCondition(it) } + d)
        val res = data.find { seat -> ((f.coord.inDirectionWhile(d) { whileCondition(it) }).lastOrNull() ?: f.coord) + d == seat.coord }
        if (res != null) result.add(res)
    } }

    return result
}

fun main() {
    lineCounter = 0
//    val data = testData.flatMap { lineParser(it) }
     val data = readFile({ lineParser(it) }).flatten()
    //println(data)

    println("Occupied: ${findStableState(data).count { it.state == SeatState.OCCUPIED }}")
}

val testData = """
    L.LL.LL.LL
    LLLLLLL.LL
    L.L.L..L..
    LLLL.LL.LL
    L.LL.LL.LL
    L.LLLLL.LL
    ..L.L.....
    LLLLLLLLLL
    L.LLLLLL.L
    L.LLLLL.LL
""".trimIndent().split("\n")
