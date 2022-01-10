package com.meli.android.carddrawer.model

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageSwitcher
import androidx.annotation.LayoutRes
import com.meli.android.carddrawer.R

class CardDrawerViewSmall @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardDrawerViewLowres(context, attrs, defStyleAttr) {

    @LayoutRes
    override fun getLayout() = R.layout.card_drawer_layout_small

    override fun updateName(cardUI: CardUI) = Unit

    override fun updateIssuerLogo(issuerLogoView: ImageSwitcher?, source: CardUI, animate: Boolean) = Unit

    override fun showSecCircle() = Unit

    override fun hideSecCircle() = Unit

    override fun setCustomView(view: View?) = Unit

    override fun setBottomLabel(label: Label) = Unit

    override fun showBottomLabel() = Unit

    override fun hideBottomLabel() = Unit

    override fun updateOverlayVisibility() {
        overlayImage.visibility = GONE
    }

    override fun showGenericText(genericPaymentMethod: GenericPaymentMethod) = Unit
}
