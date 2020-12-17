package se.heinszn.aoc2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

internal class UtilsKtTest {

    @Test
    fun testReadLinesIntoTokens() {
        val data = readLinesIntoTokens(lines1)

        assertThat(data).containsExactly(
            listOf("abc"),
            listOf("a", "b", "c"),
            listOf("ab", "ac"),
            listOf("a", "a", "a", "a"),
            listOf("b"),
            listOf("abc")
        )
    }

    @Test
    fun testCoord() {
        val c = Coord(5, 5)

        assertThat(c + Vect(6, 7)).isEqualTo(Coord(11, 12))

        assertThat(c.up()).isEqualTo(Coord(5, 6))
        assertThat(c.right()).isEqualTo(Coord(6, 5))
        assertThat(c.down()).isEqualTo(Coord(5, 4))
        assertThat(c.left()).isEqualTo(Coord(4, 5))

        assertThat(c.manhattan(Coord(10, 14))).isEqualTo(14)

//        assertThat(c.inDirectionWhile(Vect(1, 2)) { it.x <= 10 }).containsExactly(
//                Coord(6, 7),
//                Coord(7, 9),
//                Coord(8, 11),
//                Coord(9, 13),
//                Coord(10, 15),
//        )
    }

    @Test
    fun testVect() {
        assertThat(Vect(1, 1).length()).isEqualTo(sqrt(2.0))
        assertThat(Vect(4, 3).length()).isEqualTo(5.0)

        assertThat(Vect(1, 0).rotate90(Rotation.CW)).isEqualTo(Vect(0, -1))
        assertThat(Vect(1, 0).rotate90(Rotation.CW, 2)).isEqualTo(Vect(-1, 0))
        assertThat(Vect(1, 0).rotate90(Rotation.CW, 3)).isEqualTo(Vect(0, 1))
        assertThat(Vect(1, 0).rotate90(Rotation.CW, 4)).isEqualTo(Vect(1, 0))

        assertThat(Vect(1, 0).rotate90(Rotation.CCW)).isEqualTo(Vect(0, 1))
    }

    private val lines1 = """
    abc

    a
    b
    c

    ab
    ac

    a
    a
    a
    a

    b
    
    abc
    """.trimIndent().split("\n")
}
