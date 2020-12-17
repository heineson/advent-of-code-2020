package se.heinszn.aoc2020.day17

import se.heinszn.aoc2020.Coord3
import se.heinszn.aoc2020.Coord4

typealias Grid3 = MutableMap<Coord3, Int>
fun getActiveSurrounding3(c: Coord3, g: Grid3): List<Coord3> {
    return c.surroundingNeighbors().filter { g[it] == 1 }
}
fun printGrid(g: Grid3) {
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

typealias Grid4 = MutableMap<Coord4, Int>
fun getActiveSurrounding4(c: Coord4, g: Grid4): List<Coord4> {
    return c.surroundingNeighbors().filter { g[it] == 1 }
}

fun cycle3(state: Grid3): Grid3 {
    val xs = state.keys.map { it.x }
    val ys = state.keys.map { it.y }
    val zs = state.keys.map { it.z }
    val xr: IntRange = (xs.minOrNull() ?: 0)-1..(xs.maxOrNull() ?: 0)+1
    val yr: IntRange = (ys.minOrNull() ?: 0)-1..(ys.maxOrNull() ?: 0)+1
    val zr: IntRange = (zs.minOrNull() ?: 0)-1..(zs.maxOrNull() ?: 0)+1

    val newState: Grid3 = mutableMapOf()

    for (xi in xr) {
        for (yi in yr) {
            for (zi in zr) {
                val c = Coord3(xi, yi, zi)
//                println("$c ${getActiveSurrounding3(c, state)}")
                if (state[c] == 1) {
                    if (getActiveSurrounding3(c, state).size !in 2..3) {
                        newState[c] = 0
                    } else {
                        newState[c] = 1
                    }

                } else {
                    if (getActiveSurrounding3(c, state).size == 3) {
                        newState[c] = 1
                    } else {
                        newState[c] = 0
                    }
                }
            }
        }
    }

//    printGrid(newState)
    return newState
}

fun cycle4(state: Grid4): Grid4 {
    val xs = state.keys.map { it.x }
    val ys = state.keys.map { it.y }
    val zs = state.keys.map { it.z }
    val ws = state.keys.map { it.w }
    val xr: IntRange = (xs.minOrNull() ?: 0)-1..(xs.maxOrNull() ?: 0)+1
    val yr: IntRange = (ys.minOrNull() ?: 0)-1..(ys.maxOrNull() ?: 0)+1
    val zr: IntRange = (zs.minOrNull() ?: 0)-1..(zs.maxOrNull() ?: 0)+1
    val wr: IntRange = (ws.minOrNull() ?: 0)-1..(ws.maxOrNull() ?: 0)+1

    val newState: Grid4 = mutableMapOf()

    for (xi in xr) {
        for (yi in yr) {
            for (zi in zr) {
                for (wi in wr) {
                    val c = Coord4(xi, yi, zi, wi)
                    if (state[c] == 1) {
                        if (getActiveSurrounding4(c, state).size !in 2..3) {
                            newState[c] = 0
                        } else {
                            newState[c] = 1
                        }

                    } else {
                        if (getActiveSurrounding4(c, state).size == 3) {
                            newState[c] = 1
                        } else {
                            newState[c] = 0
                        }
                    }
                }
            }
        }
    }

    return newState
}

fun loadGrid3(data: List<String>): Grid3 {
    val g: Grid3 = mutableMapOf()
    data.forEachIndexed { y, l -> l.forEachIndexed { x, c ->
        g[Coord3(x, y, 0)] = if (c == '#') 1 else 0
    } }
    return g
}

fun loadGrid4(data: List<String>): Grid4 {
    val g: Grid4 = mutableMapOf()
    data.forEachIndexed { y, l -> l.forEachIndexed { x, c ->
        g[Coord4(x, y, 0, 0)] = if (c == '#') 1 else 0
    } }
    return g
}

fun phase1(data: List<String>) {
    var state = loadGrid3(data)
    printGrid(state)
    state = cycle3(state)
    state = cycle3(state)
    state = cycle3(state)
    state = cycle3(state)
    state = cycle3(state)
    state = cycle3(state)
//    printGrid(state)
    println("Part 1: ${state.values.count { it == 1 }}") // 276
}

fun phase2(data: List<String>) {
    var state = loadGrid4(data)
    state = cycle4(state)
    state = cycle4(state)
    state = cycle4(state)
    state = cycle4(state)
    state = cycle4(state)
    state = cycle4(state)
    println("Part 2: ${state.values.count { it == 1 }}") // 2136
}

fun main() {
    phase1(realData)
    phase2(realData)
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
