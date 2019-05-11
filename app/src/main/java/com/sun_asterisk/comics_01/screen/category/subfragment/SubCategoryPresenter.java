package com.sun_asterisk.comics_01.screen.category.subfragment;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import java.util.List;

public class SubCategoryPresenter implements SubCategoryContract.Presenter {
    private SubCategoryContract.View mView;
    private ComicRepository mComicRepository;

    public SubCategoryPresenter(ComicRepository comicRepository) {
        mComicRepository = comicRepository;
    }

    @Override
    public void getComicFollowCategory(int id) {
        mComicRepository.getComicByCategory(id, new OnFetchDataJsonListener<Comic>() {
            @Override
            public void onSuccess(List<Comic> comics) {
                if (!comics.isEmpty()) {
                    mView.onGetComicFollowCategorySuccess(comics);
                } else {
                    mView.onNoComicFollowCategoryAvailable();
                }
            }

            @Override
            public void onError(Exception exception) {
                if (exception != null) mView.onGetComicFollowCategoryError(exception);
            }
        });
    }

    @Override
    public void setView(SubCategoryContract.View view) {
        mView = view;
    }
}
