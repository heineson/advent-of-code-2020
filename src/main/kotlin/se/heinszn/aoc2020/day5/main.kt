package se.heinszn.aoc2020.day5

import se.heinszn.aoc2020.readFile

fun getRow(data: String): Int {
    val rows = data.take(7)
    var range = 0..127
    rows.forEach { run {
        when (it) {
            'F' -> range = range.first..((range.last-range.first) / 2)+range.first
            'B' -> range = ((range.last-range.first) / 2)+range.first+1..range.last
        }
    } }
    return range.first
}

fun getSeat(data: String): Int {
    val seats = data.drop(7)
    var range = 0..7
    seats.forEach { run {
        when (it) {
            'L' -> range = range.first..((range.last-range.first) / 2)+range.first
            'R' -> range = ((range.last-range.first) / 2)+range.first+1..range.last
        }
    } }
    return range.first
}

fun getSeatId(row: Int, seat: Int): Int = row * 8 + seat

fun main() {
    val data: List<String> = readFile({ it })
    val max = data.map { getSeatId(getRow(it), getSeat(it)) }.max()!!
    println("max seat id: $max")

    val ids = data.map { getSeatId(getRow(it), getSeat(it)) }.sorted()
    val v = (ids[0]..ids.last()).find { !ids.contains(it) && ids.contains(it-1) && ids.contains(it+1) }
    println("My seat: $v")

}
