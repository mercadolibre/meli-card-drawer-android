package com.meli.android.carddrawer.model

import android.os.Parcel
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardTest {

    private lateinit var card: Card
    private val cardName by lazy { "test" }
    private val cardExpiration by lazy { "10/22" }
    private val cardSecCode by lazy { "888" }
    private val cardNumber by lazy { "1234567891" }

    @Before
    fun setUp() {
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
    fun `when getting the value of the card name then return value that was set`() {
        Assert.assertEquals(card.name, cardName)
    }

    @Test
    fun `when getting the value of the card expiration then return value that was set`() {
        Assert.assertEquals(card.expiration, cardExpiration)
    }

    @Test
    fun `when getting the value of the card secCode then return value that was set`() {
        Assert.assertEquals(card.secCode, cardSecCode)
    }

    @Test
    fun `when getting the value of the card number then return value that was set`() {
        Assert.assertEquals(card.number, cardNumber)
    }

    @Test
    fun `when call function fillCard then return the card name`() {
        val cardFill = Card()
        cardFill.fillCard(card)
        Assert.assertEquals(card.name, cardName)
    }

    @Test
    fun `when call function fillCard then return the card expiration`() {
        val cardFill = Card()
        cardFill.fillCard(card)
        Assert.assertEquals(card.expiration, cardExpiration)
    }

    @Test
    fun `when call function fillCard then return the card secCode`() {
        val cardFill = Card()
        cardFill.fillCard(card)
        Assert.assertEquals(card.secCode, cardSecCode)
    }

    @Test
    fun `when call function fillCard then return the card number`() {
        val cardFill = Card()
        cardFill.fillCard(card)
        Assert.assertEquals(card.number, cardNumber)
    }

    @Test
    fun `when getting describeContents then return 0`() {
        val card = Card()
        Assert.assertEquals(card.describeContents(), 0)
    }

    @Test
    fun `when pass Parcel per parameter in constructor then return Card`() {
        val parcelMock = mockk<Parcel>(relaxed = true)
        val card = Card(parcelMock)
        Assert.assertEquals(card.name, "")
        Assert.assertEquals(card.number, "")
        Assert.assertEquals(card.expiration, "")
        Assert.assertEquals(card.secCode, "")
    }

    @Test
    fun `when call function writeParcel then return object Card`() {
        val parcelMock = mockk<Parcel>(relaxed = true)
        card.writeToParcel(parcelMock, 0)
        Assert.assertEquals(card.name, cardName)
        Assert.assertEquals(card.expiration, cardExpiration)
        Assert.assertEquals(card.secCode, cardSecCode)
        Assert.assertEquals(card.number, cardNumber)
    }

    @Test
    fun `when call function createFromParcel then return object Card`() {
        val parcelMock = mockk<Parcel>(relaxed = true)
        val card = Card.CREATOR.createFromParcel(parcelMock)
        assert(card is Card)
    }

    @Test
    fun `when call function newArray then return an array object Card`() {
        val card = Card.CREATOR.newArray(3)
        assert(card is Array<Card>)
        Assert.assertEquals(card.size, 3)
    }

}