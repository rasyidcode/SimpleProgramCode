package me.jamilalrasyidis.simpleprogramcode.extension

import java.util.*

fun String.toFileNameStyle(): String {
    return this.replace(" ", "_").toLowerCase(Locale.ENGLISH)
}

fun String.convertToFileType(): String {
    return when {
        this == "java" -> {
            ".java"
        }
        this == "python" -> {
            ".py"
        }
        this == "php" -> {
            ".php"
        }
        else -> {
            ".txt"
        }
    }
}

fun String.toCodeFormat(): String {
    return replace("\\n", "\n", true)
}