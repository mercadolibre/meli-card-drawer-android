package com.meli.android.carddrawer;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import com.meli.android.carddrawer.format.TypefaceHelper;
import org.robolectric.util.ReflectionHelpers;

import static org.mockito.Mockito.mock;

/**
 * Utility class for tests
 */
public final class TestUtils {

    private TestUtils() {
        //not called
    }

    public static void initTypefaceSetter() {
        // Run the fonts fetching command in the current thread's looper. This way we avoid
        // async waitings and race conditions
        ReflectionHelpers.setStaticField(TypefaceHelper.class, "robotoMono", mock(Typeface.class));
    }

    /**
     * Utility method to test that a parcelable object is being properly parceled and unparceled
     *
     * @param <T> This describes my type parameter
     * @param objectToTest the parcelable object
     * @param creator      the parcelable creator for that class
     * @return             Clone of the param parcelable
     */
    public static <T extends Parcelable> T cloneParcelable(T objectToTest, Parcelable.Creator<T> creator) {
        // Obtain a Parcel object and write the parcelable object to it:
        Parcel parcel = Parcel.obtain();
        objectToTest.writeToParcel(parcel, 0);

        // After we're done with writing, we need to reset the parcel for reading:
        parcel.setDataPosition(0);

        // Reconstruct object from parcel and asserts:
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }
}