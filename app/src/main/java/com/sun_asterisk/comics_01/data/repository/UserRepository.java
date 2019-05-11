package com.sun_asterisk.comics_01.data.repository;

import com.sun_asterisk.comics_01.data.source.UserDataSource;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import com.sun_asterisk.comics_01.data.source.remote.OnSignUpListener;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 03/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class UserRepository {
    private static UserRepository sInstance;
    private UserDataSource.LocalDataSource mLocalDataSource;
    private UserDataSource.RemoteDataSource mRemoteDataSource;

    private UserRepository(UserDataSource.LocalDataSource localDataSource,
            UserDataSource.RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static UserRepository getInstance(UserDataSource.LocalDataSource localDataSource,
            UserDataSource.RemoteDataSource remoteDataSource) {
        if (sInstance == null) sInstance = new UserRepository(localDataSource, remoteDataSource);
        return sInstance;
    }

    public void login(String userName, String password, OnObjectListener onObjectListener) {
        mRemoteDataSource.login(userName, password, onObjectListener);
    }

    public void signUp(JSONObject jsonObject, OnSignUpListener onSignUpListener) {
        mRemoteDataSource.signUp(jsonObject, onSignUpListener);
    }

    public void logOut(OnListener onListener) {
        mRemoteDataSource.logOut(onListener);
    }

    public void changeInformation(JSONObject jsonObject, OnListener onListener) {
        mRemoteDataSource.changeInformation(jsonObject, onListener);
    }

    public void getAccount(String token, OnObjectListener listener) {
        mRemoteDataSource.getAccount(token, listener);
    }

    public void sendEmailResetPassword(String email, OnObjectListener<String> mListener) {
        mRemoteDataSource.sendEmailResetPassword(email, mListener);
    }
}
