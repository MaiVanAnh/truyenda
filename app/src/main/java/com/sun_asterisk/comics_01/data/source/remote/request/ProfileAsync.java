package com.sun_asterisk.comics_01.data.source.remote.request;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class ProfileAsync extends AsyncTask<String, Void, String> {
    private OnListener mSignOutListener;
    private Exception mException;
    private JSONObject mJSONObject;

    ProfileAsync(JSONObject jSONObject, OnListener signOutListener) {
        mJSONObject = jSONObject;
        mSignOutListener = signOutListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlStr = strings[0];
        try {
            if (mJSONObject != null) {
                return RequestHandler.sendPutWithToken(urlStr, mJSONObject);
            }
            return RequestHandler.sendFollowWithToken(urlStr);
        } catch (Exception exception) {
            mException = exception;
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        if (data == null && mException != null) {
            mSignOutListener.onFailure(mException);
        } else {
            if (!ParseUserWithJson.isConnectSuccess(data)) {
                mSignOutListener.onFailure(new Exception(ParseUserWithJson.getMessageError(data)));
            } else {
                mSignOutListener.onSuccess();
            }
        }
    }
}
