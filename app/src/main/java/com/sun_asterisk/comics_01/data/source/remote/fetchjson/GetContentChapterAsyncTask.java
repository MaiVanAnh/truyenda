package com.sun_asterisk.comics_01.data.source.remote.fetchjson;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class GetContentChapterAsyncTask extends AsyncTask<String, Void, String> {
    private OnFetchDataJsonListener<String> mListener;
    private Exception mException;

    public GetContentChapterAsyncTask(OnFetchDataJsonListener<String> listener) {
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
        super.onPostExecute(data);
        if (mException != null) {
            mListener.onError(mException);
        } else {
            if (data != null) {
                mListener.onSuccess(ParseDataWithJson.parseJsonToContentChapterList(data));
            }
        }
    }
}
