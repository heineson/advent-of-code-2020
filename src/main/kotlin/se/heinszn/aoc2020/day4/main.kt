package se.heinszn.aoc2020.day4

import se.heinszn.aoc2020.readFileIntoTokens

fun validateFieldsPresent(fields: List<String>): Boolean {
    if (fields.size < 7) return false
    if (fields.size == 7) {
        return fields.find { it.startsWith("cid:") } == null
    }
    return true
}

fun validateFields(fields: List<String>): Boolean {
    return fields
            .map { Pair(it.split(":")[0], it.split(":")[1]) }
            .map { validateField(it) }
            .all { it }
}

fun validateField(field: Pair<String, String>): Boolean {
    return when (field.first) {
        "byr" -> field.second.toInt() in 1920..2002
        "iyr" -> field.second.toInt() in 2010..2020
        "eyr" -> field.second.toInt() in 2020..2030
        "hgt" -> if (field.second.endsWith("cm")) {
            field.second.removeSuffix("cm").toInt() in 150..193
        } else {
            field.second.removeSuffix("in").toInt() in 59..76
        }
        "hcl" -> field.second.matches("""#[a-f0-9]{6}""".toRegex())
        "ecl" -> field.second.matches("""(amb|blu|brn|gry|grn|hzl|oth)""".toRegex())
        "pid" -> field.second.matches("""\d{9}""".toRegex())
        "cid" -> true
        else -> false
    }
}

fun main() {
    val data = readFileIntoTokens(filename = "./data.txt")
    println(data)
    val valid = data.count { validateFieldsPresent(it) && validateFields(it) }
    println(valid)
}
