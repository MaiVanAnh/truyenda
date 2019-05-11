package com.sun_asterisk.comics_01.screen.login;

import com.sun_asterisk.comics_01.data.model.User;
import com.sun_asterisk.comics_01.data.repository.UserRepository;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;

/**
 * Created by Mai Van Anh on 30/04/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    private UserRepository mUserRepository;

    public LoginPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void login(String userName, String password) {
        mUserRepository.login(userName, password, new OnObjectListener<User>() {
            @Override
            public void onSuccess(User user) {
                mView.onLoginSuccess(user);
            }

            @Override
            public void onFailure(Exception exception) {
                mView.onLoginFail(exception);
            }
        });

    }

    @Override
    public void sendEmailResetPassword(String email) {
        mUserRepository.sendEmailResetPassword(email, new OnObjectListener<String>() {
            @Override
            public void onSuccess(String message) {
                mView.onSendEmailSuccess(message);
            }

            @Override
            public void onFailure(Exception exception) {
                mView.onSendEmailFail(exception);
            }
        });
    }

    @Override
    public void setView(LoginContract.View view) {
        mView = view;
    }
}
