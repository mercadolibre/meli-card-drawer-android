package com.meli.android.carddrawer.app.model

import android.graphics.Color
import com.meli.android.carddrawer.model.GenericPaymentMethod

class DebinConfiguration : GenericPaymentMethod(
    Color.BLUE,
    Text("Banco BBVA", Color.parseColor("#FFFFFF"), "semi_bold"),
    "https://http2.mlstatic.com/storage/logos-api-admin/227062e0-ae66-11eb-9123-2107040a9cba-xl@2x.png",
    Text("Cuenta corriente", Color.parseColor("#FFFFFF"), "regular"),
    Tag("Novo", Color.parseColor("#FFFFFF"), Color.parseColor("#009EE3"), "semi_bold"),
    arrayListOf("#002444", "#004580", "#0067BE"),
    Text("CBU: ***1234", Color.parseColor("#FFFFFF"), "regular")
)
