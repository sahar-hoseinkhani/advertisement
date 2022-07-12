package ir.ayantech.advertisement.helper

typealias SimpleCallback = () -> Unit

typealias StringCallback = (String) -> Unit

typealias TwoStringCallback = (String, String) -> Unit

typealias IntCallback = (Int) -> Unit

typealias BooleanCallback = (Boolean) -> Unit

typealias StringReturn = () -> String

fun trying(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}