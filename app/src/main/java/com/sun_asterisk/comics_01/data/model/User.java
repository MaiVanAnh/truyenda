package com.sun_asterisk.comics_01.data.model;

import android.os.Parcel;

/**
 * Created by Mai Van Anh on 03/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class User {
    private String mUserName;
    private String mName;
    private Boolean mGender;
    private String mDate;
    private String mEmail;
    private String mToken;
    private String mExpired;

    private User(UserBuilder userBuilder) {
        mUserName = userBuilder.mUserName;
        mName = userBuilder.mName;
        mGender = userBuilder.mGender;
        mDate = userBuilder.mDate;
        mEmail = userBuilder.mEmail;
        mToken = userBuilder.mToken;
        mExpired = userBuilder.mExpired;
    }

    protected User(Parcel in) {
        mUserName = in.readString();
        mName = in.readString();
        byte tmpMGender = in.readByte();
        mGender = tmpMGender == 0 ? null : tmpMGender == 1;
        mDate = in.readString();
        mEmail = in.readString();
        mToken = in.readString();
    }

    public String getUserName() {
        return mUserName;
    }

    public String getName() {
        return mName;
    }

    public Boolean getGender() {
        return mGender;
    }

    public String getDate() {
        return mDate;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getToken() {
        return mToken;
    }

    public String getExpired() {
        return mExpired;
    }

    public static class UserBuilder {
        private String mUserName;
        private String mName;
        private Boolean mGender;
        private String mDate;
        private String mEmail;
        private String mToken;
        private String mExpired;

        public UserBuilder() {
        }

        public UserBuilder userName(String userName) {
            mUserName = userName;
            return this;
        }

        public UserBuilder name(String nameStr) {
            mName = nameStr;
            return this;
        }

        public UserBuilder gender(Boolean gender) {
            mGender = gender;
            return this;
        }

        public UserBuilder date(String date) {
            mDate = date;
            return this;
        }

        public UserBuilder email(String email) {
            mEmail = email;
            return this;
        }

        public UserBuilder token(String token) {
            mToken = token;
            return this;
        }

        public UserBuilder expired(String expired) {
            mExpired = expired;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public final class UserEntry {
        public static final String USER_NAME = "Username";
        public static final String NAME = "Ten";
        public static final String GENDER = "GioiTinh";
        public static final String DATE = "NgaySinh";
        public static final String EMAIL = "Email";
        public static final String TOKEN = "Token";
        public static final String DATA = "Data";
        public static final String PROFILE = "Profile";
        public static final String EXPIRED = "NgayHetHan";
    }
}
