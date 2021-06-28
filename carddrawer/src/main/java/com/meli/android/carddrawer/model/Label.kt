package com.meli.android.carddrawer.model

data class Label(
    val text: Text,
    val backgroundColor: String
) {
    data class Text(
        val text: String,
        val color: String,
        val weight: String
    )
}