package com.sun_asterisk.comics_01.screen.profile;

import com.sun_asterisk.comics_01.data.repository.UserRepository;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 09/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View mView;
    private UserRepository mUserRepository;

    public ProfilePresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void logOut() {
        mUserRepository.logOut(new OnListener() {
            @Override
            public void onSuccess() {
                mView.onLogOutSuccess();
            }

            @Override
            public void onFailure(Exception exception) {
                mView.onLogOutFailure();
            }
        });
    }

    @Override
    public void changeInformation(JSONObject jsonObject) {
        mUserRepository.changeInformation(jsonObject, new OnListener() {
            @Override
            public void onSuccess() {
                mView.onChangeInformationSuccess();
            }

            @Override
            public void onFailure(Exception exception) {
                mView.onChangeInformationFailure(exception);
            }
        });
    }

    @Override
    public void setView(ProfileContract.View view) {
        mView = view;
    }
}
