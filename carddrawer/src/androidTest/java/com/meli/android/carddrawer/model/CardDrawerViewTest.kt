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
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CardDrawerViewTest {

    @get:Rule
    val viewScenario = ViewScenarioRule()

    @Nested
    @DisplayName("Given CardDrawerView with default configuration")
    inner class GivenCardDrawerViewWithDefaultConfiguration {

        lateinit var subject: CardDrawerView

        @BeforeEach
        fun setUp() {
            viewScenario.launch(
                viewFactory = { CardDrawerView(it) },
                onView = { subject = it }
            )
        }

        @Nested
        @DisplayName("When is rendered with tag")
        inner class WhenIsRenderedWithTag {

            private val expectedTagText = "Sample Tag"
            private val cardDrawerSource = mockk<PaymentCard>(relaxed = true)
            private val tag = buildTag(expectedTagText, "regular")

            @BeforeEach
            fun setUp() {
                every { cardDrawerSource.cardUI } returns buildConfiguration(viewScenario.activity)
                every { cardDrawerSource.tag } returns tag
                viewScenario.activity.runOnUiThread { subject.show(cardDrawerSource) }
            }

            @Test
            @DisplayName("Then display tag with expected values")
            fun thenDisplayTagWithExpectedValues() {
                onView(withText(expectedTagText.toUpperCase())).check(matches(withId(R.id.card_tag)))
            }
        }

        @Nested
        @DisplayName("When is rendered with bottom label")
        inner class WhenIsRenderedWithBottomLabel {

            private val expectedBottomLabel = "Sample bottom label"

            @BeforeEach
            fun setUp() {
                val label = Label(
                    text = expectedBottomLabel,
                    color = "#000000",
                    backgroundColor = "red"
                )
                subject.setBottomLabel(label)
                subject.showBottomLabel()
            }

            @Test
            @DisplayName("Then display tag with expected values")
            fun thenDisplayTagWithExpectedValues() {
                onView(withText(expectedBottomLabel))
                    .check(matches(isDisplayed()))
                    .check(matches(withId(R.id.card_drawer_bottom_description))
                )
            }
        }

        @Nested
        @DisplayName("When is rendered without values")
        inner class WhenIsRenderedWithDefaultValues {

            @Test
            @DisplayName("Then the CardDrawer is displayed")
            fun thenTheCardDrawerIsDisplayed() {
                onView(withClassName(endsWith("CardDrawerView"))).check(matches(isDisplayed()))
            }

            @Test
            @DisplayName("Then the card name placeholder is displayed")
            fun thenTheCardNamePlaceholderIsDisplayed() {
                onView(withId(R.id.cho_card_name)).check(matches(withText("Nombre y Apellido")))
            }

            @Test
            @DisplayName("Then the card expiration placeholder is displayed")
            fun thenTheCardExpirationPlaceholderIsDisplayed() {
                onView(withId(R.id.cho_card_date)).check(matches(withText("MM/AA")))
            }

            @Test
            @DisplayName("Then the card number truncated is displayed")
            fun thenTheCardNumberTruncatedIsDisplayed() {
                onView(withId(R.id.cho_card_number)).check(matches(withText("****  ****  ****  ****")))
            }

            @Test
            @DisplayName("Then the card cvv truncated is displayed")
            fun thenTheCardCVVTruncatedIsDisplayed() {
                onView(withId(R.id.cho_card_code_front)).check(matches(withText("****")))
            }
        }
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