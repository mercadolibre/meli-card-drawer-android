package com.meli.android.carddrawer.model

import android.graphics.Color
import org.robolectric.RobolectricTestRunner
import com.meli.android.carddrawer.BasicRobolectricTest
import com.meli.android.carddrawer.R
import androidx.appcompat.widget.AppCompatTextView
import android.view.View
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import io.mockk.spyk

/**
 * Test for the card header.. many visual and animations changes can't be tested with just junit :(
 */
@RunWith(RobolectricTestRunner::class)
class CardDrawerViewMediumTest : BasicRobolectricTest() {

    lateinit var header: CardDrawerViewMedium

    @Before
    fun doBefore() {
        header = CardDrawerViewMedium(context)
    }

    @Test
    fun givenGenericPaymentMethodWhenSetTitleAndGenericTextThenCheckValues() {
        val spyHeader = spyk(header)
        val genericPaymentMethod = GenericPaymentMethod(
            Color.BLUE,
            GenericPaymentMethod.Text("Banco BBVA", Color.parseColor("#FFFFFF"), "semi_bold"),
            "https://http2.mlstatic.com/storage/logos-api-admin/227062e0-ae66-11eb-9123-2107040a9cba-xl@2x.png",
            GenericPaymentMethod.Text("Cuenta corriente", Color.parseColor("#FFFFFF"), "regular"),
            CardDrawerSource.Tag(
                "Novo",
                Color.parseColor("#1A4189E6"),
                Color.parseColor("#009EE3"),
                "semi_bold"
            )
        )

        spyHeader.show(genericPaymentMethod)

        val genericTitle: AppCompatTextView =
            spyHeader.genericFrontLayout.findViewById(R.id.generic_title)
        val genericText: AppCompatTextView =
            spyHeader.genericFrontLayout.findViewById(R.id.generic_text)

        assertEquals("Banco BBVA", genericTitle.text)
        assertEquals("Cuenta corriente", genericText.text)
        assertEquals(genericPaymentMethod.title.color, genericTitle.currentTextColor)
        assertEquals(Color.parseColor("#FFFFFF"), genericText.currentTextColor)
    }

    @Test
    fun givenGenericPaymentMethodWhenSetTitleAndGenericTextThenShowViews() {
        val spyHeader = spyk(header)
        val genericPaymentMethod = GenericPaymentMethod(
            Color.BLUE,
            GenericPaymentMethod.Text("Banco BBVA", Color.parseColor("#FFFFFF"), "semi_bold"),
            "https://http2.mlstatic.com/storage/logos-api-admin/227062e0-ae66-11eb-9123-2107040a9cba-xl@2x.png",
            GenericPaymentMethod.Text("Cuenta corriente", Color.parseColor("#FFFFFF"), "regular"),
            CardDrawerSource.Tag(
                "Novo",
                Color.parseColor("#1A4189E6"),
                Color.parseColor("#009EE3"),
                "semi_bold"
            ),
            null,
            GenericPaymentMethod.Text("CBU: ***1234", Color.parseColor("#FFFFFF"), "regular")
        )

        spyHeader.show(genericPaymentMethod)

        val genericTitle: AppCompatTextView =
            spyHeader.genericFrontLayout.findViewById(R.id.generic_title)
        val genericText: AppCompatTextView =
            spyHeader.genericFrontLayout.findViewById(R.id.generic_text)

        assertEquals(View.VISIBLE, genericTitle.visibility)
        assertEquals(View.VISIBLE, genericText.visibility)
    }
}
