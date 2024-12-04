package day03

import readInput

fun main() {
    fun part1(input: List<String>): Int = calculatePartOne(input)
    fun part2(input: List<String>): Int = calculatePartTwo(input)

    val testInputPart1 = readInput(name = "Day03_test_part_1", "day03")
    check(part1(testInputPart1) == 161)
    val testInputPart2 = readInput(name = "Day03_test_part_2", "day03")
    check(part2(testInputPart2) == 48)

    val input = readInput(name = "Day03", "day03")
    println("=== Part 1 ===")
    println(part1(input))
    println("=== Part 2 ===")
    println(part2(input))
}

private fun calculatePartOne(input: List<String>): Int = Regex(pattern = "mul\\(\\d{1,3},\\d{1,3}\\)")
    .findAll(input.joinToString()).toList().sumOf { mul ->
        mul.value.split(",").let { parts ->
            parts[0].filter { it.isDigit() }.toInt() * parts[1].filter { it.isDigit() }.toInt()
        }
    }

private fun calculatePartTwo(input: List<String>): Int {
    val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)")
    val result = regex.findAll(input.joinToString()).toList()

    var enabled = true
    return result.sumOf { match ->
        when {
            match.value == "do()" -> {
                enabled = true
                0
            }

            match.value == "don't()" -> {
                enabled = false
                0
            }

            enabled -> {
                match.value.split(",").let { parts ->
                    parts[0].filter { it.isDigit() }.toInt() * parts[1].filter { it.isDigit() }.toInt()
                }
            }

            else -> 0
        }
    }
}


