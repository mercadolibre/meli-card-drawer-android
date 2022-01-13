package com.meli.android.carddrawer.model.customView

import com.meli.android.carddrawer.configuration.CardDrawerStyle
import com.meli.android.carddrawer.model.CardDrawerView
import com.meli.android.carddrawer.model.customview.CustomViewConfiguration
import org.junit.Assert
import org.junit.Test

class CustomViewConfigurationTest {

    private lateinit var customViewConfiguration: CustomViewConfiguration

    @Test
    fun `when set card drawer type HIGH in constructor then return the same value`() {
        customViewConfiguration = CustomViewConfiguration(
                                    cardDrawerType = CardDrawerView.Type.HIGH,
                                    cardDrawerStyle = null)
        Assert.assertEquals(customViewConfiguration.cardDrawerType, CardDrawerView.Type.HIGH)
    }

    @Test
    fun `when set card drawer type LOW in constructor then return the same value`() {
        customViewConfiguration = CustomViewConfiguration(
            cardDrawerType = CardDrawerView.Type.LOW,
            cardDrawerStyle = null)
        Assert.assertEquals(customViewConfiguration.cardDrawerType, CardDrawerView.Type.LOW)
    }

    @Test
    fun `when set card drawer type MEDIUM in constructor then return the same value`() {
        customViewConfiguration = CustomViewConfiguration(
            cardDrawerType = CardDrawerView.Type.MEDIUM,
            cardDrawerStyle = null)
        Assert.assertEquals(customViewConfiguration.cardDrawerType, CardDrawerView.Type.MEDIUM)
    }

    @Test
    fun `when set card drawer style REGULAR in constructor then return the same value`() {
        customViewConfiguration = CustomViewConfiguration(
            cardDrawerType = CardDrawerView.Type.LOW,
            cardDrawerStyle = CardDrawerStyle.REGULAR)
        Assert.assertEquals(customViewConfiguration.cardDrawerStyle, CardDrawerStyle.REGULAR)
    }

    @Test
    fun `when set card drawer style ACCOUNT_MONEY_HYBRID in constructor then return the same value`() {
        customViewConfiguration = CustomViewConfiguration(
            cardDrawerType = CardDrawerView.Type.LOW,
            cardDrawerStyle = CardDrawerStyle.ACCOUNT_MONEY_HYBRID)
        Assert.assertEquals(customViewConfiguration.cardDrawerStyle, CardDrawerStyle.ACCOUNT_MONEY_HYBRID)
    }

    @Test
    fun `when set card drawer style ACCOUNT_MONEY_DEFAULT in constructor then return the same value`() {
        customViewConfiguration = CustomViewConfiguration(
            cardDrawerType = CardDrawerView.Type.LOW,
            cardDrawerStyle = CardDrawerStyle.ACCOUNT_MONEY_DEFAULT)
        Assert.assertEquals(customViewConfiguration.cardDrawerStyle, CardDrawerStyle.ACCOUNT_MONEY_DEFAULT)
    }

}