package com.meli.android.carddrawer

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import org.junit.rules.ExternalResource

class ViewScenarioRule : ExternalResource() {

    lateinit var activity: Activity

    @Suppress("UNCHECKED_CAST")
    fun <V : View> launch(viewFactory: (Context) -> V, onView: ((V) -> Unit)? = null) {
        ActivityScenario.launch(EmptyActivity::class.java).onActivity { emptyActivity ->
            this.activity = emptyActivity
            emptyActivity.setContentView(viewFactory(emptyActivity))
            emptyActivity.findViewById<ViewGroup>(android.R.id.content).getChildAt(0).let {
                onView?.invoke(it as V)
            }
        }
    }
}

class EmptyActivity : AppCompatActivity()