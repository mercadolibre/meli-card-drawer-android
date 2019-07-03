package com.meli.android.carddrawer.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Observable;

public class Card extends Observable implements Parcelable {
    private String number;
    private String name;
    private String expiration;
    private String secCode;

    public Card() {
        super();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(@NonNull final String number) {
        this.number = number;
        setChanged();
        notifyObservers();
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull final String name) {
        this.name = name;
        setChanged();
        notifyObservers();
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(@NonNull final String expiration) {
        this.expiration = expiration;
        setChanged();
        notifyObservers();
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(@NonNull final String secCode) {
        this.secCode = secCode;
        setChanged();
        notifyObservers();
    }

    public void fillCard(@NonNull final Card anotherCard) {
        number = anotherCard.getNumber();
        name = anotherCard.getName();
        expiration = anotherCard.getExpiration();
        secCode = anotherCard.getSecCode();
    }

    // **** Parcelable implementation *****

    @Override
    public int describeContents() {
        return 0;
    }

    protected Card(final Parcel in) {
        super();
        number = in.readString();
        name = in.readString();
        expiration = in.readString();
        secCode = in.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(number);
        dest.writeString(name);
        dest.writeString(expiration);
        dest.writeString(secCode);
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(final Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(final int size) {
            return new Card[size];
        }
    };
}
