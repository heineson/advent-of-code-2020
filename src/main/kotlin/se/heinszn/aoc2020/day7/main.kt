package se.heinszn.aoc2020.day7

import se.heinszn.aoc2020.readFile

val regexInput = """^(.+) bags contain (.+)$""".toRegex()
val regexBagContents = """(\d+) (.+?) bags?[.,]""".toRegex()
const val suffixNoContents = " bags contain no other bags."

data class Bag(val color: String, val contents: List<Pair<String, Int>>) {
    fun canHold(b: Bag): Boolean = contents.find { it.first == b.color } != null
    fun canHoldAnyOf(bags: Iterable<Bag>): Boolean = bags.any { canHold(it) }

    override fun equals(other: Any?): Boolean {
        return (other is Bag) && color == other.color
    }

    override fun hashCode(): Int {
        return color.hashCode()
    }
}

fun bagOf(input: String): Bag {
    return if (input.endsWith(suffixNoContents)) {
        Bag(input.removeSuffix(suffixNoContents), emptyList())
    } else {
        val (color, contentString) = regexInput.find(input)!!.destructured
        val matches = regexBagContents.findAll(contentString).map {
            val (count, color2) = it.destructured
            Pair(color2, count.toInt())
        }.toList()

        Bag(color, matches.map { Pair(it.first, it.second) })
    }
}

fun countOuterBags(myBag: Bag,  bags: List<Bag>): Int {
    val containers = mutableSetOf<Bag>()

    var holders = bags.filter { it.canHold(myBag) }
    while (holders.isNotEmpty()) {
        containers.addAll(holders)
        holders = bags.filter { it.canHoldAnyOf(containers) }.filter { !containers.contains(it) }
    }

    return containers.size
}

fun countInnerBags(myBag: Bag, bags: List<Bag>): Int {
    var counter = 0

    var innerBags = myBag.contents
    while (innerBags.isNotEmpty()) {
        counter += innerBags.sumBy { it.second }
        val nextBags = innerBags.map { pair -> Pair(bags.find { b -> b.color == pair.first }!!, pair.second) }
        innerBags = nextBags.flatMap { nb -> nb.first.contents.map { p -> Pair(p.first, p.second * nb.second) } }
    }
    return counter
}

fun main() {
    //val bags: List<Bag> = testData.map { bagOf(it) }
    val bags: List<Bag> = readFile({ bagOf(it) })
    val myBag = bags.find { it.color == "shiny gold" }!!
    println("Bag colors count: ${countOuterBags(myBag, bags)}")

//    val myBag2 = Bag("shiny gold", listOf(Pair("dark red", 2)))
//    val bags2 = testData2.map { bagOf(it) }
//    println("Bag colors: ${countInnerBags(myBag2, bags2)}")

    println("Bag contents count: ${countInnerBags(myBag, bags)}")
}

val testData = """
    light red bags contain 1 bright white bag, 2 muted yellow bags.
    dark orange bags contain 3 bright white bags, 4 muted yellow bags.
    bright white bags contain 1 shiny gold bag.
    muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
    shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
    dark olive bags contain 3 faded blue bags, 4 dotted black bags.
    vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
    faded blue bags contain no other bags.
    dotted black bags contain no other bags.
""".trimIndent().split("\n")

val testData2 = """
    shiny gold bags contain 2 dark red bags.
    dark red bags contain 2 dark orange bags.
    dark orange bags contain 2 dark yellow bags.
    dark yellow bags contain 2 dark green bags.
    dark green bags contain 2 dark blue bags.
    dark blue bags contain 2 dark violet bags.
    dark violet bags contain no other bags.
""".trimIndent().split("\n")
