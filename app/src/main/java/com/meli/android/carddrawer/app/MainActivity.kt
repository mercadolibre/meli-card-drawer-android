package com.meli.android.carddrawer.app

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.meli.android.carddrawer.app.SwitchFactoryModelSample.createModel
import com.meli.android.carddrawer.app.SwitchFactoryModelSample.createModelForAccountMoneyHybrid
import com.meli.android.carddrawer.app.model.CardComposite
import com.meli.android.carddrawer.app.model.CustomAccountMoneyConfiguration
import com.meli.android.carddrawer.app.model.HybridCreditConfiguration
import com.meli.android.carddrawer.app.model.MasterCardConfiguration
import com.meli.android.carddrawer.app.model.PixConfiguration
import com.meli.android.carddrawer.app.model.UrlTestConfiguration
import com.meli.android.carddrawer.app.model.VisaCardBlueConfiguration
import com.meli.android.carddrawer.app.model.VisaCardGrayConfiguration
import com.meli.android.carddrawer.app.model.VisaCardGreenConfiguration
import com.meli.android.carddrawer.app.model.VisaCardRedConfiguration
import com.meli.android.carddrawer.app.model.VisaCardYellowConfiguration
import com.meli.android.carddrawer.configuration.CardDrawerStyle
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration
import com.meli.android.carddrawer.model.CardDrawerSource
import com.meli.android.carddrawer.model.CardDrawerView
import com.meli.android.carddrawer.model.Label
import com.meli.android.carddrawer.model.customview.CardDrawerSwitch

class MainActivity : AppCompatActivity() {

    private lateinit var switchCustomView: SwitchCompat
    private lateinit var switchTag: SwitchCompat
    private val card = CardComposite()
    private val cardViews = mutableListOf<CardDrawerViewOption>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_drawer_app_activity_main)

        addCard("High", findViewById(R.id.card_header_container))
        addCard("Lowres", findViewById(R.id.card_header_lowres_container))
        addCard("Medium", findViewById(R.id.card_header_medium_container))
        addCard("MediumRes", findViewById(R.id.card_header_mediumres_container))
        addCard("Small", findViewById(R.id.card_header_small_container))

        initSizesDropDown()

        switchTag = findViewById(R.id.card_header_show_tag_switch)
        switchTag.setOnCheckedChangeListener { _, _ ->
            updateCardTagVisibility()
        }

        switchCustomView = findViewById(R.id.card_drawer_custom_view_switch)
        switchCustomView.setOnCheckedChangeListener { _, _ ->
            setUpCustomView(null)
        }

        findViewById<SwitchCompat>(R.id.card_header_switch_responsive).setOnCheckedChangeListener { _, isChecked ->
            val behaviour = if (isChecked) CardDrawerView.Behaviour.RESPONSIVE else CardDrawerView.Behaviour.REGULAR
            updateCardBehaviour(behaviour)
        }

        findViewById<SwitchCompat>(R.id.card_header_show_bottom_label_switch).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showBottomLabel()
            } else {
                hideBottomLabel()
            }
        }

        findViewById<SwitchCompat>(R.id.card_header_disabled_switch).setOnCheckedChangeListener { _, isChecked ->
            cardViews.forEach {
                it.view.isEnabled = !isChecked
            }
        }

        initCardConfigurationOptions()
        initCardNumber()
        initCardName()
        initExpirationDate()
        initSecurityCode()
    }

    private fun showBottomLabel() {
        val label = Label("mensaje destacado")
        cardViews.forEach {
            it.view.setBottomLabel(label)
            it.view.showBottomLabel()
        }
    }

    private fun hideBottomLabel() {
        cardViews.forEach {
            it.view.hideBottomLabel()
        }
    }

    private fun initSecurityCode() {
        findViewById<TextView>(R.id.security_code).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                showSecurityCode()
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                card.secCode = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().isEmpty()) {
                    showCard()
                }
            }
        })
    }

    private fun initExpirationDate() {
        findViewById<TextView>(R.id.expiration_date).addTextChangedListener(object : CustomTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                card.expiration = charSequence.toString()
            }
        })
    }

    private fun initCardName() {
        val cardName = findViewById<TextView>(R.id.card_name)
        cardName.addTextChangedListener(object : CustomTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                card.name = charSequence.toString()
            }
        })
        cardName.setOnClickListener {
            showCard()
        }
    }

    private fun initCardNumber() {
        val cardNumber = findViewById<TextView>(R.id.card_number)
        cardNumber.addTextChangedListener(object : CustomTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                card.number = charSequence.toString()
            }
        })
        cardNumber.setOnClickListener {
            showCard()
        }
    }

    private fun initCardConfigurationOptions() {
        val cardConfigurations = findViewById<AutoCompleteTextView>(R.id.configurations)
        val cardOptions = mutableListOf<CardConfigurationOption>()
        val tag = CardDrawerSource.Tag(
            "Novo",
            Color.parseColor("#1A4189E6"), Color.parseColor("#009EE3"), "regular"
        )
        val defaultOption = CardConfigurationOption("Default", DefaultCardConfiguration(this), tag)
        cardOptions.add(defaultOption)
        cardOptions.add(CardConfigurationOption("Visa blue", VisaCardBlueConfiguration(this), tag))
        cardOptions.add(CardConfigurationOption("Visa green", VisaCardGreenConfiguration(this), tag))
        cardOptions.add(CardConfigurationOption("Visa red", VisaCardRedConfiguration(this), tag))
        cardOptions.add(CardConfigurationOption("Visa gray", VisaCardGrayConfiguration(this), tag))
        cardOptions.add(CardConfigurationOption("Visa yellow", VisaCardYellowConfiguration(this), tag))
        cardOptions.add(CardConfigurationOption("Master", MasterCardConfiguration(this), tag))
        cardOptions.add(CardConfigurationOption("Url Test", UrlTestConfiguration(this), tag))
        cardOptions.add(CardConfigurationOption("Hybrid Account Money", CardDrawerStyle.ACCOUNT_MONEY_HYBRID, tag))
        cardOptions.add(CardConfigurationOption("Hybrid Credit", HybridCreditConfiguration(), tag))
        cardOptions.add(CardConfigurationOption("Default Account Money", CardDrawerStyle.ACCOUNT_MONEY_DEFAULT, tag))
        cardOptions.add(CardConfigurationOption("Custom account money", CustomAccountMoneyConfiguration(), tag))
        cardOptions.add(CardConfigurationOption("PIX", PixConfiguration(this)))
        val cardAdapter = ArrayAdapter(this, R.layout.card_drawer_list_item, cardOptions)
        cardConfigurations.setAdapter(cardAdapter)
        cardConfigurations.setOnItemClickListener { _, _, position, _ ->
            val selection = cardOptions[position]
            val source = selection.cardConfiguration
            val style = selection.cardStyle
            if (source != null) {
                updateCardSource(source)
            } else if (style != null) {
                updateCardStyle(style, selection.styledCardTag)
            }
            updateCardTagVisibility()
            setUpCustomView(style)
        }
        cardConfigurations.setText(defaultOption.toString(), false)
        updateCardSource(defaultOption.cardConfiguration!!)
    }

    private fun setUpCustomView(style: CardDrawerStyle?) {
        cardViews.forEach {
            if (switchCustomView.isChecked) {
                val model = if (style === CardDrawerStyle.ACCOUNT_MONEY_HYBRID) {
                    createModelForAccountMoneyHybrid()
                } else {
                    createModel()
                }

                val switchView = CardDrawerSwitch(this@MainActivity)
                switchView.setSwitchListener(object : CardDrawerSwitch.OnSwitchListener {
                    override fun onChange(id: String) {
                        Log.i(
                            "CARD_DRAWER",
                            "ID: $id"
                        )
                    }
                })
                switchView.setSwitchModel(model)
                switchView.setConfiguration(it.view.buildCustomViewConfiguration())
                it.view.setCustomView(switchView)
            } else {
                it.view.setCustomView(null)
            }
        }
    }

    private fun initSizesDropDown() {
        findViewById<AutoCompleteTextView>(R.id.sizes).also {
            it.setAdapter(ArrayAdapter(this, R.layout.card_drawer_list_item, cardViews))
            it.setText(cardViews.first().name, false)
            it.setOnItemClickListener { _, _, position, _ ->
                updateCardVisibility(cardViews[position].view)
            }
        }
    }

    private fun updateCardTagVisibility() {
        val visibility = if (switchTag.isChecked) View.VISIBLE else View.GONE
        cardViews.forEach {
            val regularTag =
                it.view.findViewById<View>(R.id.card_header_front).findViewById<View>(R.id.card_tag_container)
            val genericTag =
                it.view.findViewById<View>(R.id.card_drawer_generic_front).findViewById<View>(R.id.card_tag_container)
            regularTag.visibility = visibility
            genericTag.visibility = visibility
        }
    }

    private fun updateCardVisibility(visibleCard: CardDrawerView) {
        cardViews.forEach {
            it.view.visibility = if (it.view == visibleCard) View.VISIBLE else View.GONE
        }
    }

    private fun showSecurityCode() {
        cardViews.forEach {
            it.view.showSecurityCode()
        }
    }

    private fun showCard() {
        cardViews.forEach {
            it.view.show()
        }
    }

    private fun updateCardSource(source: CardDrawerSource) {
        cardViews.forEach {
            it.view.show(source)
        }
    }

    private fun updateCardBehaviour(behaviour: Int) {
        cardViews.forEach {
            it.view.setBehaviour(behaviour)
        }
    }

    private fun updateCardStyle(style: CardDrawerStyle, tag: CardDrawerSource.Tag?) {
        cardViews.forEach {
            it.view.setStyle(style, tag)
        }
    }

    private fun addCard(key:String, cardDrawerView: CardDrawerView) {
        cardViews.add(CardDrawerViewOption(key, cardDrawerView))
        card.addCard(cardDrawerView.card)
    }

    abstract class CustomTextWatcher : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) = Unit
        override fun afterTextChanged(editable: Editable) = Unit
    }
}
