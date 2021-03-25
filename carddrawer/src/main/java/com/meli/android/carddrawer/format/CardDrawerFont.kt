package com.meli.android.carddrawer.format

import android.graphics.Typeface
import com.mercadolibre.android.andesui.font.Font

internal enum class CardDrawerFont(val index: Int, val font: Font, val style: Int) {
    BLACK(0, Font.BLACK, Typeface.BOLD),
    BOLD(1, Font.BOLD, Typeface.BOLD),
    EXTRA_BOLD(2, Font.EXTRA_BOLD, Typeface.BOLD),
    LIGHT(3, Font.LIGHT, Typeface.NORMAL),
    REGULAR(4, Font.REGULAR, Typeface.NORMAL),
    SEMI_BOLD(5, Font.SEMI_BOLD, Typeface.BOLD),
    MEDIUM(6, Font.MEDIUM, Typeface.BOLD),
    THIN(7, Font.THIN, Typeface.NORMAL),
    MONOSPACE(8, Font.REGULAR, Typeface.MONOSPACE.style);

    companion object {

        @JvmStatic
        fun from(name: String): CardDrawerFont {
            for (font in values()) {
                if (font.name.equals(name, ignoreCase = true)) {
                    return font
                }
            }
            return REGULAR
        }
    }
}