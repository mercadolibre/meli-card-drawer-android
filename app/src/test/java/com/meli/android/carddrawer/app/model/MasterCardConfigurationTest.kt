package com.meli.android.carddrawer.app.model

import android.widget.ImageView
import com.meli.android.carddrawer.app.BaseTest
import com.meli.android.carddrawer.app.R
import com.meli.android.carddrawer.configuration.FontType
import com.meli.android.carddrawer.configuration.SecurityCodeLocation
import com.meli.android.carddrawer.model.CardAnimationType
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class MasterCardConfigurationTest: BaseTest() {

    private lateinit var masterCardConfiguration: MasterCardConfiguration

    override fun setUp() {
        super.setUp()
        masterCardConfiguration = MasterCardConfiguration(contextMock)
    }

    @Test
    fun `when getting card number pattern then return array with size 3`() {
        Assert.assertEquals(masterCardConfiguration.cardNumberPattern.size, 3)
    }

    @Test
    fun `when getting card number pattern then return array with values 4, 6, 5`() {
        Assert.assertEquals(masterCardConfiguration.cardNumberPattern[0], 4)
        Assert.assertEquals(masterCardConfiguration.cardNumberPattern[1], 6)
        Assert.assertEquals(masterCardConfiguration.cardNumberPattern[2], 5)
    }

    @Test
    fun `when getting name placeholder then return card_drawer_card_hint_name`() {
        Assert.assertEquals(masterCardConfiguration.namePlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_name))
    }

    @Test
    fun `when getting expiration placeholder then return card_drawer_card_hint_date`() {
        Assert.assertEquals(masterCardConfiguration.expirationPlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_date))
    }

    @Test
    fun `when getting fontType then return FontType LIGHT_TYPE`() {
        Assert.assertEquals(masterCardConfiguration.fontType, FontType.LIGHT_TYPE)
    }

    @Test
    fun `when getting animationType then return CardAnimationType LEFT_TOP`() {
        Assert.assertEquals(masterCardConfiguration.animationType, CardAnimationType.LEFT_TOP)
    }

    @Test
    fun `when getting bank image res then return card_drawer_app_bank_logo_macro`() {
        Assert.assertEquals(masterCardConfiguration.bankImageRes, R.drawable.card_drawer_app_bank_logo_macro)
    }

    @Test
    fun `when getting card logo image res then return card_drawer_app_card_logo_master_dark`() {
        Assert.assertEquals(masterCardConfiguration.cardLogoImageRes, R.drawable.card_drawer_app_card_logo_master_dark)
    }

    @Test
    fun `when call function setCardLogoImage then not set cardLogoImageUrl`() {
        val imageView = mockk<ImageView>(relaxed = true)
        masterCardConfiguration.setCardLogoImage(imageView)
        Assert.assertEquals(masterCardConfiguration.cardLogoImageUrl, null)
    }

    @Test
    fun `when call function setBankImage then not set bankImageUrl`() {
        val imageView = mockk<ImageView>(relaxed = true)
        masterCardConfiguration.setCardLogoImage(imageView)
        Assert.assertEquals(masterCardConfiguration.bankImageUrl, null)
    }

    @Test
    fun `when getting security code location then return SecurityCodeLocation FRONT`() {
        Assert.assertEquals(masterCardConfiguration.securityCodeLocation, SecurityCodeLocation.FRONT)
    }

    @Test
    fun `when getting card font color then return 0`() {
        Assert.assertEquals(masterCardConfiguration.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then return 0`() {
        Assert.assertEquals(masterCardConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then return NUMBER_SEC_CODE`() {
        Assert.assertEquals(masterCardConfiguration.securityCodePattern, 4)
    }

}