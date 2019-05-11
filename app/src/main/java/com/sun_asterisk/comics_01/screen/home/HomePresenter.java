package com.sun_asterisk.comics_01.screen.home;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import java.util.List;

final public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private ComicRepository mComicRepository;

    HomePresenter(ComicRepository comicRepository) {
        mComicRepository = comicRepository;
    }

    @Override
    public void getComics() {
        mComicRepository.getComics(new OnFetchDataJsonListener<Comic>() {
            @Override
            public void onSuccess(List<Comic> comics) {
                mView.onGetComicsSuccess(comics);
            }

            @Override
            public void onError(Exception exception) {
                mView.onGetComicsError(exception);
            }
        });
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }
}
