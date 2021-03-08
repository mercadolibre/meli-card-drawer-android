package com.meli.android.carddrawer.app

import com.meli.android.carddrawer.model.customview.SwitchModel

object SwitchFactoryModelSample {
    @JvmStatic
    fun createModel() : SwitchModel {
        val states = SwitchModel.SwitchStates(
            SwitchModel.SwitchStates.State("#8c8c8c", "#000000", "semi_bold"),
            SwitchModel.SwitchStates.State("#ffffff", "#000000", "semi_bold")
        )
        val options = arrayListOf(
            SwitchModel.SwitchOption("debit_card", "Débito"),
            SwitchModel.SwitchOption("credit_card", "Crédito")
        )
        val description = SwitchModel.Text(
            "#ffffff",
            "semi_bold",
            "Você paga com"
        )
        return SwitchModel(
            description,
            states,
            options,
            "#a6000000",
            "#ffffff",
            "#26000000",
            "debit_card")
    }
}