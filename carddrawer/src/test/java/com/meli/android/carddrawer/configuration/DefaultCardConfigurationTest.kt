package com.meli.android.carddrawer.configuration

import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.base.ConfigurationTestBase
import com.meli.android.carddrawer.model.CardAnimationType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultCardConfigurationTest: ConfigurationTestBase() {

    private lateinit var defaultCardConfiguration: DefaultCardConfiguration

    @Before
    override fun init() {
        super.init()
        Mockito.`when`(contextMock.applicationContext).thenReturn(contextMock)
        defaultCardConfiguration = DefaultCardConfiguration(contextMock)
    }

    @Test
    fun `should return size pattern card number`() {
        assertEquals(defaultCardConfiguration.cardNumberPattern.size.toLong(), 4)
    }

    @Test
    fun `should return placeholder name`() {
        val text = "Nombre y Apellido"
        Mockito.`when`(contextMock.getString(R.string.card_drawer_card_hint_name)).thenReturn(text)
        assertEquals(defaultCardConfiguration.namePlaceHolder, text)
    }

    @Test
    fun `should return placeholder date expired`() {
        val text = "MM/AA"
        Mockito.`when`(contextMock.getString(R.string.card_drawer_card_hint_date)).thenReturn(text)
        assertEquals(defaultCardConfiguration.expirationPlaceHolder, text)
    }

    @Test
    fun `should return FontType`() {
        assertEquals(defaultCardConfiguration.fontType, FontType.DARK_TYPE)
    }

    @Test
    fun `should return CardAnimationType`() {
        assertEquals(defaultCardConfiguration.animationType, CardAnimationType.RIGHT_BOTTOM)
    }

    @Test
    fun `should return BankImageRes`() {
        assertEquals(defaultCardConfiguration.bankImageRes, 0)
    }

    @Test
    fun `should return CardLogoImageRes`() {
        assertEquals(defaultCardConfiguration.cardLogoImageRes, 0)
    }

    @Test
    fun `should return SecurityCodeLocation`() {
        assertEquals(defaultCardConfiguration.securityCodeLocation, SecurityCodeLocation.BACK)
    }

    @Test
    fun `should return CardFontColor`() {
        Mockito.`when`(ContextCompat.getColor(contextMock, R.color.card_drawer_card_default_font_color)).thenReturn(-2763307)
        val fontColor = ContextCompat.getColor(contextMock, R.color.card_drawer_card_default_font_color)
        assertEquals(defaultCardConfiguration.cardFontColor, fontColor)
    }

    @Test
    fun `should return CardBackgroundColor`() {
        Mockito.`when`(ContextCompat.getColor(contextMock, R.color.card_drawer_card_default_color)).thenReturn(-10066330)
        val backgroundColor = ContextCompat.getColor(contextMock, R.color.card_drawer_card_default_color)
        assertEquals(defaultCardConfiguration.cardBackgroundColor, backgroundColor)
    }

    @Test
    fun `should return SecurityCodePattern`() {
        assertEquals(defaultCardConfiguration.securityCodePattern, 4)
    }

}