package com.meli.android.carddrawer.internal

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TagDimensionsTest {

    private lateinit var tagDimensions: TagDimensions

    private val fontSize by lazy { 2.3F }
    private val paddingH by lazy { 2 }
    private val paddingV by lazy { 3 }

    @Before
    fun setUp() {
        tagDimensions = TagDimensions(
                                fontSize = fontSize,
                                paddingH = paddingH,
                                paddingV = paddingV)
    }

    @Test
    fun `when getting the value of the tag dimensions fontSize then return value that was set`() {
        Assert.assertEquals(tagDimensions.fontSize, fontSize)
    }

    @Test
    fun `when getting the value of the tag dimensions paddingH then return value that was set`() {
        Assert.assertEquals(tagDimensions.paddingH, paddingH)
    }

    @Test
    fun `when getting the value of the tag dimensions paddingV then return value that was set`() {
        Assert.assertEquals(tagDimensions.paddingV, paddingV)
    }

}