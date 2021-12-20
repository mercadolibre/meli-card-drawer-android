package com.meli.android.carddrawer.model

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


@RunWith(MockitoJUnitRunner::class)
class CardConfigurationTest {

    @Mock
    private lateinit var cardConfiguration: CardConfiguration

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Should test function updateSource`() {
        val cardUIMock = mock<CardUI>()
        cardConfiguration.updateSource(cardUIMock)
        verify(cardConfiguration, times(1)).updateSource(cardUIMock)
    }

    @Test
    fun `Should test function updateConfiguration`() {
        val constraintLayoutMock = mock<ConstraintLayout>()
        cardConfiguration.updateConfiguration(constraintLayoutMock)
        verify(cardConfiguration, times(1)).updateConfiguration(constraintLayoutMock)
    }

    @Test
    fun `Should test function resetConfiguration`() {
        val constraintLayoutMock = mock<ConstraintLayout>()
        cardConfiguration.resetConfiguration(constraintLayoutMock)
        verify(cardConfiguration, times(1)).resetConfiguration(constraintLayoutMock)
    }

    @Test
    fun `Should test function canShow`() {
        val viewMock = mock<View>()
        cardConfiguration.canShow(viewMock)
        verify(cardConfiguration, times(1)).canShow(viewMock)
    }

    @Test
    fun `Should test function canAnimate`() {
        
    }

}