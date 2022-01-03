package com.meli.android.carddrawer.configuration.base

import android.content.Context
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before

open class ConfigurationTestBase {

    @MockK(relaxed = true)
    protected lateinit var contextMock: Context

    @Before
    open fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

}