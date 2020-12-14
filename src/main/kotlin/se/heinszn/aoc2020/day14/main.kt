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
//    println("$bits + ${state.currMask} = ${String(result.toCharArray())}")
    return java.lang.Long.parseLong(String(result.toCharArray()), 2)
}


fun programStep2(step: Triple<String, Long, String>, state: State): State {
    return when(step.first) {
        "mask" -> state.copy(currMask = step.third)
        "mem" -> {
            updateMemory2(step.second, step.third.toLong(), state)
            return state
        }
        else -> throw IllegalStateException("No such op")
    }
}

fun updateMemory2(pos: Long, value: Long, state: State){
    val bits = pos.toString(2).padStart(36, '0')
    val result = bits.zip(state.currMask) { b, m -> if (m == '0') b else m }

    val list = loop(String(result.toCharArray()), mutableListOf())
    // println("update $pos: " + list.joinToString(", "))

    for (addr in list) {
        state.currMem[java.lang.Long.parseLong(String(addr.toCharArray()), 2)] = value
    }
}

fun loop(mask: String, acc: MutableList<String>): List<String> {
    val xs = mask.mapIndexed { i, c -> Pair(i, c) }.filter { p -> p.second == 'X' }
    // println("loop: $xs, acc: $acc")
    return if (xs.isEmpty()) acc + mask else acc + loop(mask.replaceFirst('X', '1'), acc) + loop(mask.replaceFirst('X', '0'), acc)
}


fun lineParser(l: String): Triple<String, Long, String> {
    val parts = l.split(" = ")
    val op = if (parts[0].startsWith("mask")) "mask" else "mem"
    val pos = if (op == "mem") parts[0].removePrefix("mem[").removeSuffix("]").toLong() else -1L
    return Triple(op, pos, parts[1])
}

fun part1() {
    //    val data = testData.map { lineParser(it) }
    val data = readFile({ lineParser(it) })

    var state = State("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", mutableMapOf())
    data.forEach { state = programStep(it, state) }

    println("Part1: ${state.currMem.values.sum()}")
}

fun part2() {
//    val data = testData2.map { lineParser(it) }
    val data = readFile({ lineParser(it) })

    var state = State("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", mutableMapOf())
    data.forEach { state = programStep2(it, state) }

    println("Part2: ${state.currMem.values.sum()}")
}

fun main() {
    part1()
    part2()
}

val testData = """
    mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
    mem[8] = 11
    mem[7] = 101
    mem[8] = 0
""".trimIndent().split("\n")

val testData2 = """
    mask = 000000000000000000000000000000X1001X
    mem[42] = 100
    mask = 00000000000000000000000000000000X0XX
    mem[26] = 1
""".trimIndent().split("\n")
