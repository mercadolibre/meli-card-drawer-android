package com.meli.android.carddrawer.configuration

import org.junit.Assert
import org.junit.Test

class CardDrawerStyleTest {

    @Test
    fun `when getting value of CardDrawerStyle REGULAR value then return 0`() {
        Assert.assertEquals(CardDrawerStyle.REGULAR.value, 0)
    }

    @Test
    fun `when getting value of CardDrawerStyle ACCOUNT_MONEY_HYBRID value then return 1`() {
        Assert.assertEquals(CardDrawerStyle.ACCOUNT_MONEY_HYBRID.value, 1)
    }

    @Test
    fun `when getting value of CardDrawerStyle ACCOUNT_MONEY_DEFAULT value then return 2`() {
        Assert.assertEquals(CardDrawerStyle.ACCOUNT_MONEY_DEFAULT.value, 2)
    }

    @Test
    fun `when call function fromValue with 0 per parameter then return CardDrawerStyle REGULAR`() {
        Assert.assertEquals(CardDrawerStyle.fromValue(0), CardDrawerStyle.REGULAR)
    }

    @Test
    fun `when call function fromValue with 1 per parameter then return CardDrawerStyle ACCOUNT_MONEY_HYBRID`() {
        Assert.assertEquals(CardDrawerStyle.fromValue(1), CardDrawerStyle.ACCOUNT_MONEY_HYBRID)
    }

    @Test
    fun `when call function fromValue with 2 per parameter then return CardDrawerStyle ACCOUNT_MONEY_DEFAULT`() {
        Assert.assertEquals(CardDrawerStyle.fromValue(2), CardDrawerStyle.ACCOUNT_MONEY_DEFAULT)
    }

    @Test
    fun `when call function fromValue with 3 per parameter then return CardDrawerStyle REGULAR`() {
        Assert.assertEquals(CardDrawerStyle.fromValue(3), CardDrawerStyle.REGULAR)
    }

}