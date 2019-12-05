package me.jamilalrasyidis.simpleprogramcode.extension

import java.util.*

fun String.getColorLanguage() : String {
    return when {
        this == "java" -> {
            "#00E676"
        }
        this == "python" -> {
            "#0091EA"
        }
        this == "php" -> {
            "#FFFF00"
        }
        this == "kotlin" -> {
            "#E65100"
        }
        else -> {
            "#FF6F00"
        }
    }
}

fun String.countNewLine() : Int {
    return this.fold(0) {
            sum: Int, c: Char ->
        if ("\\n".contains(c))
            sum + 1
        else
            sum
    }
}

fun String.reverseCodeFormat() : String {
    return replace("\\n", "\n", true)
}

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
        this == "kotlin" -> {
            ".kt"
        }
        else -> {
            ".txt"
        }
    }
}

fun String.toCodeFormat(): String {
    return replace("\\n", "\n", true)
}