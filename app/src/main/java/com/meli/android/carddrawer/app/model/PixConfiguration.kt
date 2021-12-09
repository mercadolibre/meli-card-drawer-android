package com.meli.android.carddrawer.app.model

import android.content.Context
import android.graphics.Color
import com.meli.android.carddrawer.model.GenericPaymentMethod
import com.meli.android.carddrawer.utils.DensityHelper

class PixConfiguration(context: Context) : GenericPaymentMethod(
    Color.BLUE,
    Text("PIX", Color.parseColor("#CC000000")),
    "https://http2.mlstatic.com/storage/logos-api-admin/227062e0-ae66-11eb-9123-2107040a9cba-xl@2x.png",
    Text("Aprovação Imediata", Color.parseColor("#00A650")),
    Tag("Novo", Color.parseColor("#1A4189E6"), Color.parseColor("#009EE3"), "semi_bold"),
    null,
    arrayListOf("#132F3B", "#37B4AA", "#3688AB")
)
