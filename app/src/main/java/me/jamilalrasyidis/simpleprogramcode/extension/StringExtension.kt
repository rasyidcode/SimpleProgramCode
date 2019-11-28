package me.jamilalrasyidis.simpleprogramcode.extension

fun String.toCodeFormat() : String {
    return replace("\\n", "\n", true)
}