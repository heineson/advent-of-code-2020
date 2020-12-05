package se.heinszn.aoc2020

import org.junit.jupiter.api.Test

class MazeSolverTest {
    @Test
    fun testSolveForShortestPath() {
        val maze = Maze()
        maze.parse(testMaze.split("\n"))

        fun getNodeType(c: Coord): NodeType {
            if (c.x !in 0 until maze.width || c.y !in 0 until maze.height) {
                return NodeType.INVALID
            }
            if (maze.walls.contains(c)) {
                return NodeType.WALL
            }
            if (c == maze.end) {
                return NodeType.EXIT
            }
            return NodeType.ROAD
        }

        println("maze: $maze")
        val solver = MazeSolver()
        val path = solver.solveForShortestPath(maze.start) { getNodeType(it) }

        println("Path length=${path.size}, path=${path}")
    }

    private class Maze {
        var start: Coord = Coord(0, 0)
        var end: Coord = Coord(0, 0)
        val walls = mutableListOf<Coord>()
        var width = 0
        var height = 0

        fun parse(lines: List<String>) {
            val realLines = lines.filter { it != "" }
            realLines.forEachIndexed { row, line ->
                run {
                    line.forEachIndexed { col, ch ->
                        run {
                            if (ch == '#') walls.add(Coord(col, row))
                        }
                    }
                }
            }

            start = Coord(realLines.first().indexOfFirst { it == ' ' }, 0)
            end = Coord(realLines.last().indexOfFirst { it == ' ' }, realLines.size - 1)
            width = realLines.first().length
            height = realLines.size
        }

        fun print() {
            for (row in 0 until height) {
                for (col in 0 until width) {
                    if (walls.contains(Coord(col, row))) print('#') else print(' ')
                }
                print("\n")
            }
        }

        override fun toString(): String {
            return "Start=$start, end=$end, width=${width}, height=$height, walls=$walls"
        }
    }

    val testMaze = """
        ##################### ###################
        #     #   #                   #     #   #
        # ### ### # ### ### ######### ### # # # #
        # #       # # #   # #   #   #   # #   # #
        # ######### # ### ### # # # ### ####### #
        # #     #   # #   #   #   # #         # #
        # # ### # ### # # # ####### ######### # #
        #   #   #   # # # # #     #     #     # #
        # ### ##### # # ### ##### ##### ##### # #
        #   #     # # #   #       #   #     #   #
        # ####### # # ### ####### # # ##### ### #
        # #       # #   #       #   # #   # #   #
        # # ####### # ######### ##### # ### ### #
        # #     #   #       #     #   #   #   # #
        # ##### # ####### ### ### # ##### ### # #
        #   # #   #       #   #   #     #   # # #
        ### # ##### ##### # ### ####### # # # # #
        # #   #     #     #   #     # #   # # # #
        # ### ### ### ####### ##### # ##### # # #
        #   #     #     #   #   #   # #     # # #
        # ####### ##### # # ### ### # # ##### ###
        # #       #   # # #   #   #   #     #   #
        # # ####### # ### ### # # ######### #####
        #   #       #     #   # #         # #   #
        # ### ############# ############# # ### #
        #   # #   #       #               #   # #
        ### # # # # ##### ############### ### # #
        #   # # #       #         #       #   # #
        ##### # ############# ##### ####### ### #
        #     #     #     #   #     #     # #   #
        # ####### ### ### # ### ##### # # # ### #
        # #     # #   # # #   #     # # # #   # #
        # # ### ### ### # ### ##### ### # ### # #
        #   #   #   #   #   #     #     # #   # #
        # ### ### ### ##### ### # ### ##### ### #
        # # #   # #       #   # #     #   #     #
        # # ### # ##### # ### ##### ### # ##### #
        # #   # #   #   #   #     # #   # #   # #
        # ### # ### # ##### ##### ### ### # # # #
        #     #       #         #       #   #   #
        ##################### ###################
    """.trimIndent()
}