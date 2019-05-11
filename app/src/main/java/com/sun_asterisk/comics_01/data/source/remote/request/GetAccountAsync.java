package com.sun_asterisk.comics_01.data.source.remote.request;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.model.User;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;
import com.sun_asterisk.comics_01.utils.Constant;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class GetAccountAsync extends AsyncTask<String, Void, String> {
    private OnObjectListener<User> mListener;

    GetAccountAsync(OnObjectListener<User> listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String token = strings[0];
        try {
            String url = Constant.BASE_URL + Constant.ACCOUNTS + Constant.MY;
            return RequestHandler.sendGet(url, token);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            mListener.onFailure(new Exception("Error"));
        } else {

            if (!ParseUserWithJson.isConnectSuccess(result)) {
                mListener.onFailure(new Exception(ParseUserWithJson.getMessageError(result)));
            } else {
                mListener.onSuccess(ParseUserWithJson.parseJsonToGetUser(result));
            }
        }
    }
}
