package com.meli.android.carddrawer.format;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Transforms the input text, grouping characters according to a pattern.
 * The processed text is always the max size, filling remaining space.
 */
public class NumberFormatter implements Parcelable {


    private static final String NON_VALID = "[^*0-9]";

    protected final int[] pattern;
    private static final String SEPARATOR = "  ";
    private static final String FILLER = "*";

    /**
     * @param pattern   grouping pattern to apply to input text
     */
    public NumberFormatter(int... pattern) {
        this.pattern = pattern;
    }

    /**
     * @param parcel
     */
    protected NumberFormatter(Parcel parcel) {
        pattern = parcel.createIntArray();
    }

    private String separateInGroups(@NonNull String input, boolean fill) {
        String cleaned = cleanTextForSubmit(input);
        StringBuilder formatted = new StringBuilder();
        int pos = 0;
        boolean shouldBreak = false;

        for (int i = 0; i < pattern.length && !shouldBreak; i++) {
            int groupSize = pattern[i];

            if (i > 0) {
                formatted.append(SEPARATOR);
            }

            for (int j = 0; j < groupSize && !shouldBreak; j++) {
                if (pos < cleaned.length()) {
                    formatted.append(cleaned.charAt(pos));
                } else if (fill) {
                    formatted.append(FILLER);
                } else {
                    shouldBreak = true;
                }

                pos++;
            }
        }

        return formatted.toString();
    }

    public String formatTextForVisualFeedback(@NonNull String input) {
        return separateInGroups(input, true);
    }

    public String formatEmptyText() {
        return separateInGroups(" ", true);
    }

    public String cleanTextForSubmit(@NonNull String input) {
        return input.replaceAll(NON_VALID, "");
    }


    // -------- Parcelable implementation

    public static final Creator<NumberFormatter> CREATOR = new Creator<NumberFormatter>() {
        @Override
        public NumberFormatter createFromParcel(Parcel parcel) {
            return new NumberFormatter(parcel);
        }

        @Override
        public NumberFormatter[] newArray(int size) {
            return new NumberFormatter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(pattern);
    }
}
