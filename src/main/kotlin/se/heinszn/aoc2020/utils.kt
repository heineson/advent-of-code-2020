package se.heinszn.aoc2020

import java.io.File
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt

/****** Input data utils *******/

fun <T> parseLines(lineParser: (line: String) -> T, lines: List<String>): List<T> {
    return lines.map { lineParser(it) }
}

fun <T> readFile(lineParser: (line: String) -> T, filename: String = "./data.txt"): List<T> {
    return parseLines(lineParser, File(filename).readLines())
}

fun readLinesIntoTokens(lines: List<String>, groupSeparatorLine: String = "", tokenSeparator: String = " "): List<List<String>> {
    val groups = mutableListOf<List<String>>()
    var group = mutableListOf<String>()
    lines.forEachIndexed { i, s ->
        run {
            if (s == groupSeparatorLine) {
                groups.add(group)
                group = mutableListOf()
            } else {
                group.addAll(s.split(tokenSeparator))
                // In case final line is not a "separator line"
                if (i == lines.size - 1) {
                    groups.add(group)
                }
            }
        }
    }


    return groups
}

fun readFileIntoTokens(groupSeparatorLinePattern: String = "", tokenSeparator: String = " ", filename: String = "./data.txt"): List<List<String>> {
    return readLinesIntoTokens(File(filename).readLines(), groupSeparatorLinePattern, tokenSeparator)
}

/****** extensions *******/

fun <E> Iterable<E>.updateElement(index: Int, newElem: E) = mapIndexed { i, existing ->  if (i == index) newElem else existing }
