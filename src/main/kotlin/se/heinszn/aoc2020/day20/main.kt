package se.heinszn.aoc2020.day20

import se.heinszn.aoc2020.Coord
import se.heinszn.aoc2020.Grid2d
import se.heinszn.aoc2020.readFileIntoTokens
import se.heinszn.aoc2020.readLinesIntoTokens
import kotlin.math.sqrt

data class Tile(val id: Int, val data: Grid2d<Int>) {
    override fun toString(): String {
        return "Id: $id\n$data\n"
    }
}

fun parseTile(ls: List<String>): Tile {
    val id = ls.drop(1).first().trim().removeSuffix(":").toInt()

    var tile: Grid2d<Int> = Grid2d()
    ls.drop(2).forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            tile[Coord(x, y)] = if (c == '#') 1 else 0
        }
    }
    return Tile(id, tile)
}

fun findMatchingTileIds(tile: Tile, tiles: List<Tile>): List<List<Int>> {
    val sides = tile.data.getSides().map { l -> l.map { it.second } }
    val others = tiles.filter { it.id != tile.id }

    return sides.map { side: List<Int> ->
        others.filter { t: Tile ->
            val oSides = t.data.getSides().map { l -> l.map { it.second } }
            oSides.any { it == side || it == side.reversed() }
        }.map { it.id }
    }
}

fun findCornerTileIds(sides: List<Pair<Int, List<List<Int>>>>): List<Int> {
    return sides.filter { it.second.filter { l -> l.isNotEmpty() }.size == 2 }.map { it.first }
}

fun main() {
    // val tiles = readLinesIntoTokens(testData).map { parseTile(it) }
    val tiles = readFileIntoTokens().map { parseTile(it) }
    val gridSize = sqrt(tiles.size.toDouble()).toInt()

    println(tiles)
    println(gridSize)
    val v = tiles.map { Pair(it.id, findMatchingTileIds(it, tiles)) }
    val corners = findCornerTileIds(v)
    println("Part1 : ${corners.fold(1L) { acc, i -> acc * i } }")
}

val testData = """
    Tile 2311:
    ..##.#..#.
    ##..#.....
    #...##..#.
    ####.#...#
    ##.##.###.
    ##...#.###
    .#.#.#..##
    ..#....#..
    ###...#.#.
    ..###..###

    Tile 1951:
    #.##...##.
    #.####...#
    .....#..##
    #...######
    .##.#....#
    .###.#####
    ###.##.##.
    .###....#.
    ..#.#..#.#
    #...##.#..

    Tile 1171:
    ####...##.
    #..##.#..#
    ##.#..#.#.
    .###.####.
    ..###.####
    .##....##.
    .#...####.
    #.##.####.
    ####..#...
    .....##...

    Tile 1427:
    ###.##.#..
    .#..#.##..
    .#.##.#..#
    #.#.#.##.#
    ....#...##
    ...##..##.
    ...#.#####
    .#.####.#.
    ..#..###.#
    ..##.#..#.

    Tile 1489:
    ##.#.#....
    ..##...#..
    .##..##...
    ..#...#...
    #####...#.
    #..#.#.#.#
    ...#.#.#..
    ##.#...##.
    ..##.##.##
    ###.##.#..

    Tile 2473:
    #....####.
    #..#.##...
    #.##..#...
    ######.#.#
    .#...#.#.#
    .#########
    .###.#..#.
    ########.#
    ##...##.#.
    ..###.#.#.

    Tile 2971:
    ..#.#....#
    #...###...
    #.#.###...
    ##.##..#..
    .#####..##
    .#..####.#
    #..#.#..#.
    ..####.###
    ..#.#.###.
    ...#.#.#.#

    Tile 2729:
    ...#.#.#.#
    ####.#....
    ..#.#.....
    ....#..#.#
    .##..##.#.
    .#.####...
    ####.#.#..
    ##.####...
    ##..#.##..
    #.##...##.

    Tile 3079:
    #.#.#####.
    .#..######
    ..#.......
    ######....
    ####.#..#.
    .#...#.##.
    #.#####.##
    ..#.###...
    ..#.......
    ..#.###...
""".trimIndent().split("\n")