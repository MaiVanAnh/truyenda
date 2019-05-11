package com.sun_asterisk.comics_01.data.source;

import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import com.sun_asterisk.comics_01.data.source.remote.OnSignUpListener;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 03/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public interface UserDataSource {
    interface RemoteDataSource {
        void login(String username, String password, OnObjectListener listener);

        void signUp(JSONObject jsonObject, OnSignUpListener onSignUpListener);

        void logOut(OnListener onListener);

        void changeInformation(JSONObject jsonObject, OnListener onListener);

        void getAccount(String token, OnObjectListener listener);

        void sendEmailResetPassword(String email, OnObjectListener<String> mListener);
    }

    interface LocalDataSource {
    }
}
