package com.meli.android.carddrawer.model.customView

import com.meli.android.carddrawer.model.customview.SwitchModel
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SwitchModelTest {

    private lateinit var switchModel: SwitchModel
    private val description by lazy { mockk<SwitchModel.Text>() }
    private val states by lazy { mockk<SwitchModel.SwitchStates>() }
    private val options by lazy { mockk<List<SwitchModel.SwitchOption>>() }
    private val switchBackgroundColor by lazy { "#000000" }
    private val pillBackgroundColor by lazy { "#000002" }
    private val safeZoneBackgroundColor by lazy { "#FFFFFF" }
    private val default by lazy { "#00000" }

    private lateinit var text: SwitchModel.Text
    private val textColor by lazy { "#000005" }
    private val weight by lazy { "12" }
    private val textValue by lazy { "test" }

    private lateinit var switchOption: SwitchModel.SwitchOption
    private val id by lazy { "7" }
    private val name by lazy { "test" }

    private lateinit var switchStates: SwitchModel.SwitchStates
    private val checkedState by lazy { mockk<SwitchModel.SwitchStates.State>() }
    private val uncheckedState by lazy { mockk<SwitchModel.SwitchStates.State>() }

    private lateinit var state: SwitchModel.SwitchStates.State
    private val textColorState by lazy { "#988874" }
    private val weightState by lazy { "32" }

    @Before
    fun setUp() {
        switchModel = returnSwitchModel()
        text = returnText()
        switchOption = returnSwitchOption()
        switchStates = returnSwitchStates()
        state = returnState()
    }

    private fun returnSwitchModel(): SwitchModel {
        return SwitchModel(
            description = description,
            states = states,
            options = options,
            switchBackgroundColor = switchBackgroundColor,
            pillBackgroundColor = pillBackgroundColor,
            safeZoneBackgroundColor = safeZoneBackgroundColor,
            default = default
        )
    }

    private fun returnText(): SwitchModel.Text {
        return SwitchModel.Text(
            textColor = textColor,
            weight = weight,
            text = textValue
        )
    }

    private fun returnSwitchOption(): SwitchModel.SwitchOption {
        return SwitchModel.SwitchOption(
            id = id,
            name = name
        )
    }

    private fun returnSwitchStates(): SwitchModel.SwitchStates {
        return SwitchModel.SwitchStates(
            checkedState = checkedState,
            uncheckedState = uncheckedState
        )
    }

    private fun returnState(): SwitchModel.SwitchStates.State {
        return SwitchModel.SwitchStates.State(
            textColor = textColorState,
            weight = weightState
        )
    }

    @Test
    fun `when getting the value of the SwitchModel description then return value that was set`() {
        Assert.assertEquals(switchModel.description, description)
    }

    @Test
    fun `when getting the value of the SwitchModel states then return value that was set`() {
        Assert.assertEquals(switchModel.states, states)
    }

    @Test
    fun `when getting the value of the SwitchModel options then return value that was set`() {
        Assert.assertEquals(switchModel.options, options)
    }

    @Test
    fun `when getting the value of the SwitchModel switchBackgroundColor then return value that was set`() {
        Assert.assertEquals(switchModel.switchBackgroundColor, switchBackgroundColor)
    }

    @Test
    fun `when getting the value of the SwitchModel pillBackgroundColor then return value that was set`() {
        Assert.assertEquals(switchModel.pillBackgroundColor, pillBackgroundColor)
    }

    @Test
    fun `when getting the value of the SwitchModel safeZoneBackgroundColor then return value that was set`() {
        Assert.assertEquals(switchModel.safeZoneBackgroundColor, safeZoneBackgroundColor)
    }

    @Test
    fun `when getting the value of the SwitchModel default then return value that was set`() {
        Assert.assertEquals(switchModel.default, default)
    }

    @Test
    fun `when getting the value of the Text textColor then return value that was set`() {
        Assert.assertEquals(text.textColor, textColor)
    }

    @Test
    fun `when getting the value of the Text weight then return value that was set`() {
        Assert.assertEquals(text.weight, weight)
    }

    @Test
    fun `when getting the value of the Text textValue then return value that was set`() {
        Assert.assertEquals(text.text, textValue)
    }

    @Test
    fun `when getting the value of the SwitchOption id then return value that was set`() {
        Assert.assertEquals(switchOption.id, id)
    }

    @Test
    fun `when getting the value of the SwitchOption name then return value that was set`() {
        Assert.assertEquals(switchOption.name, name)
    }

    @Test
    fun `when getting the value of the SwitchStates checkedState then return value that was set`() {
        Assert.assertEquals(switchStates.checkedState, checkedState)
    }

    @Test
    fun `when getting the value of the SwitchStates uncheckedState then return value that was set`() {
        Assert.assertEquals(switchStates.uncheckedState, uncheckedState)
    }

    @Test
    fun `when getting the value of the State textColor then return value that was set`() {
        Assert.assertEquals(state.textColor, textColorState)
    }

    @Test
    fun `when getting the value of the State weight then return value that was set`() {
        Assert.assertEquals(state.weight, weightState)
    }

}