package se.heinszn.aoc2020

enum class NodeType {
    ROAD, WALL, EXIT, INVALID
}

class MazeSolver {
    /**
     * BFS Algorithm
     * 1. Add starting coord to a queue
     * 2. While the queue is not empty, pop a coord and do:
     *   2.1 If we reach a wall or the node is already visited, skip to next iteration
     *   2.2 If exit node is reached, backtrack from current node till start node to find the shortest path
     *   2.3 Else, add all immediate neighbors in the four directions in queue
     */
    fun solveForShortestPath(start: Coord, getCoordType: (Coord) -> NodeType): List<Coord> {
        val explored = mutableListOf<MazeCoord>()
        val nextToVisit: ArrayDeque<MazeCoord> = ArrayDeque()
        nextToVisit.add(MazeCoord(start, null))

        while (!nextToVisit.isEmpty()) {
            val next = nextToVisit.removeFirst()

            if (getCoordType(next.coord) == NodeType.INVALID || explored.contains(next)) {
                continue
            }

            if (getCoordType(next.coord) == NodeType.WALL) {
                explored.add(next)
                continue
            }

            if (getCoordType(next.coord) == NodeType.EXIT) {
                return backtrack(next)
            }

            nextToVisit.add(next.up())
            nextToVisit.add(next.right())
            nextToVisit.add(next.down())
            nextToVisit.add(next.left())
            explored.add(next)
        }
        return emptyList()
    }

    private fun backtrack(cur: MazeCoord): List<Coord> {
        val path = mutableListOf<Coord>()
        var iter: MazeCoord? = cur
        while (iter != null) {
            path.add(iter.coord)
            iter = iter.parent
        }
        return path
    }

    private class MazeCoord(val coord: Coord, val parent: MazeCoord?) {
        fun up(): MazeCoord = MazeCoord(this.coord.up(), this)
        fun right(): MazeCoord = MazeCoord(this.coord.right(), this)
        fun down(): MazeCoord = MazeCoord(this.coord.down(), this)
        fun left(): MazeCoord = MazeCoord(this.coord.left(), this)

        override fun equals(other: Any?): Boolean {
            return (other is MazeCoord) && this.coord == other.coord
        }

        override fun toString(): String {
            return "(${coord.x}, ${coord.y})"
        }

        override fun hashCode(): Int {
            return coord.hashCode()
        }
    }

}