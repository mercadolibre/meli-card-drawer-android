package com.meli.android.carddrawer.app.model

import android.graphics.Color
import com.meli.android.carddrawer.app.BaseTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PixConfigurationTest: BaseTest() {

    private lateinit var pixConfiguration: PixConfiguration

    @Before
    override fun setUp() {
        super.setUp()
        pixConfiguration = PixConfiguration(contextMock)
    }

    @Test
    fun `when getting backgroundColor then return WHITE`() {
        Assert.assertEquals(pixConfiguration.backgroundColor, Color.WHITE)
    }

    @Test
    fun `when getting title then return text PIX`() {
        Assert.assertEquals(pixConfiguration.title.text, "PIX")
    }

    @Test
    fun `when getting title then return color 0`() {
        Assert.assertEquals(pixConfiguration.title.color, 0)
    }

    @Test
    fun `when getting imageUrl then return mercadolibre link`() {
        val result: Boolean = pixConfiguration.imageUrl?.contains("https://mobile.mercadolibre.com") ?: false
        Assert.assertTrue(result)
    }

    @Test
    fun `when getting subtitle then return text 'Aprovacao Imediata'`() {
        Assert.assertEquals(pixConfiguration.subtitle?.text, "Aprovação Imediata")
    }

    @Test
    fun `when getting subtitle then return color 0`() {
        Assert.assertEquals(pixConfiguration.subtitle?.color, 0)
    }

    @Test
    fun `when getting tag then return text 'Novo'`() {
        Assert.assertEquals(pixConfiguration.tag?.text, "Novo")
    }

    @Test
    fun `when getting tag then return backgroundColor 0`() {
        Assert.assertEquals(pixConfiguration.tag?.backgroundColor, 0)
    }

    @Test
    fun `when getting tag then return textColor 0`() {
        Assert.assertEquals(pixConfiguration.tag?.textColor, 0)
    }

    @Test
    fun `when getting tag then return weight 'semi_bold'`() {
        Assert.assertEquals(pixConfiguration.tag?.weight, "semi_bold")
    }
}