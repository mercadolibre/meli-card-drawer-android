package com.meli.android.carddrawer.configuration

import org.junit.Assert
import org.junit.Test

class CardDrawerStyleTest {

    @Test
    fun `when getting value of CardDrawerStyle REGULAR value then it return 0`() {
        Assert.assertEquals(CardDrawerStyle.REGULAR.value, 0)
    }

    @Test
    fun `when getting value of CardDrawerStyle ACCOUNT_MONEY_HYBRID value then it return 1`() {
        Assert.assertEquals(CardDrawerStyle.ACCOUNT_MONEY_HYBRID.value, 1)
    }

    @Test
    fun `when getting value of CardDrawerStyle ACCOUNT_MONEY_DEFAULT value then it return 2`() {
        Assert.assertEquals(CardDrawerStyle.ACCOUNT_MONEY_DEFAULT.value, 2)
    }

    @Test
    fun `when call function fromValue with 0 per parameter then should return CardDrawerStyle REGULAR`() {
        Assert.assertEquals(CardDrawerStyle.REGULAR.value, 0)
    }

    @Test
    fun `when call function fromValue with 0 per parameter then should return CardDrawerStyle ACCOUNT_MONEY_HYBRID`() {
        Assert.assertEquals(CardDrawerStyle.ACCOUNT_MONEY_HYBRID.value, 1)
    }

    @Test
    fun `when call function fromValue with 0 per parameter then should return CardDrawerStyle ACCOUNT_MONEY_DEFAULT`() {
        Assert.assertEquals(CardDrawerStyle.ACCOUNT_MONEY_DEFAULT.value, 2)
    }

}