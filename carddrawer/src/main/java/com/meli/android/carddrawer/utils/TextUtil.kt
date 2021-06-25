package com.meli.android.carddrawer.utils

internal object TextUtil {

    fun isNotEmpty(text: String?): Boolean = !isEmpty(text)

    fun isEmpty(text: String?): Boolean = text == null || text.length <= 0

}