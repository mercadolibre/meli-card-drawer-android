package com.meli.android.carddrawer.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LabelTest {

    private lateinit var label: Label
    private val text by lazy { "test" }
    private val backgroundColor by lazy { "#FFFFFF" }
    private val color by lazy { "#000000" }
    private val weight by lazy { "200" }
    private val animate by lazy { true }

    @Before
    fun setUp() {
        label = returnLabel()
    }

    private fun returnLabel(): Label {
        return Label(
            text = text,
            backgroundColor = backgroundColor,
            color = color,
            weight = weight,
            animate = animate
        )
    }

    @Test
    fun `when getting the value of the label text then return value that was set`() {
        Assert.assertEquals(label.text, text)
    }

    @Test
    fun `when getting the value of the label backgroundColor then return value that was set`() {
        Assert.assertEquals(label.backgroundColor, backgroundColor)
    }

    @Test
    fun `when getting the value of the label color then return value that was set`() {
        Assert.assertEquals(label.color, color)
    }

    @Test
    fun `when getting the value of the label weight then return value that was set`() {
        Assert.assertEquals(label.weight, weight)
    }

    @Test
    fun `when getting the value of the animate weight then return value that was set`() {
        Assert.assertEquals(label.animate, animate)
    }

}