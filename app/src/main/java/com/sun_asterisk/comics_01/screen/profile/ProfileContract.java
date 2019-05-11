package com.sun_asterisk.comics_01.screen.profile;

import com.sun_asterisk.comics_01.screen.BasePresenter;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 09/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public interface ProfileContract {
    interface View {
        void onLogOutSuccess();
        void onLogOutFailure();
        void onChangeInformationSuccess();
        void onChangeInformationFailure(Exception exception);
    }

    interface Presenter extends BasePresenter<View> {
        void logOut();
        void changeInformation(JSONObject jsonObject);
    }
}
