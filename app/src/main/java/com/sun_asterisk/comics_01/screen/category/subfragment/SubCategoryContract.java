package com.sun_asterisk.comics_01.screen.category.subfragment;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.screen.BasePresenter;
import java.util.List;

public interface SubCategoryContract {
    interface View {
        void onGetComicFollowCategorySuccess(List<Comic> comics);
        void onGetComicFollowCategoryError(Exception exception);
        void onNoComicFollowCategoryAvailable();
    }

    interface Presenter extends BasePresenter<View> {
        void getComicFollowCategory(int id);
    }
}
