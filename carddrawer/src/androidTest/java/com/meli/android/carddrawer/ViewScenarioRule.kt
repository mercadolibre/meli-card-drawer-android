package com.meli.android.carddrawer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import org.junit.rules.ExternalResource

class ViewScenarioRule : ExternalResource() {

    @Suppress("UNCHECKED_CAST")
    fun <V : View> launch(viewFactory: (Context) -> V, onView: ((V) -> Unit)? = null) {
        ActivityScenario.launch(EmptyActivity::class.java).onActivity { activity ->
            activity.setContentView(viewFactory(activity))
            activity.findViewById<ViewGroup>(android.R.id.content).getChildAt(0).let {
                onView?.invoke(it as V)
            }
        }
    }
}

class EmptyActivity : AppCompatActivity()