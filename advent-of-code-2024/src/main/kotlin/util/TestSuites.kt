package util

annotation class TestSuites(
    val testInputFiles: Array<String>,
    val testExpectedOutput: Array<String>,
    val problemFiles: String
)
