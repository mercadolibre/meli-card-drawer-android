package com.meli.android.carddrawer.configuration

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AccountMoneyHybridConfigurationTest {

    private lateinit var accountMoneyHybridConfiguration: AccountMoneyHybridConfiguration

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        accountMoneyHybridConfiguration = AccountMoneyHybridConfiguration()
    }

    @Test
    fun `should return BankImageRes`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.bankImageRes, 0)
    }

}
