package com.sun_asterisk.comics_01.data.source.remote.fetchjson;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.data.source.remote.request.RequestHandler;

/**
 * Created by Mai Van Anh on 11/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class GetComicFollowAsync extends AsyncTask<String, Void, String> {
    private Exception mException;
    private OnFetchDataJsonListener<Comic> mListener;

    GetComicFollowAsync(OnFetchDataJsonListener<Comic> listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        try {
            data = RequestHandler.sendGetFollow(strings[0]);
        } catch (Exception exception) {
            mException = exception;
        }
        return data;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        if (mException != null) {
            mListener.onError(mException);
        } else {
            if (data != null) {
                mListener.onSuccess(ParseDataWithJson.parseJsonToComicFollowList(data));
            } else {
                mListener.onError(new Exception(""));
            }
        }
    }
}
