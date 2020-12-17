package se.heinszn.aoc2020.day17

import se.heinszn.aoc2020.Coord3

typealias Grid = MutableMap<Coord3, Int>
fun getActiveSurrounding(c: Coord3, g: Grid): List<Coord3> {
    return c.surroundingNeighbors().filter { g[it] == 1 }
}
fun printGrid(g: Grid) {
    val xs = g.keys.map { it.x }
    val ys = g.keys.map { it.y }
    val zs = g.keys.map { it.z }
    val xr: IntRange = (xs.minOrNull() ?: 0)..(xs.maxOrNull() ?: 0)
    val yr: IntRange = (ys.minOrNull() ?: 0)..(ys.maxOrNull() ?: 0)
    val zr: IntRange = (zs.minOrNull() ?: 0)..(zs.maxOrNull() ?: 0)

    for (zi in zr) {
        println("z=$zi")
        for (yi in yr) {
            for (xi in xr) {
                print(if (g[Coord3(xi, yi, zi)] == 1) "#" else ".")
            }
            println()
        }
    }
    println()
}

fun cycle(state: Grid): Grid {
    val xs = state.keys.map { it.x }
    val ys = state.keys.map { it.y }
    val zs = state.keys.map { it.z }
    val xr: IntRange = (xs.minOrNull() ?: 0)-1..(xs.maxOrNull() ?: 0)+1
    val yr: IntRange = (ys.minOrNull() ?: 0)-1..(ys.maxOrNull() ?: 0)+1
    val zr: IntRange = (zs.minOrNull() ?: 0)-1..(zs.maxOrNull() ?: 0)+1

    var newState: Grid = mutableMapOf()

    for (xi in xr) {
        for (yi in yr) {
            for (zi in zr) {
                val c = Coord3(xi, yi, zi)
                println("$c ${getActiveSurrounding(c, state)}")
                if (state[c] == 1) {
                    if (getActiveSurrounding(c, state).size !in 2..3) {
                        newState[c] = 0
                    } else {
                        newState[c] = 1
                    }

                } else {
                    if (getActiveSurrounding(c, state).size == 3) {
                        newState[c] = 1
                    } else {
                        newState[c] = 0
                    }
                }
            }
        }
    }

    printGrid(newState)
    return newState
}

fun loadGrid(data: List<String>): Grid {
    val g: Grid = mutableMapOf()
    data.forEachIndexed { y, l -> l.forEachIndexed { x, c ->
        g[Coord3(x, y, 0)] = if (c == '#') 1 else 0
    } }
    return g
}

fun phase1(data: List<String>) {
    var state = loadGrid(data)
    printGrid(state)
    state = cycle(state)
    state = cycle(state)
    state = cycle(state)
    state = cycle(state)
    state = cycle(state)
    state = cycle(state)
    printGrid(state)
    println("Part 1: ${state.values.count { it == 1 }}")
}

fun main() {
    phase1(realData)
}

val testData = """
    .#.
    ..#
    ###
""".trimIndent().split("\n")

val realData = """
    .#.####.
    .#...##.
    ..###.##
    #..#.#.#
    #..#....
    #.####..
    ##.##..#
    #.#.#..#
""".trimIndent().split("\n")
