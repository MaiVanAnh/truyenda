package com.sun_asterisk.comics_01.screen.signup;

import com.sun_asterisk.comics_01.data.repository.UserRepository;
import com.sun_asterisk.comics_01.data.source.remote.OnSignUpListener;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 05/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class SignUpPresenter implements SignUpContract.Presenter {

    private SignUpContract.View mView;
    private UserRepository mUserRepository;

    SignUpPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void signUp(JSONObject jsonObject) {
        mUserRepository.signUp(jsonObject, new OnSignUpListener() {
            @Override
            public void onSuccess() {
                mView.onSignUpSuccess();
            }

            @Override
            public void onFailure(Exception exception) {
                mView.onSignUpFailure(exception);
            }
        });
    }

    @Override
    public void setView(SignUpContract.View view) {
        mView = view;
    }
}
