package com.sun_asterisk.comics_01.data.source.remote;

import com.sun_asterisk.comics_01.data.source.UserDataSource;
import com.sun_asterisk.comics_01.data.source.remote.request.GetRequest;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 03/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class UserRemoteDataSource implements UserDataSource.RemoteDataSource {
    private static UserRemoteDataSource sInstance;

    private UserRemoteDataSource() {
    }

    public static UserRemoteDataSource getInstance() {
        if (sInstance == null) sInstance = new UserRemoteDataSource();
        return sInstance;
    }

    @Override
    public void login(String username, String password, OnObjectListener listener) {
        new GetRequest(listener).login(username, password);
    }

    @Override
    public void signUp(JSONObject jsonObject, OnSignUpListener onSignUpListener) {
        new GetRequest(onSignUpListener).signUp(jsonObject);
    }

    @Override
    public void logOut(OnListener onListener) {
        new GetRequest(onListener).signOut();
    }

    @Override
    public void changeInformation(JSONObject jsonObject, OnListener onListener) {
        new GetRequest(onListener).changeInformation(jsonObject);
    }

    @Override
    public void getAccount(String token, OnObjectListener listener) {
        new GetRequest(listener).getAccount(token);
    }

    @Override
    public void sendEmailResetPassword(String email, OnObjectListener<String> listener) {
        new GetRequest(listener).sendEmailResetPassword(email);
    }
}
