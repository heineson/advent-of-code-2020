package se.heinszn.aoc2020.day22

typealias Deck = List<Int>
fun round(decks: Pair<Deck, Deck>): Pair<Deck, Deck> {
    val (p1, p2) = decks
    return if (p1.first() > p2.first()) {
        Pair(p1.drop(1) + p1.first() + p2.first(), p2.drop(1))
    } else {
        Pair(p1.drop(1), p2.drop(1) + p2.first() + p1.first())
    }
}

fun main() {
//    val player1Deck = testPlayer1.map { it.toInt() }
    val player1Deck = realPlayer1.map { it.toInt() }
//    val player2Deck = testPlayer2.map { it.toInt() }
    val player2Deck = realPlayer2.map { it.toInt() }

    var decks = Pair(player1Deck, player2Deck)
    while (decks.first.isNotEmpty() && decks.second.isNotEmpty()) {
        decks = round(decks)
    }
    val winner = if (decks.first.isEmpty()) decks.second else decks.first
    println("Winner's deck: $winner")
    println("Part1: ${winner.reversed().mapIndexed { index, i -> (index + 1) * i }.sum()}")
}

val testPlayer1 = """
    9
    2
    6
    3
    1
""".trimIndent().split("\n")

val testPlayer2 = """
    5
    8
    4
    7
    10
""".trimIndent().split("\n")

val realPlayer1 = """
    47
    19
    22
    31
    24
    6
    10
    5
    1
    48
    46
    27
    8
    45
    16
    28
    33
    41
    42
    36
    50
    39
    30
    11
    17
""".trimIndent().split("\n")

val realPlayer2 = """
    4
    18
    21
    37
    34
    15
    35
    38
    20
    23
    9
    25
    32
    13
    26
    2
    12
    44
    14
    49
    3
    40
    7
    43
    29
""".trimIndent().split("\n")
