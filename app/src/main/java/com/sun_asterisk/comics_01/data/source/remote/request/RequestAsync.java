package com.sun_asterisk.comics_01.data.source.remote.request;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;
import com.sun_asterisk.comics_01.utils.Constant;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 29/04/2019.
 * majanhqn@gmail.com
 */
public class RequestAsync extends AsyncTask<JSONObject, Void, String> {
    private OnObjectListener mListener;

    RequestAsync(OnObjectListener listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {
        JSONObject jsonObject = jsonObjects[0];
        try {
            return RequestHandler.sendPost(Constant.BASE_URL + Constant.LOGIN, jsonObject);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            mListener.onFailure(new Exception("Login Failed"));
        } else {

            if (!ParseUserWithJson.isConnectSuccess(result)) {
                mListener.onFailure(new Exception(ParseUserWithJson.getMessageError(result)));
            } else {
                mListener.onSuccess(ParseUserWithJson.parseJsonToUser(result));
            }
        }
    }
}
