package day01

import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int = calculateTotalDistance(input)
    fun part2(input: List<String>): Int = calculateSimilarityScore(input)

    val testInputPart1 = readInput(name = "Day01_test_part", "day01")
    check(part1(testInputPart1) == 11)
    val testInputPart2 = readInput(name = "Day01_test_part", "day01")
    check(part2(testInputPart2) == 31)

    val input = readInput(name = "Day01", "day01")
    println("=== Part 1 ===")
    println(part1(input))
    println("=== Part 2 ===")
    println(part2(input))
}

private fun List<String>.getLists(): Pair<List<Int>, List<Int>> = map { line ->
    line
        .split(" ")
        .let { lineDigits -> lineDigits.first().toInt() to lineDigits.last().toInt() }
}
    .let { listOfPairs ->
        listOfPairs.map { it.first }.sorted() to listOfPairs.map { it.second }.sorted()
    }

private fun calculateTotalDistance(input: List<String>): Int =
    input
        .getLists()
        .let { (list1, list2) ->
            list1.mapIndexed { index, i -> abs(i - list2[index]) }.sum()
        }

private fun calculateSimilarityScore(input: List<String>): Int =
    input.getLists()
        .let { (list1, list2) ->
            list1.sumOf { i -> i * list2.count { it == i } }
        }