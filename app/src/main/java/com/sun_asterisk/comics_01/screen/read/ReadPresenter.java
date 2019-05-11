package com.sun_asterisk.comics_01.screen.read;

import com.sun_asterisk.comics_01.data.repository.ChapterRepository;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import java.util.List;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class ReadPresenter implements ReadContract.Presenter {
    private ReadContract.View mView;
    private ChapterRepository mChapterRepository;

    public ReadPresenter(ChapterRepository chapterRepository) {
        mChapterRepository = chapterRepository;
    }

    @Override
    public void getContentChapter(int idChapter) {
        mChapterRepository.getContentChapter(idChapter, new OnFetchDataJsonListener<String>() {
            @Override
            public void onSuccess(List<String> data) {
                mView.onLoadContentSuccess(data);
            }

            @Override
            public void onError(Exception exception) {
                mView.onLoadContentFail(exception);
            }
        });
    }

    @Override
    public void setView(ReadContract.View view) {
        mView = view;
    }
}
