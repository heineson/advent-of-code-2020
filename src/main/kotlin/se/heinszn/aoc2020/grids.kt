package se.heinszn.aoc2020

data class Coord3(val x: Int, val y: Int, val z: Int) {
    fun surroundingNeighbors(): List<Coord3> {
        val coords = mutableListOf<Coord3>()
        for (xi in x-1..x+1) {
            for (yi in y-1..y+1) {
                for (zi in z-1..z+1) {
                    coords.add(Coord3(xi, yi, zi))
                }
            }
        }
        return coords.filter { it != this }
    }
}

data class Coord4(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun surroundingNeighbors(): List<Coord4> {
        val coords = mutableListOf<Coord4>()
        for (xi in x-1..x+1) {
            for (yi in y-1..y+1) {
                for (zi in z-1..z+1) {
                    for (wi in w-1..w+1) {
                        coords.add(Coord4(xi, yi, zi, wi))
                    }
                }
            }
        }
        return coords.filter { it != this }
    }
}
