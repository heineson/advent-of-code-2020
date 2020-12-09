package se.heinszn.aoc2020.day8

import se.heinszn.aoc2020.readFile
import se.heinszn.aoc2020.updateElement

fun lineParser(line: String): Pair<String, Int> {
    val parts = line.split(" ")
    return Pair(parts[0], parts[1].toInt())
}

typealias ProgramCode = List<Pair<String, Int>>

class Program(private val program: ProgramCode) {

    private var accumulator = 0
    private var pointer: Int = 0
    private val executed = mutableListOf<Int>()

    private fun executeNextOp() {
        if (pointer < 0) {
            throw IllegalStateException("Negative index $pointer")
        }
        val op = program[pointer]
        if (executed.contains(pointer)) {
            throw IllegalStateException("Infinite loop: index $pointer executed again")
        }
        executed.add(pointer)
        when(op.first) {
            "acc" -> {
                accumulator += op.second
                pointer += 1
            }
            "jmp" -> pointer += op.second
            "nop" -> pointer += 1
            else -> throw IllegalArgumentException("Illegal instruction: ${op.first}")
        }
    }

    fun run(): Int {
        try {
            while (true) {
                executeNextOp()
                if (pointer == program.size) {
                    println("Done! Acc: $accumulator")
                    return 0
                }
            }
        } catch (e: IllegalStateException) {
            println(e.message)
        }

        return -1
    }
}

fun changeNext(index: Int, program: ProgramCode): Pair<Int, ProgramCode> {
    var i = index
    while (program[i].first == "acc") i++
    val elem = if (program[i].first == "jmp") Pair("nop", program[i].second) else Pair("jmp", program[i].second)
    val newProgram: ProgramCode = program.updateElement(i, elem)
    return Pair(i, newProgram)
}

fun main() {
//    val data = testData.map { lineParser(it) }
    val data = readFile({ lineParser(it) })
    Program(data).run()

    var cnt = 0
    while (cnt < data.size) {
        val result = changeNext(cnt, data)
        cnt = result.first + 1
        if(Program(result.second).run() == 0) {
            break
        }
    }
}

val testData = """
    nop +0
    acc +1
    jmp +4
    acc +3
    jmp -3
    acc -99
    acc +1
    jmp -4
    acc +6
""".trimIndent().split("\n")
