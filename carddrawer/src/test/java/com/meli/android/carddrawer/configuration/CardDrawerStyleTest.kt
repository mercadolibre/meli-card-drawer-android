package com.meli.android.carddrawer.configuration

import org.junit.Assert
import org.junit.Test

class CardDrawerStyleTest {

    @Test
    fun `should return values CardDrawerStyle REGULAR`() {
        Assert.assertEquals(CardDrawerStyle.REGULAR.value, 0)
        Assert.assertEquals(CardDrawerStyle.fromValue(0), CardDrawerStyle.REGULAR)
    }

    @Test
    fun `should return values CardDrawerStyle ACCOUNT_MONEY_HYBRID`() {
        Assert.assertEquals(CardDrawerStyle.ACCOUNT_MONEY_HYBRID.value, 1)
        Assert.assertEquals(CardDrawerStyle.fromValue(1), CardDrawerStyle.ACCOUNT_MONEY_HYBRID)
    }

    @Test
    fun `should return CardDrawerStyle ACCOUNT_MONEY_DEFAULT`() {
        Assert.assertEquals(CardDrawerStyle.ACCOUNT_MONEY_DEFAULT.value, 2)
        Assert.assertEquals(CardDrawerStyle.fromValue(2), CardDrawerStyle.ACCOUNT_MONEY_DEFAULT)
    }

    @Test
    fun `should return CardDrawerStyle with invalid value`() {
        Assert.assertEquals(CardDrawerStyle.fromValue(3), CardDrawerStyle.REGULAR)
    }
}