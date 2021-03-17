package com.meli.android.carddrawer.format

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Handler
import android.os.HandlerThread
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import android.widget.TextView
import com.meli.android.carddrawer.R
import com.mercadolibre.android.andesui.font.Font
import com.mercadolibre.android.andesui.font.TypefaceHelper

/**
 * A helper class for setting typeface to views
 */
internal object TypefaceHelper {
    private const val HANDLER_THREAD_NAME = "fonts"
    private val handlerThread = HandlerThread(HANDLER_THREAD_NAME)
    private var robotoMono: Typeface? = null

    fun set(textView: TextView, customTypeface: Typeface?) {
        customTypeface?.let {
            textView.typeface = it
        } ?: setMonospace(textView)
    }

    fun set(textView: TextView, cardDrawerFont: CardDrawerFont) {
        TypefaceHelper.setTypeface(textView, cardDrawerFont.font)
    }

    fun set(context: Context, paint: Paint, cardDrawerFont: CardDrawerFont) {
        TypefaceHelper.setTypeface(context, paint, cardDrawerFont.font)
    }

    fun get(context: Context, cardDrawerFont: CardDrawerFont): Typeface? {
        return TypefaceHelper.getFontTypeface(context, cardDrawerFont.font)
    }

    private fun setMonospace(textView: TextView) {
        robotoMono?.let {
            textView.typeface = it
        } ?: run {
            val context = textView.context
            val authority = context.resources.getString(R.string.card_drawer_gms_font_provider_authority)
            val packageName = context.resources.getString(R.string.card_drawer_gms_font_provider_package)
            val certificates = R.array.card_drawer_com_google_android_gms_fonts_certs
            val query = context.resources.getString(R.string.card_drawer_roboto_mono_query)
            val request = FontRequest(authority, packageName, query, certificates)
            val callback: FontsContractCompat.FontRequestCallback = object : FontsContractCompat.FontRequestCallback() {
                override fun onTypefaceRetrieved(typeface: Typeface) {
                    robotoMono = typeface
                    textView.typeface = typeface
                }

                override fun onTypefaceRequestFailed(reason: Int) {
                    textView.typeface = Typeface.MONOSPACE
                }
            }
            FontsContractCompat.requestFont(context, request, callback, Handler(handlerThread.looper))
        }
    }

    init {
        handlerThread.start()
    }
}