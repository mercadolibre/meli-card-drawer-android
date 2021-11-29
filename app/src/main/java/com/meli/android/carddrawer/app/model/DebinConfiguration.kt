package com.meli.android.carddrawer.app.model

import android.content.Context
import android.graphics.Color
import com.meli.android.carddrawer.model.GenericPaymentMethod
import com.meli.android.carddrawer.utils.DensityHelper

class DebinConfiguration(context: Context) : GenericPaymentMethod(
    Color.BLUE,
    Text("Banco Galicia", Color.parseColor("#FFFFFF")),
    "https://http2.mlstatic.com/storage/logos-api-admin/227062e0-ae66-11eb-9123-2107040a9cba-xl@2x.png",
    Text("Cuenta corriente", Color.parseColor("#FFFFFF")),
    Tag("Novo", Color.parseColor("#1A4189E6"), Color.parseColor("#009EE3"), "semi_bold"),
    Text("CBU: ***1234", Color.parseColor("#FFFFFF"))
)
