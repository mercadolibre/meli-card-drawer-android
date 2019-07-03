package com.meli.android.carddrawer.app;

import com.meli.android.carddrawer.model.CardUI;

/**
 * Created by rcortazzo on 28/08/2018.
 */
@SuppressWarnings("PMD.DefaultPackage")
public class CardConfigurationOption {

    final CardUI cardConfiguration;
    final String cardName;

    public CardConfigurationOption(CardUI cardConfiguration, String cardName) {
        this.cardConfiguration = cardConfiguration;
        this.cardName = cardName;
    }

    public CardUI getCardConfiguration() {
        return cardConfiguration;
    }

    @Override
    public String toString() {
        return cardName;
    }
}
