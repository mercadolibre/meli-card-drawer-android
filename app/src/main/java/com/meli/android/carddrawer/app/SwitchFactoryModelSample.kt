package com.meli.android.carddrawer.app

import com.meli.android.carddrawer.model.customview.SwitchModel

object SwitchFactoryModelSample {

    @JvmStatic
    fun createModel():SwitchModel {
        val states = SwitchModel.SwitchStates(
            SwitchModel.SwitchStates.State("#8c8c8c", "semi_bold"),
            SwitchModel.SwitchStates.State("#ffffff", "semi_bold")
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

    @JvmStatic
    fun createModelForAccountMoneyHybrid() : SwitchModel {
        return createModel().copy(switchBackgroundColor = "#009ee3")
    }
}