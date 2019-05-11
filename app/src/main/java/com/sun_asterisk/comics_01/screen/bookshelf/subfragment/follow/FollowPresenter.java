package com.sun_asterisk.comics_01.screen.bookshelf.subfragment.follow;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import java.util.List;

/**
 * Created by Mai Van Anh on 11/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class FollowPresenter implements FollowContract.Presenter {
    private FollowContract.View mView;
    private ComicRepository mComicRepository;

    public FollowPresenter(ComicRepository comicRepository) {
        mComicRepository = comicRepository;
    }

    @Override
    public void getComicFollow() {
        mComicRepository.getComicFollow(new OnFetchDataJsonListener<Comic>() {
            @Override
            public void onSuccess(List<Comic> data) {
                if (!data.isEmpty()) mView.onGetComicFollowSuccess(data);
            }

            @Override
            public void onError(Exception e) {
                mView.onGetComicFollowFail(e);
            }
        });
    }

    @Override
    public void deleteComicFollow(int idComic) {
        mComicRepository.deleteComicFollow(idComic, new OnListener() {
            @Override
            public void onSuccess() {
                mView.onDeleteComicFollowSuccess();
            }

            @Override
            public void onFailure(Exception exception) {
                mView.onDeleteComicFollowFail();
            }
        });
    }

    @Override
    public void setView(FollowContract.View view) {
        mView = view;
    }
}
