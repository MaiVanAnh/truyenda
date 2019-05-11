package com.sun_asterisk.comics_01.data.source.remote.request;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.data.source.remote.OnSignUpListener;
import com.sun_asterisk.comics_01.utils.Constant;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 05/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class SignUpAsync extends AsyncTask<JSONObject, Void, String> {
    private OnSignUpListener mSignUpListener;

    SignUpAsync(OnSignUpListener signUpListener) {
        mSignUpListener = signUpListener;
    }

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {
        JSONObject jsonObject = jsonObjects[0];
        try {
            return RequestHandler.sendPost(Constant.BASE_URL + Constant.ACCOUNT, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        if (data == null) {
            mSignUpListener.onFailure(new Exception("Login fail!"));
        } else {
            if (!ParseUserWithJson.isConnectSuccess(data)) {
                mSignUpListener.onFailure(new Exception(ParseUserWithJson.getMessageError(data)));
            } else {
                mSignUpListener.onSuccess();
            }
        }
    }
}
