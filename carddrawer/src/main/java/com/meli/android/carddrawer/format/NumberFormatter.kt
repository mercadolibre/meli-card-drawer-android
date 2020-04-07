package com.meli.android.carddrawer.format

object NumberFormatter {
    private const val NON_VALID = "[^*0-9]"
    private const val SEPARATOR = "  "
    private const val FILLER = "*"

    fun format(input: String?, vararg pattern: Int) = separateInGroups(input, pattern)

    fun formatShort(input: String?, vararg pattern: Int): String {
        return ""
    }

    private fun separateInGroups(input: String?, pattern: IntArray): String {
        val cleaned = cleanTextForSubmit(input)
        val formatted = StringBuilder()
        var pos = 0

        for (groupSize in pattern) {
            if (pos > 0) {
                formatted.append(SEPARATOR)
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