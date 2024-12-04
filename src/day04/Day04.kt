package day04

import readInput

fun main() {
    fun part1(input: List<String>): Int = countXMAS(input)
    fun part2(input: List<String>): Int = `countX-MAS`(input)

    val testInputPart1 = readInput(name = "Day04_test_part", "day04")
    check(part1(testInputPart1) == 18)
    val testInputPart2 = readInput(name = "Day04_test_part", "day04")
    check(part2(testInputPart2) == 9)

    val input = readInput(name = "Day04", "day04")
    println("=== Part 1 ===")
    println(part1(input))
    println("=== Part 2 ===")
    println(part2(input))
}

private fun List<String>.map(): List<List<Char>> = map { line -> line.map { it } }

private fun countXMAS(input: List<String>): Int =
    input
        .map()
        .let { map ->
            map.mapIndexed { y, line ->
                line.mapIndexed { x, c ->
                    if (c != 'X') 0 else map.countXMax(x = x, y = y)
                }.sum()
            }.sum()
        }

private fun `countX-MAS`(input: List<String>): Int =
    input
        .map()
        .let { map ->
            map.mapIndexed { y, line ->
                line.mapIndexed { x, c ->
                    if (c != 'A') 0 else map.`countX-MAS`(x = x, y = y)
                }.sum()
            }.sum()
        }

private fun List<List<Char>>.`countX-MAS`(x: Int, y: Int): Int {
    val result = findXMas1(x = x, y = y) ||
            findXMas2(x = x, y = y) ||
            findXMas3(x = x, y = y) ||
            findXMas4(x = x, y = y)

    return if (result) 1 else 0
}

private fun List<List<Char>>.countXMax(x: Int, y: Int): Int = listOf(
    findXMasLeft(x = x, y = y),
    findXMasLeftDown(x = x, y = y),
    findXMasLeftUp(x = x, y = y),
    findXMasRightDown(x = x, y = y),
    findXMasRightUp(x = x, y = y),
    findXMasRight(x = x, y = y),
    findXMasUp(x = x, y = y),
    findXMasDown(x = x, y = y),
).count { it }

private fun List<List<Char>>.findXMasLeft(x: Int, y: Int): Boolean =
    getOrNull(y)?.getOrNull(x - 1) == 'M' &&
            getOrNull(y)?.getOrNull(x - 2) == 'A' &&
            getOrNull(y)?.getOrNull(x - 3) == 'S'

private fun List<List<Char>>.findXMasLeftUp(x: Int, y: Int): Boolean =
    getOrNull(y - 1)?.getOrNull(x - 1) == 'M' &&
            getOrNull(y - 2)?.getOrNull(x - 2) == 'A' &&
            getOrNull(y - 3)?.getOrNull(x - 3) == 'S'


private fun List<List<Char>>.findXMasLeftDown(x: Int, y: Int): Boolean =
    getOrNull(y + 1)?.getOrNull(x - 1) == 'M' &&
            getOrNull(y + 2)?.getOrNull(x - 2) == 'A' &&
            getOrNull(y + 3)?.getOrNull(x - 3) == 'S'

private fun List<List<Char>>.findXMasUp(x: Int, y: Int): Boolean =
    getOrNull(y - 1)?.getOrNull(x) == 'M' &&
            getOrNull(y - 2)?.getOrNull(x) == 'A' &&
            getOrNull(y - 3)?.getOrNull(x) == 'S'


private fun List<List<Char>>.findXMasDown(x: Int, y: Int): Boolean =
    getOrNull(y + 1)?.getOrNull(x) == 'M' &&
            getOrNull(y + 2)?.getOrNull(x) == 'A' &&
            getOrNull(y + 3)?.getOrNull(x) == 'S'

private fun List<List<Char>>.findXMasRightUp(x: Int, y: Int): Boolean =
    getOrNull(y - 1)?.getOrNull(x + 1) == 'M' &&
            getOrNull(y - 2)?.getOrNull(x + 2) == 'A' &&
            getOrNull(y - 3)?.getOrNull(x + 3) == 'S'

private fun List<List<Char>>.findXMasRight(x: Int, y: Int): Boolean =
    getOrNull(y)?.getOrNull(x + 1) == 'M' &&
            getOrNull(y)?.getOrNull(x + 2) == 'A' &&
            getOrNull(y)?.getOrNull(x + 3) == 'S'

private fun List<List<Char>>.findXMasRightDown(x: Int, y: Int): Boolean =
    getOrNull(y + 1)?.getOrNull(x + 1) == 'M' &&
            getOrNull(y + 2)?.getOrNull(x + 2) == 'A' &&
            getOrNull(y + 3)?.getOrNull(x + 3) == 'S'

private fun List<List<Char>>.findXMas1(x: Int, y: Int): Boolean =
    getOrNull(y - 1)?.getOrNull(x - 1) == 'M' &&
            getOrNull(y - 1)?.getOrNull(x + 1) == 'M' &&
            getOrNull(y + 1)?.getOrNull(x - 1) == 'S' &&
            getOrNull(y + 1)?.getOrNull(x + 1) == 'S'

private fun List<List<Char>>.findXMas2(x: Int, y: Int): Boolean =
    getOrNull(y - 1)?.getOrNull(x - 1) == 'M' &&
            getOrNull(y - 1)?.getOrNull(x + 1) == 'S' &&
            getOrNull(y + 1)?.getOrNull(x - 1) == 'M' &&
            getOrNull(y + 1)?.getOrNull(x + 1) == 'S'

private fun List<List<Char>>.findXMas3(x: Int, y: Int): Boolean =
    getOrNull(y - 1)?.getOrNull(x - 1) == 'S' &&
            getOrNull(y - 1)?.getOrNull(x + 1) == 'S' &&
            getOrNull(y + 1)?.getOrNull(x - 1) == 'M' &&
            getOrNull(y + 1)?.getOrNull(x + 1) == 'M'

private fun List<List<Char>>.findXMas4(x: Int, y: Int): Boolean =
    getOrNull(y - 1)?.getOrNull(x - 1) == 'S' &&
            getOrNull(y - 1)?.getOrNull(x + 1) == 'M' &&
            getOrNull(y + 1)?.getOrNull(x - 1) == 'S' &&
            getOrNull(y + 1)?.getOrNull(x + 1) == 'M'