package com.meli.android.carddrawer.model

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.ViewScenarioRule
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.jupiter.api.*

class CardDrawerViewTest {

    @get:Rule
    val viewScenario = ViewScenarioRule()

    @Nested
    @DisplayName("When is rendered with default values")
    inner class WhenIsRenderedWithDefaultValues {

        @BeforeEach
        fun setUp() {
            viewScenario.launch(
                    viewFactory = { CardDrawerView(it) }
            )
        }

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