package day06

import readInput

fun main() {
    fun part1(input: List<String>): Int = calculatePartOne(input)
    fun part2(input: List<String>): Int = calculatePartTwo(input)

    val testInputPart1 = readInput(name = "Day06_test_part", "day06")
    check(part1(testInputPart1) == 41)
    val testInputPart2 = readInput(name = "Day06_test_part", "day06")
    check(part2(testInputPart2) == 6)

    val input = readInput(name = "Day06", "day06")
    println("=== Part 1 ===")
    println(part1(input))
    println("=== Part 2 ===")
    println(part2(input))
}

private fun List<String>.map(): Map = Map(map { line -> line.map { it } })

private fun calculatePartOne(input: List<String>): Int =
    input.map().traverse().distinctBy { it.cord }.count()

private fun calculatePartTwo(input: List<String>): Int {
    val map = input.map()
    val originalTraversedSteps = map.traverse()
    val alreadyBlockedCoords: MutableList<Cord> = mutableListOf()

    return originalTraversedSteps
        .count { step ->
            if (
                step.cord != map.startStep.cord &&
                !alreadyBlockedCoords.contains(step.cord)
            ) {
                alreadyBlockedCoords.add(step.cord)
                map.traverse(forceBlock = step.cord)
                    .let { steps ->
                        steps.distinct().size != steps.size // Loop detected
                    }
            } else false
        }
}

private fun Map.traverse(forceBlock: Cord? = null): List<Step> {
    var curStep = startStep
    val traversedSteps: MutableList<Step> = mutableListOf()
    var continueTraversing = true

    while (continueTraversing) {
        val nextStep = curStep.getNextStep()
        when {
            traversedSteps.contains(curStep) -> {
                traversedSteps.add(curStep)
                continueTraversing = false
            }

            isOutOfBounds(nextStep) -> {
                traversedSteps.add(curStep)
                continueTraversing = false
            }

            isBlockedOnMap(nextStep) || nextStep.cord == forceBlock -> curStep = curStep.updateDirection()

            else -> {
                traversedSteps.add(curStep)
                curStep = nextStep
            }
        }
    }

    return traversedSteps
}

private enum class Direction {
    Up, Down, Left, Right
}

private data class Cord(val x: Int, val y: Int)

private data class Step(val cord: Cord, val direction: Direction)

private fun Step.updateDirection(): Step = copy(
    direction = when (direction) {
        Direction.Up -> Direction.Right
        Direction.Down -> Direction.Left
        Direction.Left -> Direction.Up
        Direction.Right -> Direction.Down
    }
)

private fun Step.getNextStep(): Step = Step(
    cord = Cord(
        x = when (direction) {
            Direction.Up -> cord.x
            Direction.Down -> cord.x
            Direction.Left -> cord.x - 1
            Direction.Right -> cord.x + 1
        },
        y = when (direction) {
            Direction.Up -> cord.y - 1
            Direction.Down -> cord.y + 1
            Direction.Left -> cord.y
            Direction.Right -> cord.y
        },
    ),
    direction = direction
)

private data class Map(
    private val map: List<List<Char>>,
) {
    val startStep: Step = map
        .indexOfFirst { lines -> lines.contains('^') }
        .let {
            Step(
                cord = Cord(
                    x = map[it].indexOfFirst { it == '^' },
                    y = it,
                ),
                direction = Direction.Up
            )
        }

    fun isOutOfBounds(step: Step) = step.cord.x !in 0..<map[0].size || step.cord.y !in 0..<map[0].size
    fun isBlockedOnMap(step: Step) = map[step.cord.y][step.cord.x] == '#'
}
