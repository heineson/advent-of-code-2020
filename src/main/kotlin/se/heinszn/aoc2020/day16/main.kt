package se.heinszn.aoc2020.day16

import se.heinszn.aoc2020.readFile

typealias Note = Triple<String, IntRange, IntRange>

fun parseTicket(line: String): List<Int> = line.split(",").map { it.toInt() }

val noteRegex = """^(.+): (\d+)-(\d+) or (\d+)-(\d+)$""".toRegex()
fun parseNote(line: String): Note {
    val (note, r1s, r1e, r2s, r2e) = noteRegex.find(line)!!.destructured
    return Triple(note, r1s.toInt()..r1e.toInt(), r2s.toInt()..r2e.toInt())
}

fun part1(notes: List<Note>, nearby: List<List<Int>>): List<List<Int>> {
    val ranges = notes.fold(listOf<IntRange>()) { acc, n: Note -> acc + listOf(n.second, n.third) }
    val invalid = nearby.flatten().filter { n -> ranges.all { r -> n !in r } }
    println("Error rate: ${invalid.sum()}")
    return nearby.filter { it.none { n -> invalid.contains(n) } }
}

fun part2(notes: List<Note>, myTicket: List<Int>, nearby: List<List<Int>>) {
    val posToNote = mutableMapOf<Int, Note>()
    val remainingI = myTicket.indices.toMutableList()
    val remainingN = notes.toMutableList();

    while (remainingI.size > 0) {
        for (i in remainingI.toList()) {
            val allValues = nearby.map { it[i] }
            val n = findNote(allValues, remainingN)
            if (n != null) {
                posToNote[i] = n
                remainingI.remove(i)
                remainingN.remove(n)
                println("Found $i = $n, remaining: $remainingI")
            }
        }
    }

    val depIndices = posToNote.filter { it.value.first.startsWith("departure") }.map { it.key }
    println("Part2: ${depIndices.map { myTicket[it] }.fold(1L) { acc, i -> acc * i }}")
}

fun findNote(allValues: List<Int>, notes: List<Note>): Note? {
    val notesForValue = mutableListOf<Note>()
    notes.forEach { n ->
        if (matchesNote(allValues, n)) {
            notesForValue.add(n)
        }
    }
    return if (notesForValue.size == 1) notesForValue[0] else null
}
fun matchesNote(numbers: List<Int>, note: Note): Boolean = numbers.all { it in note.second || it in note.third }

fun main() {
//    val notes = testNotes.map { parseNote(it) }
//    val nearby = testNearby.map { parseTicket(it) }
    val notes = realNotes.map { parseNote(it) }
    val nearby = readFile({ parseTicket(it) }, "./nearby.txt")
    val myTicket = realTicket.split(",").map { it.toInt() }

    val valid = part1(notes, nearby)
    part2(notes, myTicket, valid)
}

val testNotes = """
    class: 1-3 or 5-7
    row: 6-11 or 33-44
    seat: 13-40 or 45-50
""".trimIndent().split("\n")
val testTicket = "7,1,14"
val testNearby = """
    7,3,47
    40,4,50
    55,2,20
    38,6,12
""".trimIndent().split("\n")

val realNotes = """
    departure location: 32-615 or 626-955
    departure station: 47-439 or 454-961
    departure platform: 31-98 or 119-969
    departure track: 45-746 or 763-967
    departure date: 49-723 or 736-954
    departure time: 42-556 or 581-962
    arrival location: 46-401 or 418-964
    arrival station: 39-281 or 295-974
    arrival platform: 43-80 or 99-950
    arrival track: 28-670 or 682-959
    class: 43-504 or 520-957
    duration: 31-358 or 365-959
    price: 41-626 or 650-956
    route: 26-488 or 495-949
    row: 46-913 or 931-965
    seat: 40-223 or 249-958
    train: 32-832 or 853-966
    type: 36-776 or 798-960
    wagon: 38-122 or 134-969
    zone: 27-870 or 885-952
""".trimIndent().split("\n")
val realTicket = "191,61,149,157,79,197,67,139,59,71,163,53,73,137,167,173,193,151,181,179"
