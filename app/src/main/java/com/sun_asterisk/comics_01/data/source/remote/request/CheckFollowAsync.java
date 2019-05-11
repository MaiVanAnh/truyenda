package com.sun_asterisk.comics_01.data.source.remote.request;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.model.BookMark;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class CheckFollowAsync extends AsyncTask<String, Void, String> {
    private OnObjectListener<BookMark> mListener;
    private Exception mException;

    CheckFollowAsync(OnObjectListener<BookMark> listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        try {
            return RequestHandler.sendGetFollow(url);
        } catch (Exception exception) {
            mException = exception;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        if (data == null) {
            mListener.onFailure(new Exception());
        } else if (mException != null) {
            mListener.onFailure(mException);
        } else {
            BookMark bookMark = ParseUserWithJson.getBookMark(data);
            if (bookMark == null) {
                mListener.onFailure(new Exception());
            } else {
                mListener.onSuccess(bookMark);
            }
        }
    }
}
