package com.sun_asterisk.comics_01.screen.bookshelf.subfragment.readhistory;

import com.sun_asterisk.comics_01.data.model.Comic;
import java.util.List;

public interface ReadHistoryContract {
    interface View {
    }

    interface Presenter {
        List<Comic> getReadHistory();
        void deleteReadHistory(int idComic);
    }
}
