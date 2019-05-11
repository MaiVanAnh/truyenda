package com.sun_asterisk.comics_01.screen.bookshelf.subfragment.follow;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.screen.BasePresenter;
import java.util.List;

/**
 * Created by Mai Van Anh on 11/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public interface FollowContract {
    interface View {
        void onGetComicFollowSuccess(List<Comic> data);

        void onGetComicFollowFail(Exception exception);

        void onDeleteComicFollowSuccess();

        void onDeleteComicFollowFail();
    }

    interface Presenter extends BasePresenter<View> {
        void getComicFollow();

        void deleteComicFollow(int idComic);
    }
}
