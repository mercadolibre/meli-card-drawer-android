package com.meli.android.carddrawer.format;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;
import android.widget.TextView;

import com.meli.android.carddrawer.R;

/**
 * A helper class for setting the roboto mono typeface to views
 */
public final class MonospaceTypefaceSetter {

    private static final String HANDLER_THREAD_NAME = "fonts";

    private static final Handler HANDLER;

    static {
        final HandlerThread handlerThread = new HandlerThread(HANDLER_THREAD_NAME);
        handlerThread.start();
        HANDLER = new Handler(handlerThread.getLooper());
    }

    private MonospaceTypefaceSetter() {
        // hide constructor
    }

    /**
     * Apply Roboto Monospace Regular to this view
     *
     * @param context  the app context
     * @param textView the view that will have the font
     */
    public static void setRobotoMono(@NonNull final Context context, @NonNull final TextView textView) {
        final String authority = context.getResources().getString(R.string.card_drawer_gms_font_provider_authority);
        final String packageName = context.getResources().getString(R.string.card_drawer_gms_font_provider_package);
        final int certificates = R.array.card_drawer_com_google_android_gms_fonts_certs;
        final String query = context.getResources().getString(R.string.card_drawer_roboto_mono_query);
        final FontRequest request = new FontRequest(authority, packageName, query, certificates);

        final FontsContractCompat.FontRequestCallback callback =
                new FontsContractCompat.FontRequestCallback() {
                    @Override
                    public void onTypefaceRetrieved(@NonNull final Typeface typeface) {
                        textView.setTypeface(typeface);
                    }

                    @Override
                    public void onTypefaceRequestFailed(final int reason) {
                        // Do nothing.
                    }
                };
        FontsContractCompat.requestFont(context, request, callback, HANDLER);
    }

}
