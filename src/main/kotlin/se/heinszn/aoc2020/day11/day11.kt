package se.heinszn.aoc2020.day11

import se.heinszn.aoc2020.Coord
import se.heinszn.aoc2020.Grid2d
import se.heinszn.aoc2020.Vect
import se.heinszn.aoc2020.readFile

enum class SeatState { FLOOR, EMPTY, OCCUPIED }
data class Seat(val coord: Coord, var state: SeatState)

class Grid : Grid2d<SeatState>() {
    override fun printElement(e: SeatState): Char {
        return when (e) {
            SeatState.FLOOR -> '.'
            SeatState.OCCUPIED -> '#'
            SeatState.EMPTY -> 'L'
        }
    }
}

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

fun findStableState(seats: Grid): Grid {
    val nextState = seats.getEntries().map { mapper2(Seat(it.key, it.value), seats) }
    val nextGrid = Grid()
    nextState.forEach { seat -> nextGrid[seat.coord] = seat.state }
    if (nextGrid == seats) {
        return seats
    }
    return findStableState(nextGrid)
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

fun mapper2(s: Seat, grid: Grid): Seat {
    if (s.state == SeatState.EMPTY) {
        val ns = getVisibleFrom(s.coord, grid)
        if(ns.count { it.state == SeatState.OCCUPIED } == 0) {
            return s.copy(state = SeatState.OCCUPIED)
        }
    }
    if (s.state == SeatState.OCCUPIED) {
        val ns = getVisibleFrom(s.coord, grid)
        if(ns.count { it.state == SeatState.OCCUPIED } >= 5) {
            return s.copy(state = SeatState.EMPTY)
        }
    }
    return s
}

fun getVisibleFrom(coord: Coord, grid: Grid): Set<Seat> {
    val ns = coord.surroundingNeighbors().filter { grid[it] != null }.map { n -> Seat(n, grid.getValue(n)) }
    val result = mutableSetOf<Seat>()
    result.addAll(ns.filter { it.state != SeatState.FLOOR })

    val floors = ns.filter { it.state == SeatState.FLOOR }.map { it.coord }
    floors.forEach { f ->
        val d = Vect(f.x - coord.x, f.y - coord.y)
        val whileCondition = fun(c: Coord): Boolean {
            val seatState = grid[c]
            return seatState != null && seatState == SeatState.FLOOR
        }
        val target = ((f.inDirectionWhile(d) { whileCondition(it) }).lastOrNull() ?: f) + d
        grid[target]?.let { result.add(Seat(target, it)) }
    }

    return result
}

fun main() {
    lineCounter = 0
    val grid = Grid()
//    val data = testData.flatMap { lineParser(it) }
     val data = readFile({ lineParser(it) }).flatten()
     data.forEach { grid[it.coord] = it.state }
    println(grid)

    println("Occupied: ${findStableState(grid).getEntries().count { it.value == SeatState.OCCUPIED }}")
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
