package com.sun_asterisk.comics_01.screen.login;

import com.sun_asterisk.comics_01.data.model.User;
import com.sun_asterisk.comics_01.screen.BasePresenter;

/**
 * Created by Mai Van Anh on 30/04/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public interface LoginContract {
    interface View {
        void onLoginSuccess(User user);

        void onLoginFail(Exception exception);

        void onSendEmailSuccess(String message);

        void onSendEmailFail(Exception exception);
    }

    interface Presenter extends BasePresenter<View> {
        void login(String userName, String password);

        void sendEmailResetPassword(String email);
    }
}
