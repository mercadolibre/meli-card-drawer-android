package com.meli.android.carddrawer.configuration

enum class CardDrawerStyle(val value: Int) {
    REGULAR(0), ACCOUNT_MONEY(1), ACCOUNT_MONEY_LEGACY(2);

    companion object {
        @JvmStatic fun fromValue(value: Int): CardDrawerStyle {
            for (style in values()) {
                if (style.value == value) {
                    return style
                }
            }
            return REGULAR
        }
    }
}