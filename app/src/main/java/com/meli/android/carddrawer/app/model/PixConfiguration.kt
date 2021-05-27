package com.meli.android.carddrawer.app.model

import android.content.Context
import android.graphics.Color
import com.meli.android.carddrawer.model.GenericPaymentMethod
import com.meli.android.carddrawer.utils.DensityHelper

class PixConfiguration(context: Context) : GenericPaymentMethod(
    Color.WHITE,
    Text("PIX", Color.parseColor("#CC000000")),
    "https://mobile.mercadolibre.com/remote_resources/image/card_drawer_mlb_pm_pix_normal?density=${DensityHelper.getName(context)}&locale=es_AR&version=1",
    Text("Aprovação Imediata", Color.parseColor("#00A650")),
    Tag("Novo", Color.parseColor("#1A4189E6"), Color.parseColor("#009EE3"))
)
