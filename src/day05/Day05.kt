package day05

import readInput

fun main() {
    fun part1(input: List<String>): Int = calculatePartOne(input)
    fun part2(input: List<String>): Int = calculatePartTwo(input)

    val testInputPart1 = readInput(name = "Day05_test_part", "day05")
    check(part1(testInputPart1) == 143)
    val testInputPart2 = readInput(name = "Day05_test_part", "day05")
    check(part2(testInputPart2) == 123)

    val input = readInput(name = "Day05", "day05")
    println("=== Part 1 ===")
    println(part1(input))
    println("=== Part 2 ===")
    println(part2(input))
}

private fun List<String>.readInput(): Pair<Map<Int, List<Int>>, List<String>> =
    filter { it.contains("|") }.groupBy {
        it.split("|")[0].toInt()
    }.map { it.key to it.value.map { it.split("|")[1].toInt() } }
        .toMap() to filter { it.isNotBlank() && !it.contains("|") }

private fun calculatePartOne(input: List<String>): Int {
    val (rules, updates) = input.readInput()
    return updates
        .map { it.split(",").map { it.toInt() } }
        .filter { update -> update.isCorrectOrder(rules) }
        .sumOf { it[it.size / 2] }
}

private fun calculatePartTwo(input: List<String>): Int {
    val (rules, updates) = input.readInput()
    return updates
        .map { it.split(",").map { it.toInt() } }
        .filter { update -> !update.isCorrectOrder(rules) }
        .sumOf { it.correctOrder(rules)[it.size / 2] }
}

private fun List<Int>.correctOrder(rules: Map<Int, List<Int>>): List<Int> {
    val result = toIntArray()
    for (i in 0 until size - 1) {
        for (j in 0 until size - i - 1) {
            val a = result[j]
            val b = result[j + 1]
            if (rules[b]?.contains(a) == true) {
                val temp = result[j]
                result[j] = result[j + 1]
                result[j + 1] = temp
            }
        }
    }
    return result.toList()
}

private fun List<Int>.isCorrectOrder(rules: Map<Int, List<Int>>): Boolean {
    forEachIndexed { index, number ->
        if (index < size - 1) {
            val rule = rules[number].orEmpty()
            if (!subList(index + 1, size).all { it in rule }) {
                return false
            }
        }
    }
    return true
}
