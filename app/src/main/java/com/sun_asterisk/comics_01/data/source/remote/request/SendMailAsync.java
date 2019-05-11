package com.sun_asterisk.comics_01.data.source.remote.request;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;
import com.sun_asterisk.comics_01.utils.Constant;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 11/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class SendMailAsync extends AsyncTask<String, Void, String> {
    private OnObjectListener<String> mListener;
    private String mEmail;

    SendMailAsync(String email, OnObjectListener<String> listener) {
        mEmail = email;
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", mEmail);
            return RequestHandler.sendPost(strings[0], jsonObject);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            mListener.onFailure(new Exception("Error"));
        } else {

            if (ParseUserWithJson.isConnectSuccess(result)) {
                mListener.onSuccess(ParseUserWithJson.getMessageError(result));
            } else {
                mListener.onFailure(new Exception("Invalid email!"));
            }
        }
    }
}
