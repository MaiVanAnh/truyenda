package com.sun_asterisk.comics_01.data.source.remote.request;

import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;
import com.sun_asterisk.comics_01.data.source.remote.OnSignUpListener;
import com.sun_asterisk.comics_01.utils.Constant;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 04/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class GetRequest {
    private OnObjectListener mOnObjectListener;
    private OnSignUpListener mSignUpListener;
    private OnListener mOnListener;

    public GetRequest() {
    }

    public GetRequest(OnObjectListener onObjectListener) {
        mOnObjectListener = onObjectListener;
    }

    public GetRequest(OnSignUpListener signUpListener) {
        mSignUpListener = signUpListener;
    }

    public GetRequest(OnListener onListener) {
        mOnListener = onListener;
    }

    public void login(String userName, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constant.USER_NAME, userName);
            jsonObject.put(Constant.PASSWORD, password);
        } catch (JSONException exception) {
            mOnObjectListener.onFailure(new Exception("Login Failed"));
            exception.printStackTrace();
        }
        new RequestAsync(mOnObjectListener).execute(jsonObject);
    }

    public void signUp(JSONObject jsonObject) {
        new SignUpAsync(mSignUpListener).execute(jsonObject);
    }

    public void signOut() {
        String url = Constant.BASE_URL + Constant.LOGOUT;
        new ProfileAsync(null, mOnListener).execute(url);
    }

    public void changeInformation(JSONObject jsonObject) {
        String url = Constant.BASE_URL + Constant.ACCOUNTS + Constant.MY;
        new ProfileAsync(jsonObject, mOnListener).execute(url);
    }

    public void getAccount(String token) {
        new GetAccountAsync(mOnObjectListener).execute(token);
    }

    public void checkFollow(int idComic) {
        String url = Constant.BASE_URL + Constant.BOOKMARKS + "/" + Constant.STRORY + idComic;
        new CheckFollowAsync(mOnObjectListener).execute(url);
    }

    public void bookmark(int idChapter, int idComic) {
        String url = Constant.BASE_URL + Constant.BOOKMARKS;
        new BookMarkAsync(idChapter, idComic).execute(url);
    }

    public void follow(int idComic) {
        String url = Constant.BASE_URL + Constant.BOOKMARKS;
        new FollowAsync(idComic, mOnListener, RequestHandler.METHOD_POST).execute(url);
    }

    public void unFollow(int idComic) {
        String url = Constant.BASE_URL + Constant.BOOKMARKS + "/" + Constant.STRORY + idComic;
        new FollowAsync(0, mOnListener, RequestHandler.METHOD_DELETE).execute(url);
    }

    public void sendEmailResetPassword(String email) {
        String url = Constant.BASE_URL + Constant.FORGOT;
        new SendMailAsync(email, mOnObjectListener).execute(url);
    }
}
