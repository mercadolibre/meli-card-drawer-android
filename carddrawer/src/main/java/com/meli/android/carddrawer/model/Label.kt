package com.meli.android.carddrawer.model

data class Label(
    val text: String,
    val backgroundColor: String?,
    val color: String?,
    val weight: String?
) {
    constructor(text: String) : this(text, null, null, null)
}