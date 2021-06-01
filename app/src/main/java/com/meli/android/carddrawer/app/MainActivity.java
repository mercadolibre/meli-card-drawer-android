package com.meli.android.carddrawer.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.meli.android.carddrawer.app.model.CardComposite;
import com.meli.android.carddrawer.app.model.CustomAccountMoneyConfiguration;
import com.meli.android.carddrawer.app.model.HybridCreditConfiguration;
import com.meli.android.carddrawer.app.model.MasterCardConfiguration;
import com.meli.android.carddrawer.app.model.PixConfiguration;
import com.meli.android.carddrawer.app.model.UrlTestConfiguration;
import com.meli.android.carddrawer.app.model.VisaCardBlueConfiguration;
import com.meli.android.carddrawer.app.model.VisaCardGrayConfiguration;
import com.meli.android.carddrawer.app.model.VisaCardGreenConfiguration;
import com.meli.android.carddrawer.app.model.VisaCardRedConfiguration;
import com.meli.android.carddrawer.app.model.VisaCardYellowConfiguration;
import com.meli.android.carddrawer.configuration.CardDrawerStyle;
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration;
import com.meli.android.carddrawer.model.CardDrawerSource;
import com.meli.android.carddrawer.model.CardDrawerView;
import com.meli.android.carddrawer.model.customview.CardDrawerSwitch;
import com.meli.android.carddrawer.model.customview.SwitchModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* default */ CardComposite card;
    /* default */ CardDrawerView cardDrawerView;
    /* default */ CardDrawerView cardDrawerViewLowRes;
    /* default */ CardDrawerView cardDrawerViewMedium;
    /* default */ CardDrawerView cardDrawerViewMediumRes;
    /* default */ SwitchCompat switchCustomView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_drawer_app_activity_main);

        cardDrawerView = findViewById(R.id.card_header_container);
        cardDrawerViewLowRes = findViewById(R.id.card_header_lowres_container);
        cardDrawerViewMedium = findViewById(R.id.card_header_medium_container);
        cardDrawerViewMediumRes = findViewById(R.id.card_header_mediumres_container);
        switchCustomView = findViewById(R.id.card_drawer_custom_view_switch);

        card = new CardComposite();
        card.addCard(cardDrawerView.getCard());
        card.addCard(cardDrawerViewLowRes.getCard());
        card.addCard(cardDrawerViewMedium.getCard());
        card.addCard(cardDrawerViewMediumRes.getCard());
        ((SwitchCompat) findViewById(R.id.card_header_switch_responsive)).setOnCheckedChangeListener(
            (buttonView, isChecked) -> {
                final int behaviour = isChecked ?
                    CardDrawerView.Behaviour.RESPONSIVE : CardDrawerView.Behaviour.REGULAR;
                cardDrawerView.setBehaviour(behaviour);
                cardDrawerViewLowRes.setBehaviour(behaviour);
                cardDrawerViewMedium.setBehaviour(behaviour);
                cardDrawerViewMediumRes.setBehaviour(behaviour);
            });

        final SwitchCompat switchLowres = findViewById(R.id.card_header_lowres_switch);
        final SwitchCompat switchMedium = findViewById(R.id.card_header_medium_switch);
        final SwitchCompat switchMediumRes = findViewById(R.id.card_header_mediumres_switch);

        switchLowres.setOnCheckedChangeListener(
            (buttonView, isChecked) -> {
                if (isChecked) {
                    switchMedium.setChecked(false);
                    switchMediumRes.setChecked(false);
                    cardDrawerViewLowRes.setVisibility(View.VISIBLE);
                    cardDrawerViewMedium.setVisibility(View.GONE);
                    cardDrawerViewMediumRes.setVisibility(View.GONE);
                    cardDrawerView.setVisibility(View.GONE);
                } else {
                    cardDrawerViewLowRes.setVisibility(View.GONE);
                    if (switchMedium.isChecked()) {
                        cardDrawerViewMedium.setVisibility(View.VISIBLE);
                    } else {
                        if (switchMediumRes.isChecked()) {
                            cardDrawerViewMediumRes.setVisibility(View.VISIBLE);
                        } else {
                            cardDrawerView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

        switchMedium.setOnCheckedChangeListener(
            (buttonView, isChecked) -> {
                if (isChecked) {
                    switchLowres.setChecked(false);
                    switchMediumRes.setChecked(false);
                    cardDrawerViewMedium.setVisibility(View.VISIBLE);
                    cardDrawerView.setVisibility(View.GONE);
                    cardDrawerViewLowRes.setVisibility(View.GONE);
                    cardDrawerViewMediumRes.setVisibility(View.GONE);
                } else {
                    cardDrawerViewMedium.setVisibility(View.GONE);
                    if (switchLowres.isChecked()) {
                        cardDrawerViewLowRes.setVisibility(View.VISIBLE);
                    } else {
                        if (switchMediumRes.isChecked()) {
                            cardDrawerViewMediumRes.setVisibility(View.VISIBLE);
                        } else {
                            cardDrawerView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

        switchMediumRes.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if (isChecked) {
                        switchLowres.setChecked(false);
                        switchMedium.setChecked(false);
                        cardDrawerViewMediumRes.setVisibility(View.VISIBLE);
                        cardDrawerView.setVisibility(View.GONE);
                        cardDrawerViewMedium.setVisibility(View.GONE);
                        cardDrawerViewLowRes.setVisibility(View.GONE);
                    } else {
                        cardDrawerViewMediumRes.setVisibility(View.GONE);
                        if (switchLowres.isChecked()) {
                            cardDrawerViewLowRes.setVisibility(View.VISIBLE);
                        } else {
                            if (switchMedium.isChecked()) {
                                cardDrawerViewMedium.setVisibility(View.VISIBLE);
                            } else {
                                cardDrawerView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        final SwitchCompat switchDisabled = findViewById(R.id.card_header_disabled_switch);
        switchDisabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cardDrawerView.setEnabled(!isChecked);
            cardDrawerViewLowRes.setEnabled(!isChecked);
            cardDrawerViewMedium.setEnabled(!isChecked);
            cardDrawerViewMediumRes.setEnabled(!isChecked);
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
                cardDrawerViewMediumRes.showSecurityCode();
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
                    cardDrawerViewMediumRes.show();
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
        final TextView cardName = findViewById(R.id.card_name);

        cardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                //Do nothing
            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                card.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                //Do nothing
            }
        });

        cardName.setOnClickListener(v -> {
            cardDrawerView.show();
            cardDrawerViewLowRes.show();
            cardDrawerViewMedium.show();
            cardDrawerViewMediumRes.show();
        });
    }

    private void initCardNumber() {
        final TextView cardNumber = findViewById(R.id.card_number);

        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                //Do nothing
            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                card.setNumber(charSequence.toString());
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                //Do nothing
            }
        });

        cardNumber.setOnClickListener(v -> {
            cardDrawerView.show();
            cardDrawerViewLowRes.show();
            cardDrawerViewMedium.show();
            cardDrawerViewMediumRes.show();
        });
    }

    private void initCardConfigurationOptions() {
        final Spinner cardsSpinner = findViewById(R.id.spinner_cards);

        final List<CardConfigurationOption> cardOptions = new ArrayList<>();

        cardOptions.add(new CardConfigurationOption("Default", new DefaultCardConfiguration(this)));
        cardOptions.add(new CardConfigurationOption("Visa blue", new VisaCardBlueConfiguration(this)));
        cardOptions.add(new CardConfigurationOption("Visa green", new VisaCardGreenConfiguration(this)));
        cardOptions.add(new CardConfigurationOption("Visa red", new VisaCardRedConfiguration(this)));
        cardOptions.add(new CardConfigurationOption("Visa gray", new VisaCardGrayConfiguration(this)));
        cardOptions.add(new CardConfigurationOption("Visa yellow", new VisaCardYellowConfiguration(this)));
        cardOptions.add(new CardConfigurationOption("Master", new MasterCardConfiguration(this)));
        cardOptions.add(new CardConfigurationOption("Url Test", new UrlTestConfiguration(this)));
        cardOptions.add(new CardConfigurationOption("Hybrid Account Money", CardDrawerStyle.ACCOUNT_MONEY_HYBRID));
        cardOptions.add(new CardConfigurationOption("Hybrid Credit", new HybridCreditConfiguration()));
        cardOptions.add(new CardConfigurationOption("Default Account Money", CardDrawerStyle.ACCOUNT_MONEY_DEFAULT));
        cardOptions.add(new CardConfigurationOption("Custom account money", new CustomAccountMoneyConfiguration()));
        cardOptions.add(new CardConfigurationOption("PIX", new PixConfiguration(this)));

        final ArrayAdapter<CardConfigurationOption> cardAdapter =
            new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cardOptions);
        cardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardsSpinner.setAdapter(cardAdapter);
        cardsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                final CardConfigurationOption selection = cardOptions.get(position);
                final CardDrawerSource source = selection.getCardConfiguration();
                final CardDrawerStyle style = selection.getCardStyle();
                if (source != null) {
                    cardDrawerView.show(source);
                    cardDrawerViewLowRes.show(source);
                    cardDrawerViewMedium.show(source);
                    cardDrawerViewMediumRes.show(source);
                } else if (style != null) {
                    cardDrawerView.setStyle(style);
                    cardDrawerViewLowRes.setStyle(style);
                    cardDrawerViewMedium.setStyle(style);
                    cardDrawerViewMediumRes.setStyle(style);
                }

                setUpCustomView(style, switchCustomView.isChecked());
                switchCustomView.setOnCheckedChangeListener((buttonView, isChecked) -> setUpCustomView(style, isChecked));
            }

            private void setUpCustomView(@NonNull final CardDrawerStyle style, @NonNull final Boolean isChecked) {
                if (isChecked) {
                    final SwitchModel model;
                    if (style == CardDrawerStyle.ACCOUNT_MONEY_HYBRID) {
                        model = SwitchFactoryModelSample.createModelForAccountMoneyHybrid();
                    } else {
                        model = SwitchFactoryModelSample.createModel();
                    }
                    final CardDrawerSwitch viewHighRes = new CardDrawerSwitch(MainActivity.this);
                    viewHighRes.setSwitchListener(optionId -> Log.i("CARD_DRAWER", "ID: "+ optionId));
                    viewHighRes.setSwitchModel(model);
                    cardDrawerView.setCustomView(viewHighRes);
                    final CardDrawerSwitch viewLowRes = new CardDrawerSwitch(MainActivity.this);
                    viewLowRes.setSwitchListener(optionId -> Log.i("CARD_DRAWER", "ID: "+ optionId));
                    viewLowRes.setSwitchModel(model);
                    viewLowRes.setConfiguration(cardDrawerViewLowRes.buildCustomViewConfiguration());
                    cardDrawerViewLowRes.setCustomView(viewLowRes);
                    final CardDrawerSwitch viewMediumRes = new CardDrawerSwitch(MainActivity.this);
                    viewMediumRes.setSwitchListener(optionId -> Log.i("CARD_DRAWER", "ID: "+ optionId));
                    viewMediumRes.setSwitchModel(model);
                    viewMediumRes.setConfiguration(cardDrawerViewMediumRes.buildCustomViewConfiguration());
                    cardDrawerViewMediumRes.setCustomView(viewMediumRes);
                } else {
                    cardDrawerView.setCustomView(null);
                    cardDrawerViewLowRes.setCustomView(null);
                    cardDrawerViewMediumRes.setCustomView(null);
                }
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                // do nothing
            }
        });
    }
}