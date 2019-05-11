package com.sun_asterisk.comics_01.data.source.remote.request;

import com.sun_asterisk.comics_01.data.model.BookMark;
import com.sun_asterisk.comics_01.data.model.User;
import com.sun_asterisk.comics_01.utils.Constant;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 04/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
class ParseUserWithJson {

    static User parseJsonToUser(String data) {
        User user = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonObjectData = jsonObject.optJSONObject(User.UserEntry.DATA);
            String token = jsonObjectData.optString(User.UserEntry.TOKEN);
            JSONObject jsonObjectProfile = jsonObjectData.optJSONObject(User.UserEntry.PROFILE);
            user = new User.UserBuilder().userName(
                    jsonObjectProfile.optString(User.UserEntry.USER_NAME))
                    .name(jsonObjectProfile.optString(User.UserEntry.NAME))
                    .gender(jsonObjectProfile.optBoolean(User.UserEntry.GENDER))
                    .date(jsonObjectProfile.optString(User.UserEntry.DATE))
                    .email(jsonObjectProfile.optString(User.UserEntry.EMAIL))
                    .token(token)
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    static Boolean isConnectSuccess(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.optInt(Constant.CODE);
            if (code == 200) return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    static String getMessageError(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            return jsonObject.optString(Constant.MESSAGE_ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User parseJsonToGetUser(String result) {
        User user = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObjectData = jsonObject.optJSONObject(User.UserEntry.DATA);
            String token = jsonObjectData.optString(User.UserEntry.TOKEN);
            user = new User.UserBuilder().userName(
                    jsonObjectData.optString(User.UserEntry.USER_NAME))
                    .name(jsonObjectData.optString(User.UserEntry.NAME))
                    .gender(jsonObjectData.optBoolean(User.UserEntry.GENDER))
                    .date(jsonObjectData.optString(User.UserEntry.DATE))
                    .email(jsonObjectData.optString(User.UserEntry.EMAIL))
                    .expired(jsonObjectData.optString(User.UserEntry.EXPIRED))
                    .token(token)
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    static BookMark getBookMark(String data) {
        BookMark bookMark = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonObjectData = jsonObject.getJSONObject(BookMark.BookMarkEntry.DATA);
            bookMark = new BookMark.BookMarkBuilder().idChapter(
                    jsonObjectData.optInt(BookMark.BookMarkEntry.ID_CHAPTER_BOOKMARK))
                    .nameChapter(
                            jsonObjectData.optString(BookMark.BookMarkEntry.NAME_CHAPTER_BOOKMARK))
                    .build();
            return bookMark;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookMark;
    }
}
