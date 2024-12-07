package day06

import readInput

fun main() {
    fun part1(input: List<String>): Int = calculatePartOne(input)
    fun part2(input: List<String>): Int = calculatePartTwo(input)

    val testInputPart = readInput(name = "Day06_test_part", "day06")
    check(part1(testInputPart) == 41)
    check(part2(testInputPart) == 6)

    val input = readInput(name = "Day06", "day06")
    println("=== Part 1 ===")
    println(part1(input))
    println("=== Part 2 ===")
    println(part2(input))
}

private fun calculatePartOne(input: List<String>): Int =
    input.map().traverse().distinctBy { it.cord }.count()

private fun calculatePartTwo(input: List<String>): Int {
    val map = input.map()
    val originalSteps = map.traverse()
    val alreadyBlockedCoords: MutableList<Cord> = mutableListOf()

    return originalSteps
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

private fun List<String>.map(): Map = Map(map { line -> line.map { it } })

private fun Map.traverse(forceBlock: Cord? = null): List<Step> {
    var curStep = startStep
    val steps: MutableList<Step> = mutableListOf()
    var continueTraversing = true

    while (continueTraversing) {
        val nextStep = curStep.nextStep()
        when {
            steps.contains(curStep) -> {
                steps.add(curStep)
                continueTraversing = false
            }

            nextStep.isOutOfBounds() -> {
                steps.add(curStep)
                continueTraversing = false
            }

            nextStep.isBlockedOnMap() || nextStep.cord == forceBlock -> curStep = curStep.updateDirection()

            else -> {
                steps.add(curStep)
                curStep = nextStep
            }
        }
    }

    return steps
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

private fun Step.nextStep(): Step = Step(
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
        .let { y ->
            val x = map[y].indexOfFirst { it == '^' }
            Step(
                cord = Cord(x = x, y = y),
                direction = Direction.Up
            )
        }

    fun Step.isOutOfBounds() = cord.x !in 0..<map[0].size || cord.y !in 0..<map[0].size
    fun Step.isBlockedOnMap() = map[cord.y][cord.x] == '#'
}
