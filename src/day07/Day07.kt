package day07

import readInput

fun main() {
    fun part1(input: List<String>): Long = calculatePartOne(input)
    fun part2(input: List<String>): Long = calculatePartTwo(input)

    val testInputPart = readInput(name = "Day07_test_part", "day07")
    check(part1(testInputPart) == 3749L)
    check(part2(testInputPart) == 11387L)

    val input = readInput(name = "Day07", "day07")
    println("=== Part 1 ===")
    println(part1(input))
    println("=== Part 2 ===")
    println(part2(input))
}

private fun calculatePartOne(input: List<String>): Long =
    input.sumOf {
        val splitted = it.split(":")
        val testValue = splitted[0].toLong()
        val calibrationNumbers = splitted[1].trim().split(" ").map { number -> number.toLong() }
        if (
            recursive(
                target = testValue,
                acc = calibrationNumbers.first(),
                list = calibrationNumbers,
                index = 1,
                operators = listOf(
                    { acc: Long, number: Long -> acc * number },
                    { acc: Long, number: Long -> acc + number },
                ),
            )
        ) {
            testValue
        } else 0
    }

private fun calculatePartTwo(input: List<String>): Long =
    input.sumOf {
        val splitted = it.split(":")
        val testValue = splitted[0].toLong()
        val calibrationNumbers = splitted[1].trim().split(" ").map { number -> number.toLong() }
        if (
            recursive(
                target = testValue,
                acc = calibrationNumbers.first(),
                list = calibrationNumbers,
                index = 1,
                operators = listOf(
                    { acc: Long, number: Long -> acc + number },
                    { acc: Long, number: Long -> acc * number },
                    { acc: Long, number: Long -> "$acc$number".toLong() },
                ),
            )
        ) {
            testValue
        } else 0
    }

private fun recursive(
    target: Long,
    acc: Long,
    list: List<Long>,
    index: Int,
    operators: List<(Long, Long) -> Long>,
): Boolean = when {
    index == list.size -> acc == target
    else -> operators.any { operator ->
        recursive(
            target = target,
            acc = operator(acc, list[index]),
            list = list,
            index = index + 1,
            operators = operators,
        )
    }
}

