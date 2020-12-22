package se.heinszn.aoc2020.day22

typealias Deck = List<Int>

fun game(startingDecks: Pair<Deck, Deck>): Pair<Deck, Deck> {
    var decks = startingDecks
    while (decks.first.isNotEmpty() && decks.second.isNotEmpty()) {
        decks = round(decks)
    }
    return decks
}

fun round(decks: Pair<Deck, Deck>): Pair<Deck, Deck> {
    val (p1, p2) = decks
    return if (p1.first() > p2.first()) {
        Pair(p1.drop(1) + p1.first() + p2.first(), p2.drop(1))
    } else {
        Pair(p1.drop(1), p2.drop(1) + p2.first() + p1.first())
    }
}

var gameCnt = 0

fun recursiveGame(startingDecks: Pair<Deck, Deck>): Pair<Deck, Deck> {
    gameCnt++
    val currGame = gameCnt
    val prevStates1 = mutableSetOf<Deck>()
    val prevStates2 = mutableSetOf<Deck>()
    var decks = startingDecks
    var roundCnt = 0
    while (decks.first.isNotEmpty() && decks.second.isNotEmpty()) {
        roundCnt++
        decks = recursiveRound(decks, prevStates1, prevStates2)
        if (currGame == 1) {
            println("-- Round $roundCnt (Game $currGame) --")
            println("Player 1's deck: ${decks.first}")
            println("Player 2's deck: ${decks.second}\n")
        }
    }
    return decks
}

fun recursiveRound(decks: Pair<Deck, Deck>, prevRounds1: MutableSet<Deck>, prevRounds2: MutableSet<Deck>): Pair<Deck, Deck> {
    val (p1, p2) = decks
    if (prevRounds1.contains(p1) || prevRounds2.contains(p2)) {
        return Pair(p1.drop(1) + p1.first() + p2.first(), p2.drop(1))
    } else {
        prevRounds1.add(p1)
        prevRounds2.add(p2)
    }
    return if ((p1.size - 1) >= p1.first() && (p2.size - 1) >= p2.first()) {
        val (sp1, sp2) = recursiveGame(Pair(
                p1.drop(1).take(p1.first()),
                p2.drop(1).take(p2.first())
        ))
        if (sp1.size > sp2.size) {
            Pair(p1.drop(1) + p1.first() + p2.first(), p2.drop(1))
        } else {
            Pair(p1.drop(1), p2.drop(1) + p2.first() + p1.first())
        }
    } else {
        round(decks)
    }
}

fun main() {
//    val player1Deck = testPlayer1.map { it.toInt() }
    val player1Deck = realPlayer1.map { it.toInt() }
//    val player2Deck = testPlayer2.map { it.toInt() }
    val player2Deck = realPlayer2.map { it.toInt() }

    val (p1, p2) = game(Pair(player1Deck, player2Deck))
    val winner = if (p1.isEmpty()) p2 else p1
    println("Winner's deck: $winner")
    println("Part1: ${winner.reversed().mapIndexed { index, i -> (index + 1) * i }.sum()}")

    val (p12, p22) = recursiveGame(Pair(player1Deck, player2Deck))
    val winner2 = if (p12.isEmpty()) p22 else p12
    println("Winner2's deck: $winner2")
    println("Part2: ${winner2.reversed().mapIndexed { index, i -> (index + 1) * i }.sum()}")
}
// 33534 too high
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
