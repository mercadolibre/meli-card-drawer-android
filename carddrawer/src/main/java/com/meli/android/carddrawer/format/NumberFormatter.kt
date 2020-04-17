package com.meli.android.carddrawer.format

object NumberFormatter {
    private const val SHORT_PATTERN_MAX_CHARS = 10
    private const val NON_VALID = "[^*0-9]"
    private const val SEPARATOR = "  "
    private const val SHORT_SEPARATOR = " "
    private const val FILLER = "*"

    fun format(input: String?, vararg pattern: Int) = separateInGroups(input, pattern, SEPARATOR)

    fun formatShort(input: String?, vararg pattern: Int): String {
        var totalCount = 0
        var groupSize = 0
        var i = pattern.size - 1
        if (i >= 0) {
            groupSize = pattern[i]
        }
        while(i >= 0 && groupSize + totalCount <= SHORT_PATTERN_MAX_CHARS) {
            if (totalCount > 0 && groupSize > 0) {
                //separator
                totalCount++
            }
            totalCount += groupSize
            if (--i >= 0) {
                groupSize = pattern[i]
            }
        }
        return separateInGroups(input, pattern, SHORT_SEPARATOR).run {
            substring(length-totalCount until length)
        }
    }

    private fun separateInGroups(input: String?, pattern: IntArray, separator: String): String {
        val cleaned = cleanTextForSubmit(input)
        val formatted = StringBuilder()
        var pos = 0

        for (groupSize in pattern) {
            if (pos > 0) {
                formatted.append(separator)
            }
            for (i in 0 until groupSize) {
                formatted.append(if (pos < cleaned.length) cleaned[pos] else FILLER)
                pos++
            }
        }

        return formatted.toString()
    }

    private fun cleanTextForSubmit(input: String?) = input?.replace(NON_VALID.toRegex(), "") ?: ""
}