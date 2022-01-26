package com.meli.android.carddrawer.internal

import com.meli.android.carddrawer.model.CardUI
import com.meli.android.carddrawer.model.GenericPaymentMethod
import com.meli.android.carddrawer.model.PaymentCard
import io.mockk.mockkClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BaseExtensionsTest {

    private lateinit var text: GenericPaymentMethod.Text

    private val genericPaymentMethod by lazy {
        GenericPaymentMethod(0, text, "", text)
    }

    private val paymentCard by lazy {
        PaymentCard(mockkClass(CardUI::class, relaxed = true))
    }

    private var paymentCardBlockIsCalled: Boolean = false
    private var genericPaymentMethodIsCalled: Boolean = false

    @Before
    fun setUp() {
        text = GenericPaymentMethod.Text("Text", 0)
    }

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
    fun `when call function processPaymentCard with genericPaymentMethod then don't execute function passed by parameter`() {
        genericPaymentMethod.processPaymentCard {
            callGenericPaymentMethodIsCalled()
        }
        Assert.assertFalse(paymentCardBlockIsCalled)
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

    @Test
    fun `when call function processGenericPaymentMethod with paymentCard then don't execute function passed by parameter`() {
        paymentCard.processGenericPaymentMethod {
            callPaymentCardBlock()
        }
        Assert.assertFalse(genericPaymentMethodIsCalled)
        Assert.assertFalse(paymentCardBlockIsCalled)
    }

    @Test
    fun `when call either then return first CardUI and second null`() {
        val paymentCard = paymentCard.either()
        assert(paymentCard.first is CardUI)
        Assert.assertEquals(paymentCard.second, null)
    }

    @Test
    fun `when call either then return first null and second GenericPaymentMethod`() {
        val genericPayment = genericPaymentMethod.either()
        Assert.assertEquals(genericPayment.first, null)
        assert(genericPayment.second is GenericPaymentMethod)
    }

    private fun callPaymentCardBlock() {
        paymentCardBlockIsCalled = true
    }

    private fun callGenericPaymentMethodIsCalled() {
        genericPaymentMethodIsCalled = true
    }

}