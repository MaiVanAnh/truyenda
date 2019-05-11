package com.sun_asterisk.comics_01.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Author implements Parcelable {
    private int mAuthorId;
    private String mAuthorName;

    public Author() {
    }

    private Author(AuthorBuilder authorBuilder) {
        mAuthorId = authorBuilder.mAuthorId;
        mAuthorName = authorBuilder.mAuthorName;
    }

    protected Author(Parcel in) {
        mAuthorId = in.readInt();
        mAuthorName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mAuthorId);
        dest.writeString(mAuthorName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    public int getAuthorId() {
        return mAuthorId;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public static class AuthorBuilder {
        private int mAuthorId;
        private String mAuthorName;

        public AuthorBuilder() {
        }

        public AuthorBuilder authorId(int authorId) {
            mAuthorId = authorId;
            return this;
        }

        public AuthorBuilder authorName(String authorName) {
            mAuthorName = authorName;
            return this;
        }

        public Author build() {
            return new Author(this);
        }
    }

    public static class AuthorEntry {
        public static final String AUTHOR_LIST = "DanhSachTacGia";
        public static final String AUTHOR_ID = "Id";
        public static final String AUTHOR_NAME = "TenTacGia";
    }
}
