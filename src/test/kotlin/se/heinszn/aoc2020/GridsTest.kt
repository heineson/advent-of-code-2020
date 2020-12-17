package se.heinszn.aoc2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GridsTest {
    @Test
    fun testCoord3_GetSurroundingNeighbors() {
        val c = Coord3(0, 0, 0)
        val n = c.surroundingNeighbors()

        assertThat(n).hasSize(26)
        assertThat(n.toSet()).hasSize(26)
        assertThat(n).doesNotContain(c)
        assertThat(n.map { it.x }.maxOrNull()).isEqualTo(1)
        assertThat(n.map { it.x }.minOrNull()).isEqualTo(-1)
        assertThat(n.map { it.y }.maxOrNull()).isEqualTo(1)
        assertThat(n.map { it.y }.minOrNull()).isEqualTo(-1)
        assertThat(n.map { it.z }.maxOrNull()).isEqualTo(1)
        assertThat(n.map { it.z }.minOrNull()).isEqualTo(-1)
    }

    @Test
    fun testCoord4_GetSurroundingNeighbors() {
        val c = Coord4(0, 0, 0, 0)
        val n = c.surroundingNeighbors()

        assertThat(n).hasSize(80)
        assertThat(n.toSet()).hasSize(80)
        assertThat(n).doesNotContain(c)
        assertThat(n.map { it.x }.maxOrNull()).isEqualTo(1)
        assertThat(n.map { it.x }.minOrNull()).isEqualTo(-1)
        assertThat(n.map { it.y }.maxOrNull()).isEqualTo(1)
        assertThat(n.map { it.y }.minOrNull()).isEqualTo(-1)
        assertThat(n.map { it.z }.maxOrNull()).isEqualTo(1)
        assertThat(n.map { it.z }.minOrNull()).isEqualTo(-1)
        assertThat(n.map { it.w }.maxOrNull()).isEqualTo(1)
        assertThat(n.map { it.w }.minOrNull()).isEqualTo(-1)
    }
}
