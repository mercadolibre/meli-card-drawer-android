package com.meli.android.carddrawer.app.model

import android.content.Context
import android.graphics.Color
import com.meli.android.carddrawer.model.GenericPaymentMethod

class PixConfiguration(context: Context) : GenericPaymentMethod(
    Color.WHITE,
    Text("PIX", Color.parseColor("#FFFFFF"), "semi_bold"),
    "https://mobile.mercadolibre.com/remote_resources/image/card_drawer_mlb_pm_pix_normal?density=xxxhdpi&locale=en&version=1",
    Text("Aprovação Imediata", Color.parseColor("#FFFFFF"), "regular"),
    Tag("Novo", Color.parseColor("#FFFFFF"), Color.parseColor("#009EE3"), "semi_bold"),
    arrayListOf("#132F3B", "#255E76", "#3688AB")
)
