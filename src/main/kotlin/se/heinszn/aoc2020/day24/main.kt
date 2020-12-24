package se.heinszn.aoc2020.day24

import se.heinszn.aoc2020.HexCoord
import se.heinszn.aoc2020.readFile

val lineRegex = """(e|se|sw|w|nw|ne)+?""".toRegex()
fun parseLine(line: String): List<String> {
    return lineRegex.findAll(line).map { it.groupValues[1] }.toList()
}

fun getTarget(start: HexCoord, path: List<String>): HexCoord {
    var pos = start.copy()
    path.forEach {
        pos = when (it) {
            "e" -> pos.e()
            "se" -> pos.se()
            "sw" -> pos.sw()
            "w" -> pos.w()
            "nw" -> pos.nw()
            "ne" -> pos.ne()
            else -> error("No such direction: $it")
        }
    }
    return pos
}

fun part1(data: List<List<String>>) {
    val flipped = mutableMapOf<HexCoord, Int>()
    data.forEach {
        val t = getTarget(HexCoord(0, 0), it)
        flipped[t] = flipped.getOrDefault(t, 0) + 1
    }
    val black = flipped.values.filter { it % 2 == 1 }.count()
    println("Part1: $black")
}

fun main() {
    // val data = testData1.map { parseLine(it) }
    val data = readFile({ parseLine(it) })
    part1(data)
}

val testData1 = """
    sesenwnenenewseeswwswswwnenewsewsw
    neeenesenwnwwswnenewnwwsewnenwseswesw
    seswneswswsenwwnwse
    nwnwneseeswswnenewneswwnewseswneseene
    swweswneswnenwsewnwneneseenw
    eesenwseswswnenwswnwnwsewwnwsene
    sewnenenenesenwsewnenwwwse
    wenwwweseeeweswwwnwwe
    wsweesenenewnwwnwsenewsenwwsesesenwne
    neeswseenwwswnwswswnw
    nenwswwsewswnenenewsenwsenwnesesenew
    enewnwewneswsewnwswenweswnenwsenwsw
    sweneswneswneneenwnewenewwneswswnese
    swwesenesewenwneswnwwneseswwne
    enesenwswwswneneswsenwnewswseenwsese
    wnwnesenesenenwwnenwsewesewsesesew
    nenewswnwewswnenesenwnesewesw
    eneswnwswnwsenenwnwnwwseeswneewsenese
    neswnwewnwnwseenwseesewsenwsweewe
    wseweeenwnesenwwwswnew
""".trimIndent().split("\n")