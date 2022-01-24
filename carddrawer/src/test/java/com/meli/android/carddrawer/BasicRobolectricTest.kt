package com.meli.android.carddrawer

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.LOLLIPOP_MR1])
open class BasicRobolectricTest {
    protected val context: Context
        protected get() = ApplicationProvider.getApplicationContext()
}