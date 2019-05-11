package com.sun_asterisk.comics_01.data.source.remote.request;

import android.os.AsyncTask;
import com.sun_asterisk.comics_01.utils.Constant;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class BookMarkAsync extends AsyncTask<String, Void, Void> {
    private int mIdChapter;
    private int mIdComic;

    public BookMarkAsync(int idChapter, int idComic) {
        mIdChapter = idChapter;
        mIdComic = idComic;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String url = strings[0];
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constant.ID_CHUONG_FOLLOW, mIdChapter);
            jsonObject.put(Constant.ID_COMIC, mIdComic);
            RequestHandler.sendPutWithToken(url, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
