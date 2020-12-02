package se.heinszn.aoc2020

import java.io.File

fun <T> readFile(lineParser: (line: String) -> T, filename: String = "./data.txt"): List<T> {
    return File(filename).readLines().map { lineParser(it) }
}
