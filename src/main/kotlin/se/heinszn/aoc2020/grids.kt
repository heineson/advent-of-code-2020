package se.heinszn.aoc2020

class Grid2d<T> {
    private val data = mutableMapOf<Coord, T>()

    operator fun set(c: Coord, d: T) {
        data[c] = d
    }

    operator fun get(c: Coord): T? {
        return data[c]
    }

    fun getDimensionRanges(): Pair<IntRange, IntRange> {
        val xr: IntRange = (data.keys.minOfOrNull { it.x } ?: 0)..(data.keys.maxOfOrNull { it.x } ?: 0)
        val yr: IntRange = (data.keys.minOfOrNull { it.y } ?: 0)..(data.keys.maxOfOrNull { it.y } ?: 0)
        return Pair(xr, yr)
    }

    fun getSides(): List<List<Pair<Coord, T>>> {
        val (xr, yr) = getDimensionRanges()
        val top = data.entries.filter { it.key.y == yr.last }.map { Pair(it.key, it.value) }
        val right = data.entries.filter { it.key.x == xr.last }.map { Pair(it.key, it.value) }
        val down = data.entries.filter { it.key.y == yr.first }.map { Pair(it.key, it.value) }
        val left = data.entries.filter { it.key.x == xr.first }.map { Pair(it.key, it.value) }
        return listOf(top, right, down, left)
    }

    fun printElement(e: T): Char {
        if (e is Int || e is Long) {
            return if (e != 0) '#' else '.'
        } else if (e is Boolean) {
            return if (e) '#' else '.'
        }
        return '.'
    }

    override fun toString(): String {
        val (xr, yr) = getDimensionRanges()
        var r = ""
        for (yi in yr) {
            for (xi in xr) {
                r += data[Coord(xi, yi)]?.let { printElement(it) } ?: '.'
            }
            r += "\n"
        }
        return r
    }
}

data class Coord3(val x: Int, val y: Int, val z: Int) {
    fun surroundingNeighbors(): List<Coord3> {
        val coords = mutableListOf<Coord3>()
        for (xi in x-1..x+1) {
            for (yi in y-1..y+1) {
                for (zi in z-1..z+1) {
                    if (!(xi == x && yi == y && zi == z)) {
                        coords.add(Coord3(xi, yi, zi))
                    }
                }
            }
        }
        return coords
    }
}

data class Coord4(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun surroundingNeighbors(): List<Coord4> {
        val coords = mutableListOf<Coord4>()
        for (xi in x-1..x+1) {
            for (yi in y-1..y+1) {
                for (zi in z-1..z+1) {
                    for (wi in w-1..w+1) {
                        if (!(xi == x && yi == y && zi == z && wi == w)) {
                            coords.add(Coord4(xi, yi, zi, wi))
                        }
                    }
                }
            }
        }
        return coords
    }
}
