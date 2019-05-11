package com.sun_asterisk.comics_01.data.source.remote.fetchjson;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.model.Chapter;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;

public class GetChapterAsyncTask extends AsyncTask<String, Void, String> {
    private OnFetchDataJsonListener<Chapter> mListener;
    private Exception mException;

    GetChapterAsyncTask(OnFetchDataJsonListener<Chapter> listener) {
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
                mListener.onSuccess(ParseDataWithJson.parseJsonToChapterList(data));
            }
        }
    }
}
