package com.meli.android.carddrawer.model;

import com.meli.android.carddrawer.TestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class CardTest {
    @Test
    public void newCard_worksOk() {
        Card card = new Card();
        card.setName("Test Test");
        card.setExpiration("10/19");
        card.setSecCode("888");
        card.setNumber("777777777");

        assertEquals("Test Test", card.getName());
        assertEquals("777777777", card.getNumber());
        assertEquals("10/19", card.getExpiration());
        assertEquals(card.getSecCode(), card.getSecCode());
    }

    @Test
    public void newParcelable_worksOK() {
        Card card = new Card();
        card.setNumber("123456789");
        card.setName("Visa");
        card.setSecCode("444");
        card.setExpiration("10/19");
        Card cardClone = TestUtils.cloneParcelable(card, Card.CREATOR);
        assertNotNull(cardClone);
        assertEquals(card.getNumber(), cardClone.getNumber());
        assertEquals(card.getName(), cardClone.getName());
        assertEquals(card.getSecCode(), cardClone.getSecCode());
        assertEquals(card.getExpiration(), cardClone.getExpiration());
    }

    @Test
    public void fillCard_setsValues() {
        Card card = new Card();
        card.setNumber("123456789");
        card.setName("Visa");
        card.setSecCode("444");
        card.setExpiration("10/19");

        Card anotherCard = new Card();
        anotherCard.fillCard(card);

        assertEquals(card.getNumber(), anotherCard.getNumber());
        assertEquals(card.getName(), anotherCard.getName());
        assertEquals(card.getSecCode(), anotherCard.getSecCode());
        assertEquals(card.getExpiration(), anotherCard.getExpiration());
    }
}
