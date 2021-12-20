package com.meli.android.carddrawer.model

import org.junit.Assert
import org.junit.Test

class LabelTest {

    @Test
    fun `Should test get and set functions`() {
        val text = "test"
        val backgroundColor = "#FFFFFF"
        val color = "#000000"
        val weight = "200"
        val animate = true

        val label = Label(
            text = text,
            backgroundColor = backgroundColor,
            color = color,
            weight = weight,
            animate = animate
        )

        Assert.assertEquals(label.text, text)
        Assert.assertEquals(label.backgroundColor, backgroundColor)
        Assert.assertEquals(label.color, color)
        Assert.assertEquals(label.weight, weight)
        Assert.assertEquals(label.animate, animate)
    }

}