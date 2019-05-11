package com.sun_asterisk.comics_01.data.model;

import java.util.HashMap;

/**
 * Created by Mai Van Anh on 09/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class Cache {
    private final static String KEY_USER_NAME = "Username";
    private final static String KEY_NAME = "Ten";
    private final static String KEY_GENDER = "GioiTinh";
    private final static String KEY_BIRTHDAY = "NgaySinh";
    private final static String KEY_EMAIL = "Email";
    private final static String KEY_TOKEN = "Token";
    private final static String KEY_PASSWORD = "Password";

    public Boolean isLogin;
    private HashMap<String, Object> mCached;

    private static Cache sInstance;

    private Cache() {
        isLogin = false;
        mCached = new HashMap<>();
    }

    public static Cache getInstance() {
        if (sInstance == null) sInstance = new Cache();
        return sInstance;
    }

    public void cacheUserName(String userName) {
        mCached.put(KEY_USER_NAME, userName);
    }

    public String getUserName() {
        return (String) mCached.get(KEY_USER_NAME);
    }

    public void cachedName(String name) {
        mCached.put(KEY_NAME, name);
    }

    public String getName() {
        return (String) mCached.get(KEY_NAME);
    }

    public void cachedGender(Boolean gender) {
        mCached.put(KEY_GENDER, gender);
    }

    public Boolean getGender() {
        return (Boolean) mCached.get(KEY_GENDER);
    }

    public void cachedBirthDay(String birthday) {
        mCached.put(KEY_BIRTHDAY, birthday);
    }

    public String getBirthDay() {
        return (String) mCached.get(KEY_BIRTHDAY);
    }

    public void cachedEmail(String birthday) {
        mCached.put(KEY_EMAIL, birthday);
    }

    public String getEmail() {
        return (String) mCached.get(KEY_EMAIL);
    }

    public void cachedToken(String birthday) {
        mCached.put(KEY_TOKEN, birthday);
    }

    public String getToken() {
        return (String) mCached.get(KEY_TOKEN);
    }

    public void cachedPassword(String password) {
        mCached.put(KEY_PASSWORD, password);
    }

    public String getPassword() {
        return (String) mCached.get(KEY_PASSWORD);
    }
}
