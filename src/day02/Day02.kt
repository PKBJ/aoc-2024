package day02

import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int = countSafeReports(input)
    fun part2(input: List<String>): Int = countSafeReportsIfToleratingOneBadLevel(input)

    val testInputPart = readInput(name = "Day02_test_part", "day02")
    check(part1(testInputPart) == 2)
    check(part2(testInputPart) == 4)

    val input = readInput(name = "Day02", "day02")
    println("=== Part 1 ===")
    println(part1(input))
    println("=== Part 2 ===")
    println(part2(input))
}

private fun List<String>.reports(): List<List<Int>> = map { line ->
    line
        .split(" ")
        .map { it.toInt() }
}

private fun countSafeReports(input: List<String>): Int =
    input
        .reports()
        .count { it.isSafeReport() }

private fun countSafeReportsIfToleratingOneBadLevel(input: List<String>): Int =
    input
        .reports()
        .count { report ->
            var isSafeReport = report.isSafeReport()
            if (!isSafeReport) {
                for (i in report.indices) {
                    val modifiedReport = report.take(i) + report.takeLast(report.lastIndex - i)
                    if (modifiedReport.isSafeReport()) {
                        isSafeReport = true
                        break
                    }
                }
            }
            isSafeReport
        }

private fun List<Int>.isSafeReport(): Boolean =
    when {
        distinct() != this -> false
        sorted() != this && sortedDescending() != this -> false
        else -> {
            var isSafe = true
            for (i in 0..<lastIndex) {
                if (abs(this[i] - this[i + 1]) !in 1..3) {
                    isSafe = false
                    break
                }
            }
            isSafe
        }
    }

