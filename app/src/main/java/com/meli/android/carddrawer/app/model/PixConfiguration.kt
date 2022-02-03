package com.meli.android.carddrawer.app.model

import android.content.Context
import android.graphics.Color
import com.meli.android.carddrawer.model.GenericPaymentMethod

class PixConfiguration(context: Context) : GenericPaymentMethod(
    Color.WHITE,
    Text("PIX", Color.parseColor("#FFFFFF"), "semi_bold"),
    "https://http2.mlstatic.com/storage/logos-api-admin/8a135600-1fa5-11eb-b2cc-3f20f2f5ffe3-xl@2x.png",
    Text("Aprovação Imediata", Color.parseColor("#FFFFFF"), "regular"),
    Tag("Novo", Color.parseColor("#FFFFFF"), Color.parseColor("#009EE3"), "semi_bold"),
    arrayListOf("#132F3B", "#255E76", "#3688AB")
)
