package com.meli.android.carddrawer.app.model

import com.meli.android.carddrawer.app.TestUtils.getDeclaredField
import com.meli.android.carddrawer.model.Card
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardCompositeTest {

    private lateinit var cardComposite: CardComposite

    private val card: Card by lazy {
        Card().apply {
            this.name = "test"
            this.expiration = "01/30"
            this.secCode = "012"
            this.number = "12345"
        }
    }

    @Before
    fun setUp() {
        cardComposite = CardComposite()
        cardComposite.addCard(card)
    }

    @Test
    fun `when call function addCard then should add cards to the list`() {
        cardComposite.addCard(card)
        val cards = cardComposite.getDeclaredField("cards") as ArrayList<*>
        Assert.assertEquals(cards.size, 2)
    }

    @Test
    fun `when set number of Card then return the value was set`() {
        cardComposite.number = "0000"
        Assert.assertEquals(card.number, "0000")
    }

    @Test
    fun `when set name of Card then return the value was set`() {
        cardComposite.name = "Mercado Livre"
        Assert.assertEquals(card.name, "Mercado Livre")
    }

    @Test
    fun `when set expiration of Card then return the value was set`() {
        cardComposite.expiration = "12/29"
        Assert.assertEquals(card.expiration, "12/29")
    }

    @Test
    fun `when set code of Card then return the value was set`() {
        cardComposite.secCode = "999"
        Assert.assertEquals(card.secCode, "999")
    }

}