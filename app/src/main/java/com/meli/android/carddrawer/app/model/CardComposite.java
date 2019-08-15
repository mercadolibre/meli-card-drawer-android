package com.meli.android.carddrawer.app.model;

import android.support.annotation.NonNull;
import com.meli.android.carddrawer.model.Card;
import java.util.ArrayList;
import java.util.Collection;

public final class CardComposite extends Card {

    @NonNull private final Collection<Card> cards = new ArrayList<>();

    public void addCard(@NonNull final Card card) {
        cards.add(card);
    }

    @Override
    public void setNumber(@NonNull final String number) {
        for (final Card card : cards) {
            card.setNumber(number);
        }
    }

    @Override
    public void setName(@NonNull final String name) {
        for (final Card card : cards) {
            card.setName(name);
        }
    }

    @Override
    public void setExpiration(@NonNull final String expiration) {
        for (final Card card : cards) {
            card.setExpiration(expiration);
        }
    }

    @Override
    public void setSecCode(@NonNull final String secCode) {
        for (final Card card : cards) {
            card.setSecCode(secCode);
        }
    }
}