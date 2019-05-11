package com.sun_asterisk.comics_01.screen.main;

import com.sun_asterisk.comics_01.data.model.User;
import com.sun_asterisk.comics_01.data.repository.UserRepository;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private UserRepository mUserRepository;

    public MainPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void getAccount(String token) {
        mUserRepository.getAccount(token, new OnObjectListener<User>() {
            @Override
            public void onSuccess(User user) {
                mView.onGetAccountSuccess(user);
            }

            @Override
            public void onFailure(Exception exception) {
                if (exception != null) mView.onGetAccountFail(exception);
            }
        });
    }

    @Override
    public void setView(MainContract.View view) {
        mView = view;
    }
}
