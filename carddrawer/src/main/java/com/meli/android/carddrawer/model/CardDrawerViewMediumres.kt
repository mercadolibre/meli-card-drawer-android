package com.meli.android.carddrawer.model

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.LayoutRes
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.model.customview.CustomViewConfiguration

class CardDrawerViewMediumres @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardDrawerView(context, attrs, defStyleAttr) {
    override fun buildCustomViewConfiguration(): CustomViewConfiguration {
        return CustomViewConfiguration(Type.MEDIUM, style)
    }

    @LayoutRes
    override fun getLayout(): Int {
        return R.layout.card_drawer_layout_mediumres
    }
}
