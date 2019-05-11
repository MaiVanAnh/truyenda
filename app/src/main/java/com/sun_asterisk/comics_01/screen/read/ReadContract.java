package com.sun_asterisk.comics_01.screen.read;

import com.sun_asterisk.comics_01.screen.BasePresenter;
import java.util.List;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public interface ReadContract {
    interface View {
        void onLoadContentSuccess(List<String> data);

        void onLoadContentFail(Exception exception);
    }

    interface Presenter extends BasePresenter<View> {
        void getContentChapter(int idChapter);
    }
}
