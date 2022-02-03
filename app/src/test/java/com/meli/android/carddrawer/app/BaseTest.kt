package com.meli.android.carddrawer.app

import android.content.Context
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before

open class BaseTest {

    @MockK(relaxed = true)
    protected lateinit var contextMock: Context

    @Before
    open fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

}