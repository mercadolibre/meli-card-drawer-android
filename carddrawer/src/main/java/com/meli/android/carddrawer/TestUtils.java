package com.meli.android.carddrawer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Utility class for tests
 */

public final class TestUtils {

    private TestUtils() {
        //not called
    }

    /**
     * Utility method to test that a parcelable object is being properly parceled and unparceled
     *
     * @param objectToTest the parcelable object
     * @param creator      the parcelable creator for that class
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

