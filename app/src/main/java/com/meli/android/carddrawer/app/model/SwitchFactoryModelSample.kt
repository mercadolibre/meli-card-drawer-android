package com.meli.android.carddrawer.app.model

import com.meli.android.carddrawer.model.State
import com.meli.android.carddrawer.model.SwitchModel
import com.meli.android.carddrawer.model.SwitchOption
import com.meli.android.carddrawer.model.SwitchStates

object SwitchFactoryModelSample {
    @JvmStatic
    fun createModel() : SwitchModel {
        val states = SwitchStates(
            State("#000000", "#000000", "regular"),
            State("#000000", "#000000", "regular"),
            State("#000000", "#000000", "regular")
        )
        val options = arrayListOf(
            SwitchOption("debit_card", "Debito"),
            SwitchOption("credit_card", "Credito")
        )
        return SwitchModel(states, options, "#000000", "debit_card")
    }
}