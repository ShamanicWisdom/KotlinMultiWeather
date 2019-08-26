package common.szaman

expect fun platformName(): String

fun createApplicationScreenMessage() : String
{
    return "Kotlin Rocks on ${platformName()}"
}