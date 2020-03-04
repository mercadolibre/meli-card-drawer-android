package com.meli.android.carddrawer.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import com.meli.android.carddrawer.app.model.CardComposite;
import com.meli.android.carddrawer.app.model.MasterCardConfiguration;
import com.meli.android.carddrawer.app.model.UrlTestConfiguration;
import com.meli.android.carddrawer.app.model.VisaCardBlueConfiguration;
import com.meli.android.carddrawer.app.model.VisaCardGrayConfiguration;
import com.meli.android.carddrawer.app.model.VisaCardGreenConfiguration;
import com.meli.android.carddrawer.app.model.VisaCardRedConfiguration;
import com.meli.android.carddrawer.app.model.VisaCardYellowConfiguration;
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration;
import com.meli.android.carddrawer.model.CardDrawerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* default */ CardComposite card;
    /* default */ CardDrawerView cardDrawerView;
    /* default */ CardDrawerView cardDrawerViewLowRes;
    /* default */ CardDrawerView cardDrawerViewMedium;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_drawer_app_activity_main);

        cardDrawerView = findViewById(R.id.card_header_container);
        cardDrawerViewLowRes = findViewById(R.id.card_header_lowres_container);
        cardDrawerViewMedium = findViewById(R.id.card_header_medium_container);
        card = new CardComposite();
        card.addCard(cardDrawerView.getCard());
        card.addCard(cardDrawerViewLowRes.getCard());
        card.addCard(cardDrawerViewMedium.getCard());
        ((Switch) findViewById(R.id.card_header_switch_responsive)).setOnCheckedChangeListener(
            (buttonView, isChecked) -> {
                final int behaviour = isChecked ?
                    CardDrawerView.Behaviour.RESPONSIVE : CardDrawerView.Behaviour.REGULAR;
                cardDrawerView.setBehaviour(behaviour);
                cardDrawerViewLowRes.setBehaviour(behaviour);
                cardDrawerViewMedium.setBehaviour(behaviour);
            });
        final Switch switchLowres = findViewById(R.id.card_header_lowres_switch);
        final Switch switchMedium = findViewById(R.id.card_header_medium_switch);
        switchLowres.setOnCheckedChangeListener(
            (buttonView, isChecked) -> {
                if (isChecked) {
                    switchMedium.setChecked(false);
                    cardDrawerViewLowRes.setVisibility(View.VISIBLE);
                    cardDrawerViewMedium.setVisibility(View.GONE);
                    cardDrawerView.setVisibility(View.GONE);
                } else {
                    cardDrawerViewLowRes.setVisibility(View.GONE);
                    if (switchMedium.isChecked()) {
                        cardDrawerViewMedium.setVisibility(View.VISIBLE);
                    } else {
                        cardDrawerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        switchMedium.setOnCheckedChangeListener(
            (buttonView, isChecked) -> {
                if (isChecked) {
                    switchLowres.setChecked(false);
                    cardDrawerViewMedium.setVisibility(View.VISIBLE);
                    cardDrawerView.setVisibility(View.GONE);
                    cardDrawerViewLowRes.setVisibility(View.GONE);
                } else {
                    cardDrawerViewMedium.setVisibility(View.GONE);
                    if (switchLowres.isChecked()) {
                        cardDrawerViewLowRes.setVisibility(View.VISIBLE);
                    } else {
                        cardDrawerView.setVisibility(View.VISIBLE);
                    }
                }
            });

        initCardConfigurationOptions();
        initCardNumber();
        initCardName();
        initExpirationDate();
        initSecurityCode();
    }

    private void initSecurityCode() {
        final TextView securityCode = findViewById(R.id.security_code);

        securityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                cardDrawerView.showSecurityCode();
                cardDrawerViewLowRes.showSecurityCode();
                cardDrawerViewMedium.showSecurityCode();
            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                card.setSecCode(charSequence.toString());
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (editable.toString().isEmpty()) {
                    cardDrawerView.show();
                    cardDrawerViewLowRes.show();
                    cardDrawerViewMedium.show();
                }
            }
        });
    }

    private void initExpirationDate() {
        final TextView expirationDate = findViewById(R.id.expiration_date);

        expirationDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                //Do nothing
            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                card.setExpiration(charSequence.toString());
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                //Do nothing
            }
        });
    }

    private void initCardName() {
        TextView cardName = findViewById(R.id.card_name);

        cardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                card.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Do nothing
            }
        });

        cardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDrawerView.show();
                cardDrawerViewLowRes.show();
                cardDrawerViewMedium.show();
            }
        });
    }

    private void initCardNumber() {
        TextView cardNumber = findViewById(R.id.card_number);

        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                card.setNumber(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Do nothing
            }
        });

        cardNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDrawerView.show();
                cardDrawerViewLowRes.show();
                cardDrawerViewMedium.show();
            }
        });
    }

    private void initCardConfigurationOptions() {
        Spinner cardsSpinner = findViewById(R.id.spinner_cards);

        final List<CardConfigurationOption> cardOptions = new ArrayList<>();

        DefaultCardConfiguration defaultCardConfiguration = new DefaultCardConfiguration(this);
        cardOptions.add(new CardConfigurationOption(defaultCardConfiguration, "Default"));

        VisaCardBlueConfiguration visaCardBlueConfiguration = new VisaCardBlueConfiguration(this);
        cardOptions.add(new CardConfigurationOption(visaCardBlueConfiguration, "Visa blue"));

        VisaCardGreenConfiguration visaCardGreenConfiguration = new VisaCardGreenConfiguration(this);
        cardOptions.add(new CardConfigurationOption(visaCardGreenConfiguration, "Visa green"));

        VisaCardRedConfiguration visaCardRedConfiguration = new VisaCardRedConfiguration(this);
        cardOptions.add(new CardConfigurationOption(visaCardRedConfiguration, "Visa red"));

        VisaCardGrayConfiguration visaCardGrayConfiguration = new VisaCardGrayConfiguration(this);
        cardOptions.add(new CardConfigurationOption(visaCardGrayConfiguration, "Visa gray"));

        VisaCardYellowConfiguration visaCardYellowConfiguration = new VisaCardYellowConfiguration(this);
        cardOptions.add(new CardConfigurationOption(visaCardYellowConfiguration, "Visa yellow"));

        MasterCardConfiguration masterCardConfiguration = new MasterCardConfiguration(this);
        cardOptions.add(new CardConfigurationOption(masterCardConfiguration, "Master"));

        final UrlTestConfiguration urlTestConfiguration = new UrlTestConfiguration(this);
        cardOptions.add(new CardConfigurationOption(urlTestConfiguration, "Url Test"));

        ArrayAdapter<CardConfigurationOption> cardAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cardOptions);
        cardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardsSpinner.setAdapter(cardAdapter);
        cardsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CardConfigurationOption selection = cardOptions.get(position);
                cardDrawerView.show(selection.getCardConfiguration());
                cardDrawerViewLowRes.show(selection.getCardConfiguration());
                cardDrawerViewMedium.show(selection.getCardConfiguration());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }
}