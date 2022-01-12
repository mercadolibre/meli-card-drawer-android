package com.meli.android.carddrawer

import org.robolectric.util.ReflectionHelpers
import org.mockito.Mockito
import android.graphics.Typeface
import android.os.Build
import android.os.Parcelable
import android.os.Parcel
import com.meli.android.carddrawer.format.TypefaceHelper
import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * Utility class for tests
 */
object TestUtils {
    @JvmStatic
    fun initTypefaceSetter() {
        // Run the fonts fetching command in the current thread's looper. This way we avoid
        // async waitings and race conditions
        ReflectionHelpers.setStaticField(
            TypefaceHelper::class.java, "robotoMono", Mockito.mock(
                Typeface::class.java
            )
        )
    }

    /**
     * Utility method to test that a parcelable object is being properly parceled and unparceled
     *
     * @param <T> This describes my type parameter
     * @param objectToTest the parcelable object
     * @param creator      the parcelable creator for that class
     * @return             Clone of the param parcelable
    </T> */
    fun <T : Parcelable?> cloneParcelable(objectToTest: T, creator: Parcelable.Creator<T>): T {
        // Obtain a Parcel object and write the parcelable object to it:
        val parcel = Parcel.obtain()
        objectToTest!!.writeToParcel(parcel, 0)

        // After we're done with writing, we need to reset the parcel for reading:
        parcel.setDataPosition(0)

        // Reconstruct object from parcel and asserts:
        val result = creator.createFromParcel(parcel)
        parcel.recycle()
        return result
    }

    fun setFinalStatic(field: Field, newValue: Any?) {
        field.isAccessible = true
        val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, newValue)
    }

    fun changeSDKVersion(value: Int) {
        setFinalStatic(Build.VERSION::class.java.getField("SDK_INT"), value)
    }

}