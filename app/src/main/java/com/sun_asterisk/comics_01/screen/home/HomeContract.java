package com.sun_asterisk.comics_01.screen.home;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.screen.BasePresenter;
import java.util.List;

public interface HomeContract {
    interface View {
        void onGetComicsSuccess(List<Comic> comics);

        void onGetComicsError(Exception exception);
    }

    interface Presenter extends BasePresenter<View> {
        void getComics();
    }
}
