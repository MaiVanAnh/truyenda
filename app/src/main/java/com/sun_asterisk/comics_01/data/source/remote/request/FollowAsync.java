package com.sun_asterisk.comics_01.data.source.remote.request;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import com.sun_asterisk.comics_01.utils.Constant;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class FollowAsync extends AsyncTask<String, Void, String> {
    private OnListener mListener;
    private int mIdComic;
    private Exception mException;
    private String mMethod;

    public FollowAsync(int idComic, OnListener listener, String method) {
        mIdComic = idComic;
        mListener = listener;
        mMethod = method;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        JSONObject jsonObject = new JSONObject();
        try {
            if (mIdComic != 0) {
                jsonObject.put(Constant.ID_TRUYEN, mIdComic);
                return RequestHandler.sendFollowWithToken(url, jsonObject, mMethod);
            } else {
                return RequestHandler.sendFollowWithToken(url, null, mMethod);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            mException = e;
        } catch (Exception e) {
            e.printStackTrace();
            mException = e;
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
            if (ParseUserWithJson.isConnectSuccess(data)) {
                mListener.onSuccess();
            } else {
                mListener.onFailure(new Exception());
            }
        }
    }
}
