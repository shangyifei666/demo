package com.example.demo.AIDL;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Book  implements Parcelable {
    private int bookId;
    private String bookName;

    public Book(int id, String name){
        this.bookId = id;
        this.bookName = name;
    }

    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    @NonNull
    @Override
    public String toString() {
        return "[" +
                "bookId : " + bookId +
                ", bookName : " + bookName +
                "]";
    }
}
