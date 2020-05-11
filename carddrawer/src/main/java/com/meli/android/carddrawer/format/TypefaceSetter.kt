package com.meli.android.carddrawer.format

import android.content.Context
import android.graphics.Typeface
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.provider.FontRequest
import android.support.v4.provider.FontsContractCompat
import android.widget.TextView
import com.meli.android.carddrawer.R

/**
 * A helper class for setting typeface to views
 */
object TypefaceSetter {
    private const val HANDLER_THREAD_NAME = "fonts"
    private var HANDLER: Handler? = null
    private var robotoMono: Typeface? = null

    fun init(context: Context) {
        fetchRobotoMono(context)
    }

    fun set(textView: TextView, customTypeface: Typeface?) {
        textView.typeface = customTypeface ?: robotoMono ?: Typeface.MONOSPACE
    }

    private fun fetchRobotoMono(context: Context) {
        val authority = context.resources.getString(R.string.card_drawer_gms_font_provider_authority)
        val packageName = context.resources.getString(R.string.card_drawer_gms_font_provider_package)
        val certificates = R.array.card_drawer_com_google_android_gms_fonts_certs
        val query = context.resources.getString(R.string.card_drawer_roboto_mono_query)
        val request = FontRequest(authority, packageName, query, certificates)
        val callback: FontsContractCompat.FontRequestCallback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface) {
                robotoMono = typeface
            }

            override fun onTypefaceRequestFailed(reason: Int) {
                // Do nothing.
            }
        }
        FontsContractCompat.requestFont(context, request, callback, HANDLER!!)
    }

    init {
        val handlerThread = HandlerThread(HANDLER_THREAD_NAME)
        handlerThread.start()
        HANDLER = Handler(handlerThread.looper)
    }
}