package com.sun_asterisk.comics_01.screen.signup;

import com.sun_asterisk.comics_01.screen.BasePresenter;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 05/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
interface SignUpContract {
    interface View {
        void onSignUpSuccess();
        void onSignUpFailure(Exception exception);
    }

    interface Presenter extends BasePresenter<View> {
        void signUp(JSONObject jsonObject);
    }
}
