package com.meli.android.carddrawer.configuration

import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.BaseTest
import com.meli.android.carddrawer.model.CardAnimationType
import io.mockk.every
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultCardTest: BaseTest() {

    private lateinit var defaultCardConfiguration: DefaultCardConfiguration

    @Before
    override fun setUp() {
        super.setUp()
        every { contextMock.applicationContext } returns contextMock
        defaultCardConfiguration = DefaultCardConfiguration(contextMock)
    }

    @Test
    fun `when getting card number pattern size then return 4`() {
        assertEquals(defaultCardConfiguration.cardNumberPattern.size.toLong(), 4)
    }

    @Test
    fun `when getting placeholder name then return 'Nombre y Apellido'`() {
        val text = "Nombre y Apellido"
        every { contextMock.getString(R.string.card_drawer_card_hint_name) } returns text
        assertEquals(defaultCardConfiguration.namePlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_name))
    }

    @Test
    fun `when getting expiration placeholder then return 'MM-AA'`() {
        val text = "MM/AA"
        every { contextMock.getString(R.string.card_drawer_card_hint_date) } returns text
        assertEquals(defaultCardConfiguration.expirationPlaceHolder, text)
    }

    @Test
    fun `when getting fontType then return FontType DARK_TYPE`() {
        assertEquals(defaultCardConfiguration.fontType, FontType.DARK_TYPE)
    }

    @Test
    fun `when getting animationType then return CardAnimationType RIGHT_BOTTOM`() {
        assertEquals(defaultCardConfiguration.animationType, CardAnimationType.RIGHT_BOTTOM)
    }

    @Test
    fun `when getting bank image res then return 0`() {
        assertEquals(defaultCardConfiguration.bankImageRes, 0)
    }

    @Test
    fun `when getting card logo image res then return 0`() {
        assertEquals(defaultCardConfiguration.cardLogoImageRes, 0)
    }

    @Test
    fun `when getting securityCodeLocation then return SecurityCodeLocation BLACK`() {
        assertEquals(defaultCardConfiguration.securityCodeLocation, SecurityCodeLocation.BACK)
    }

    @Test
    fun `when getting card font color then return card_drawer_card_default_font_color`() {
        every { contextMock.getColor(R.color.card_drawer_card_default_font_color) } returns -2763307
        val fontColor = ContextCompat.getColor(contextMock, R.color.card_drawer_card_default_font_color)
        assertEquals(defaultCardConfiguration.cardFontColor, fontColor)
    }

    @Test
    fun `when getting default card configuration then return card_drawer_card_default_color`() {
        every { contextMock.getColor(R.color.card_drawer_card_default_color) } returns -10066330
        val backgroundColor = ContextCompat.getColor(contextMock, R.color.card_drawer_card_default_color)
        assertEquals(defaultCardConfiguration.cardBackgroundColor, backgroundColor)
    }

    @Test
    fun `when getting security code pattern then return 4`() {
        assertEquals(defaultCardConfiguration.securityCodePattern, 4)
    }

}