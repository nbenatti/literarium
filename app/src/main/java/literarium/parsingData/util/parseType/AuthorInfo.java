package com.example.com.parsingData.parseType;

import android.os.Parcel;
import android.os.Parcelable;

public final class AuthorInfo implements Parcelable {

    private final int id;
    private final String name;

    public AuthorInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(id);
    }

    public static final Creator<AuthorInfo> CREATOR = new Creator<AuthorInfo>() {
        @Override
        public AuthorInfo createFromParcel(Parcel in) {
            return new AuthorInfo(in);
        }

        @Override
        public AuthorInfo[] newArray(int size) {
            return new AuthorInfo[size];
        }
    };

    private AuthorInfo(Parcel parcel) {

        name = parcel.readString();
        id = parcel.readInt();
    }

    @Override
    public String toString() {
        return "AuthorInfo{" +
                "name='" + name + '\'' +
                '}';
    }
}
