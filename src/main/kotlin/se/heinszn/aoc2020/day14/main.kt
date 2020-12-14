package se.heinszn.aoc2020.day14

import se.heinszn.aoc2020.readFile
import java.lang.IllegalStateException

val MAX_ADDRESS = 34359738367

data class State(val currMask: String, val currMem: MutableMap<Long, Long>)
fun programStep(step: Triple<String, Long, String>, state: State): State {
    return when(step.first) {
        "mask" -> state.copy(currMask = step.third)
        "mem" -> {
            state.currMem[step.second] = updateMemory(step.third.toLong(), state)
            return state
        }
        else -> throw IllegalStateException("No such op")
    }
}

fun updateMemory(value: Long, state: State): Long {
    val bits = value.toString(2).padStart(36, '0')
    val result = bits.zip(state.currMask) { b, m -> if (m == 'X') b else m }
    println("$bits + ${state.currMask} = ${String(result.toCharArray())}")
    return java.lang.Long.parseLong(String(result.toCharArray()), 2)
}

fun lineParser(l: String): Triple<String, Long, String> {
    val parts = l.split(" = ")
    val op = if (parts[0].startsWith("mask")) "mask" else "mem"
    val pos = if (op == "mem") parts[0].removePrefix("mem[").removeSuffix("]").toLong() else -1L
    return Triple(op, pos, parts[1])
}

fun main() {
//    val data = testData.map { lineParser(it) }
    val data = readFile({ lineParser(it) })

    var state = State("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", mutableMapOf())
    data.forEach { state = programStep(it, state) }

    println("Part1: ${state.currMem.values.sum()}")

}

val testData = """
    mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
    mem[8] = 11
    mem[7] = 101
    mem[8] = 0
""".trimIndent().split("\n")
