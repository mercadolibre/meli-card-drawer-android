package com.meli.android.carddrawer.model

import android.content.Context
import android.graphics.Color
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.ViewScenarioRule
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers.endsWith
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CardDrawerViewTest {

    @get:Rule
    val viewScenario = ViewScenarioRule()

    private lateinit var subject: CardDrawerView
    private val expectedCardName = "Maria Alessandra Louise Figueiredo"
    private val expectedCardExpiration = "12/12"
    private val cardNumber = "5325  4816  4243  7098"
    private val expectedCardNumber = "5325  4816  4243  7098"
    private val cvv = "11"
    private val expectedCVV = "11**"

    @Before
    fun setUp() {
        viewScenario.launch(
            viewFactory = { CardDrawerView(it) },
            onView = { subject = it }
        )
    }

    @Test
    fun when_is_rendered_with_tag_then_display_tag_with_expected_values() {

        val expectedTagText = "Sample Tag"
        val tag = buildTag(expectedTagText, "regular")
        val cardDrawerSource = PaymentCard(
            cardUI = buildConfiguration(viewScenario.activity),
            tag = tag
        )

        viewScenario.activity.runOnUiThread { subject.show(cardDrawerSource) }

        onView(withText(expectedTagText.toUpperCase())).check(matches(withId(R.id.card_tag)))
    }

    @Test
    fun when_is_rendered_with_bottom_label_then_display_tag_with_expected_values() {

        val expectedBottomLabel = "Sample bottom label"
        val label = Label(text = expectedBottomLabel,color = "#000000",backgroundColor = "red")

        subject.setBottomLabel(label)
        subject.showBottomLabel()

        onView(withText(expectedBottomLabel))
            .check(matches(isDisplayed()))
            .check(matches(withId(R.id.card_drawer_bottom_description))
        )
    }

    @Test
    fun when_is_rendered_with_default_values_then_CardDrawerView_isDisplayed() {
        onView(withClassName(endsWith("CardDrawerView"))).check(matches(isDisplayed()))
    }

    @Test
    fun when_is_rendered_with_default_values_then_card_name_placeholder_isDisplayed() {
        val expectedCardName = "Nombre y Apellido"
        onView(withId(R.id.cho_card_name)).check(matches(withText(expectedCardName)))
    }

    @Test
    fun when_is_rendered_with_default_values_then_card_expiration_placeholder_isDisplayed() {
        val expectedCardExpiration = "MM/AA"
        onView(withId(R.id.cho_card_date)).check(matches(withText(expectedCardExpiration)))
    }

    @Test
    fun when_is_rendered_with_default_values_then_card_number_truncated_isDisplayed() {
        val expectedCardNumber = "****  ****  ****  ****"
        onView(withId(R.id.cho_card_number)).check(matches(withText(expectedCardNumber)))
    }

    @Test
    fun when_is_rendered_with_default_values_then_card_CVV_truncated_isDisplayed() {
        val expectedCVV = "****"
        onView(withId(R.id.cho_card_code_front)).check(matches(withText(expectedCVV)))
    }

    @Test
    fun when_is_rendered_with_user_values_then_card_name_placeholder_isDisplayed() {
        updateCardInformation()
        onView(withId(R.id.cho_card_name)).check(matches(withText(expectedCardName)))
    }

    @Test
    fun when_is_rendered_with_user_values_then_card_expiration_placeholder_isDisplayed() {
        updateCardInformation()
        onView(withId(R.id.cho_card_date)).check(matches(withText(expectedCardExpiration)))
    }

    @Test
    fun when_is_rendered_with_user_values_then_card_number_truncated_isDisplayed() {
        updateCardInformation()
        onView(withId(R.id.cho_card_number)).check(matches(withText(expectedCardNumber)))
    }

    @Test
    fun when_is_rendered_with_user_values_then_card_CVV_Truncated_isDisplayed() {
        updateCardInformation()
        onView(withId(R.id.cho_card_code_front)).check(matches(withText(expectedCVV)))
    }

    private fun updateCardInformation() {
        subject.card = Card().apply {
            name = expectedCardName
            expiration = expectedCardExpiration
            number = cardNumber
            secCode = cvv
        }
        subject.updateCardInformation()
    }

    private fun buildConfiguration(context: Context): DefaultCardConfiguration {
        return DefaultCardConfiguration(context)
    }

    private fun buildTag(text: String, weight: String): CardDrawerSource.Tag {
        return CardDrawerSource.Tag(
                text,
                Color.parseColor("#CCCCCC"),
                Color.parseColor("#FFFFFF"),
                weight
        )
    }
}