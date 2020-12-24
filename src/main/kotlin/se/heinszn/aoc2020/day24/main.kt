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

fun isBlack(value: Int?) = (value ?: 0) % 2 == 1

fun part1(data: List<List<String>>): MutableMap<HexCoord, Int> {
    val flipped = mutableMapOf<HexCoord, Int>()
    data.forEach {
        val t = getTarget(HexCoord(0, 0), it)
        flipped[t] = flipped.getOrDefault(t, 0) + 1
    }
    val black = flipped.values.filter { isBlack(it) }.count()
    println("Part1: $black\n")
    return flipped
}

fun turn(flipped: Map<HexCoord, Int>): Map<HexCoord, Int> {
    val all = mutableMapOf<HexCoord, Int>()
    flipped.forEach { entry ->
        all[entry.key] = flipped[entry.key] ?: 0
        val ns = entry.key.surroundingNeighbors()
        ns.forEach { all[it] = flipped.getOrDefault(it, 0) }
    }

    val res = mutableMapOf<HexCoord, Int>()
    all.forEach { hc ->
        val blackNeighbors = hc.key.surroundingNeighbors().filter { isBlack(all[it]) }.count()
        if (isBlack(hc.value) && (blackNeighbors == 0 || blackNeighbors > 2)) {
            res[hc.key] = hc.value + 1
        } else if (!isBlack(hc.value) && blackNeighbors == 2) {
            res[hc.key] = hc.value + 1
        } else res[hc.key] = hc.value
    }
    return res
}

fun part2(flipped: Map<HexCoord, Int>) {
    var current = flipped
    for (i in 1..100) {
        current = turn(current)
        println("Day $i: ${current.values.filter { isBlack(it) }.count()}")
    }
}

fun main() {
    //val data = testData1.map { parseLine(it) }
    val data = readFile({ parseLine(it) })
    val flipped = part1(data)
    part2(flipped)
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