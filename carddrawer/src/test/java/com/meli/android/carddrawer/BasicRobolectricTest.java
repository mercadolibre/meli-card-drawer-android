package com.meli.android.carddrawer;

import android.content.Context;

import org.robolectric.RuntimeEnvironment;

public class BasicRobolectricTest {
    protected Context getContext() {
        return RuntimeEnvironment.application;
    }
}
