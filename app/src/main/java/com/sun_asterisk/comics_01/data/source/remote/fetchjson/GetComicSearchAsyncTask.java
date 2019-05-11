package com.sun_asterisk.comics_01.data.source.remote.fetchjson;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;

/**
 * Created by Mai Van Anh on 03/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class GetComicSearchAsyncTask extends AsyncTask<String, Void, String> {
    private Exception mException;
    private OnFetchDataJsonListener<Comic> mListener;

    GetComicSearchAsyncTask(OnFetchDataJsonListener<Comic> listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        try {
            data = ParseDataWithJson.getJsonFromUrl(strings[0]);
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
                mListener.onSuccess(ParseDataWithJson.parseJsonSearchToComicList(data));
            }
        }
    }
}
