package com.example.translation.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Model_Book implements Parcelable {
   int id ;
   private String note ;
   private String title;
   private String lock;
   private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Model_Book(int id, String note, String title, String lock , String date) {
        this.id = id;
        this.note = note;
        this.title = title;
        this.lock = lock;
        this.date = date ;
    }


    protected Model_Book(Parcel in) {
        id = in.readInt();
        note = in.readString();
        title = in.readString();
        lock = in.readString();
        date = in.readString();
    }

    public static final Creator<Model_Book> CREATOR = new Creator<Model_Book>() {
        @Override
        public Model_Book createFromParcel(Parcel in) {
            return new Model_Book(in);
        }

        @Override
        public Model_Book[] newArray(int size) {
            return new Model_Book[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public Model_Book(String note, String title, String lock) {
        this.note = note;
        this.title = title;
        this.lock = lock;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(note);
        parcel.writeString(title);
        parcel.writeString(lock);
        parcel.writeString(date);
    }
}
