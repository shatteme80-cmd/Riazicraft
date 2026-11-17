package com.example.util

object PersianUtils {
    private val englishDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    private val persianDigits = charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')

    fun toPersian(input: Any?): String {
        if (input == null) return ""
        val str = input.toString()
        val builder = StringBuilder(str.length)
        for (char in str) {
            val index = englishDigits.indexOf(char)
            if (index != -1) {
                builder.append(persianDigits[index])
            } else {
                builder.append(char)
            }
        }
        return builder.toString()
    }

    fun formatSet(elements: Collection<Int>, name: String? = null): String {
        val sortedList = elements.sorted()
        val elementsStr = sortedList.joinToString("، ") { toPersian(it) }
        val body = if (sortedList.isEmpty()) "∅" else "{ $elementsStr }"
        return if (name != null) "$name = $body" else body
    }

    fun formatFraction(numerator: Int, denominator: Int): String {
        return "${toPersian(numerator)} / ${toPersian(denominator)}"
    }
}
