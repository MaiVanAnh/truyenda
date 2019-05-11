package com.sun_asterisk.comics_01.screen.main;

import com.sun_asterisk.comics_01.data.model.User;
import com.sun_asterisk.comics_01.screen.BasePresenter;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public interface MainContract {
    interface View {
        void onGetAccountSuccess(User user);
        void onGetAccountFail(Exception exception);
    }
    interface Presenter extends BasePresenter<View> {
        void getAccount(String token);
    }
}
