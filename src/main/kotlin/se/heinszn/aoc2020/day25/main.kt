package se.heinszn.aoc2020.day25

fun transformSubjectNumber(s: Long, loopSize: Long): Long {
    var current = 1L
    for (i in 1..loopSize) {
        current = (current * s) % 20201227
    }
    return current
}

fun findLoopSize(pk: Long): Long {
    var ls = 0L
    var current = 1L
    while (current != pk) {
        current = (current * 7) % 20201227
        ls++
    }
    return ls
}

fun getSecretKey(cls: Long, cpk: Long, dls: Long, dpk: Long): Long {
    val encKey = transformSubjectNumber(dpk, cls)
    val confirm = transformSubjectNumber(cpk, dls)
    if (encKey != confirm) error("Should be equal, was $encKey != $confirm")
    return encKey
}

fun part1(cpk: Long, dpk: Long) {
    val dls = findLoopSize(dpk)
    println("Found dls = $dls")
    val cls = findLoopSize(cpk)
    println("Found cls = $cls")
    val secretKey = getSecretKey(cls, cpk, dls, dpk)
    println("Part1: $secretKey")
}

fun main() {
    //part1(testCardPK.toLong(), testDoorPK.toLong())
    part1(realCardPK.toLong(), realDoorPK.toLong())
}

val testCardPK = 5764801
val testDoorPK = 17807724

val realCardPK = 1327981
val realDoorPK = 2822615