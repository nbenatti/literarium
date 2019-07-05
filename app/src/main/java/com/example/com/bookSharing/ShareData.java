package com.example.com.bookSharing;

import android.os.Parcel;
import android.os.Parcelable;

public class ShareData implements Parcelable {

    private static final String TAG = ShareData.class.getSimpleName();

    private String userId;
    private String bookId;

    public ShareData(String userId, String bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookId() {
        return bookId;
    }

    @Override
    public String toString() {
        return "ShareData{" +
                "userId='" + userId + '\'' +
                ", bookId='" + bookId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(userId);
        out.writeString(bookId);
    }

    public static final Creator<ShareData> CREATOR = new Creator<ShareData>() {
        @Override
        public ShareData createFromParcel(Parcel in) {
            return new ShareData(in);
        }

        @Override
        public ShareData[] newArray(int size) {
            return new ShareData[size];
        }
    };

    private ShareData(Parcel parcel) {

        userId = parcel.readString();
        bookId = parcel.readString();
    }
}
