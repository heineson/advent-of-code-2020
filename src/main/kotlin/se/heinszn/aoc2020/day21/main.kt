package se.heinszn.aoc2020.day21

import se.heinszn.aoc2020.readFile

data class Item(val ingredients: List<String>, val allergens: List<String>)
fun parseLine(line: String): Item {
    val (ings, allgs) = line.split(" (contains ")
    return Item(ings.trim().split(" "), allgs.removeSuffix(")").split(",? ".toRegex()))
}

fun allAllergens(items: List<Item>): List<String> = items.flatMap { it.allergens }.distinct()
fun allIngredients(items: List<Item>): List<String> = items.flatMap { it.ingredients }.distinct()

fun findAllergens(items: List<Item>): List<Pair<String, String>> {
    val grouped = allAllergens(items).map { ae ->
        val itemsWithAllergen = items.filter { it.allergens.contains(ae) }
        val occurrences = itemsWithAllergen.flatMap { it.ingredients }.groupingBy { it }.eachCount()
        Pair(ae, occurrences)
    }
    val result = mutableListOf<Pair<String, String>>()
    while (result.size < grouped.size) {
        val remaining = grouped.filter { it.first !in result.map { r -> r.first } }
        remaining.forEach { g ->
            val max = g.second.values.maxOrNull() ?: error("No max")
            val occursMost = g.second
                    .filter { entry -> entry.value == max }
                    .map { it.key }
                    .filter { foreign -> foreign !in result.map { it.second } }
            if (occursMost.size == 1) {
                result.add(Pair(g.first, occursMost[0]))
            }
        }
    }
    println("Found allergens: $result")
    return result
}

fun findNonAllergenOccurrences(items: List<Item>): Map<String, Int> {
    val allergensForeign = findAllergens(items).map { it.second }
    val nonAllergens = allIngredients(items).filter { it !in allergensForeign }
    val result = mutableMapOf<String, Int>()
    items.forEach { item ->
        nonAllergens.forEach { na ->
            if (item.ingredients.contains(na)) {
                result[na] = (result[na] ?: 0) + 1
            }
        }
    }
    return result
}

fun main() {
//    val items = testData.map { parseLine(it) }
    val items = readFile({ parseLine(it) })
    println(items)
    val nonAllergens = findNonAllergenOccurrences(items)
    println(nonAllergens)
    println("Part1: ${nonAllergens.map { it.value }.sum()}")

    val allergens = findAllergens(items)
    println("Part2: ${allergens.sortedBy { it.first }.joinToString(",") { it.second }}")
}

val testData = """
    mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
    trh fvjkl sbzzf mxmxvkd (contains dairy)
    sqjhc fvjkl (contains soy)
    sqjhc mxmxvkd sbzzf (contains fish)
""".trimIndent().split("\n")
