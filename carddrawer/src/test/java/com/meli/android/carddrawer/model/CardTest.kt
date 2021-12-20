package com.meli.android.carddrawer.model

import android.os.Parcel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class CardTest {

    private lateinit var card: Card
    private val cardName by lazy { "test" }
    private val cardExpiration by lazy { "10/22" }
    private val cardSecCode by lazy { "888" }
    private val cardNumber by lazy { "1234567891" }

    @Before
    fun init() {
        card = returnCard()
    }

    private fun returnCard(): Card {
         return Card().apply {
            this.name = cardName
            this.expiration = cardExpiration
            this.secCode = cardSecCode
            this.number = cardNumber
        }
    }

    @Test
    fun `Should test get and set functions`() {
        Assert.assertEquals(card.name, cardName)
        Assert.assertEquals(card.expiration, cardExpiration)
        Assert.assertEquals(card.secCode, cardSecCode)
        Assert.assertEquals(card.number, cardNumber)
    }

    @Test
    fun `Should test functions fillCard`() {
        val cardFill = Card()
        cardFill.fillCard(card)
        Assert.assertEquals(cardFill.name, cardName)
        Assert.assertEquals(cardFill.expiration, cardExpiration)
        Assert.assertEquals(cardFill.secCode, cardSecCode)
        Assert.assertEquals(cardFill.number, cardNumber)
    }

    @Test
    fun `Should return describeContents`(){
        val card = Card()
        Assert.assertEquals(card.describeContents(), 0)
    }

    @Test
    fun `Should test constructor with Parcel`() {
        val parcelMock = mock<Parcel>()
        val card = Card(parcelMock)
        Assert.assertNotNull(card)
    }

    @Test
    fun `Should test writeToPercel`() {
        val parcelMock = mock<Parcel>()
        card.writeToParcel(parcelMock, 0)
        Assert.assertEquals(card.name, cardName)
        Assert.assertEquals(card.expiration, cardExpiration)
        Assert.assertEquals(card.secCode, cardSecCode)
        Assert.assertEquals(card.number, cardNumber)
    }

    @Test
    fun `Should test function createFromParcel`() {
        val parcelMock = mock<Parcel>()
        val card = Card.CREATOR.createFromParcel(parcelMock)
        assert(card is Card)
    }

    @Test
    fun `Should test function newArray`() {
        val card = Card.CREATOR.newArray(3)
        assert(card is Array<Card>)
    }

}