package com.sun_asterisk.comics_01.screen.comic;

import com.sun_asterisk.comics_01.data.model.BookMark;
import com.sun_asterisk.comics_01.data.model.Chapter;
import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ChapterRepository;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;
import java.util.List;

public class ComicDetailPresenter implements ComicDetailContract.Presenter {
    private ComicDetailContract.View mView;
    private ChapterRepository mChapterRepository;
    private ComicRepository mComicRepository;
    private int mIdComic;

    public ComicDetailPresenter(ChapterRepository chapterRepository,
            ComicRepository comicRepository, int idComic) {
        mChapterRepository = chapterRepository;
        mComicRepository = comicRepository;
        mIdComic = idComic;
    }

    @Override
    public void getChapters() {
        mChapterRepository.getChapters(mIdComic, new OnFetchDataJsonListener<Chapter>() {
            @Override
            public void onSuccess(List<Chapter> chapters) {
                if (chapters != null) mView.onGetChapterSuccess(chapters);
            }

            @Override
            public void onError(Exception exception) {
                mView.onError(exception);
            }
        });
    }

    @Override
    public void saveComicCurrent(Comic comic) {
        mComicRepository.saveComicCurrent(comic);
    }

    @Override
    public void checkFollow(int idComic) {
        mChapterRepository.checkFollow(idComic, new OnObjectListener<BookMark>() {
            @Override
            public void onSuccess(BookMark bookMark) {
                mView.onCheckFollowSuccess(bookMark);
            }

            @Override
            public void onFailure(Exception exception) {
                mView.onCheckFollowFail();
            }
        });
    }

    @Override
    public void follow(int idComic) {
        mChapterRepository.follow(idComic, new OnListener() {
            @Override
            public void onSuccess() {
                mView.onFollowSuccess();
            }

            @Override
            public void onFailure(Exception exception) {
                mView.onFollowFail();
            }
        });
    }

    @Override
    public void bookmark(int idChapter, int idComic) {
        mChapterRepository.bookmark(idChapter, idComic);
    }

    @Override
    public void unFollow(int idComic) {
        mChapterRepository.unFollow(idComic, new OnListener() {
            @Override
            public void onSuccess() {
                mView.onUnFollowSuccess();
            }

            @Override
            public void onFailure(Exception exception) {
                mView.onUnFollowFail();
            }
        });
    }

    @Override
    public void setView(ComicDetailContract.View view) {
        mView = view;
    }
}
