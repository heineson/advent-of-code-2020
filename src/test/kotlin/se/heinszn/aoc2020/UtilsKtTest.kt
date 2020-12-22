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
