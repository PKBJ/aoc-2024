import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, day: String) = File("src/$day", "$name.txt").readLines()
