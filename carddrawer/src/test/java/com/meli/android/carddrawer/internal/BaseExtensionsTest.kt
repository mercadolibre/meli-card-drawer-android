package com.meli.android.carddrawer.internal

import com.meli.android.carddrawer.model.GenericPaymentMethod
import com.meli.android.carddrawer.model.PaymentCard
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class BaseExtensionsTest {

    private val genericPaymentMethod by lazy {
        GenericPaymentMethod(0, mockk(), "", mockk())
    }

    private val paymentCard by lazy {
        PaymentCard(mockk(relaxed = true))
    }

    private var paymentCardBlockIsCalled: Boolean = false
    private var genericPaymentMethodIsCalled: Boolean = false

    @Test
    fun `when call function process with generic payment then execute function passed by parameter`() {
        genericPaymentMethod.process({
            callGenericPaymentMethodIsCalled()
        })
        Assert.assertTrue(genericPaymentMethodIsCalled)
        Assert.assertFalse(paymentCardBlockIsCalled)
    }

    @Test
    fun `when call function process with payment card then execute function passed by parameter`() {
        paymentCard.process({
        }, {
            callPaymentCardBlock()
        })
        Assert.assertTrue(paymentCardBlockIsCalled)
        Assert.assertFalse(genericPaymentMethodIsCalled)
    }

    @Test
    fun `when call function processPaymentCard then execute function passed by parameter`() {
        paymentCard.processPaymentCard {
            callPaymentCardBlock()
        }
        Assert.assertTrue(paymentCardBlockIsCalled)
        Assert.assertFalse(genericPaymentMethodIsCalled)
    }

    @Test
    fun `when call function processGenericPaymentMethod then execute function passed by parameter`() {
        genericPaymentMethod.processGenericPaymentMethod {
            callGenericPaymentMethodIsCalled()
        }
        Assert.assertTrue(genericPaymentMethodIsCalled)
        Assert.assertFalse(paymentCardBlockIsCalled)
    }

    fun callPaymentCardBlock() {
        paymentCardBlockIsCalled = true
    }

    fun callGenericPaymentMethodIsCalled() {
        genericPaymentMethodIsCalled = true
    }

}