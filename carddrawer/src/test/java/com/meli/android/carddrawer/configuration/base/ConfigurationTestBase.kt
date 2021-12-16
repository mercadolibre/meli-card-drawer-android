package com.meli.android.carddrawer.configuration.base

import android.content.Context
import android.content.res.Resources
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

open class ConfigurationTestBase {

    @Mock
    protected lateinit var contextMock: Context

    @Mock
    protected lateinit var resourcesMock: Resources

    @Before
    open fun init() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(contextMock.resources).thenReturn(resourcesMock)
    }

}