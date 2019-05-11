package com.sun_asterisk.comics_01.data.source.remote.fetchjson;

import com.sun_asterisk.comics_01.data.model.Author;
import com.sun_asterisk.comics_01.data.model.BookMark;
import com.sun_asterisk.comics_01.data.model.Chapter;
import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.utils.IOUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ParseDataWithJson {
    private static final int TIME_OUT = 15000;
    private static final String METHOD_GET = "GET";
    private static final String LINK_IMAGE = "LinkAnh";
    private static final String DATA = "Data";

    static String getJsonFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(TIME_OUT);
        httpURLConnection.setReadTimeout(TIME_OUT);
        httpURLConnection.setAllowUserInteraction(false);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setRequestMethod(METHOD_GET);
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) return null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        IOUtils.closeQuietly(httpURLConnection);
        IOUtils.closeQuietly(bufferedReader);
        return stringBuilder.toString();
    }

    static List<Comic> parseJsonToComicList(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(Comic.ComicEntry.DATA);
            return parseJsonArrayToComicList(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    static List<Comic> parseJsonSearchToComicList(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonData = jsonObject.getJSONObject(Comic.ComicEntry.DATA);
            JSONArray jsonArray = jsonData.getJSONArray(Comic.ComicEntry.LIST_COMIC);
            return parseJsonArrayToComicList(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Comic> parseJsonArrayToComicList(JSONArray jsonArrayComicList) {
        List<Comic> comicList = new ArrayList<>();
        try {
            for (int index = 0; index < jsonArrayComicList.length(); index++) {
                JSONObject comicJson = jsonArrayComicList.getJSONObject(index);
                Comic comic = parseJsonObjectToComic(comicJson);
                if (comic != null) comicList.add(comic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comicList;
    }

    private static Comic parseJsonObjectToComic(JSONObject jsonObjectComic) {
        Comic comic = null;
        List<Author> authors;
        try {
            JSONArray jsonArrayAuthor =
                    jsonObjectComic.getJSONArray(Author.AuthorEntry.AUTHOR_LIST);
            authors = parseJSonArrayAuthorToAuthorList(jsonArrayAuthor);
            comic = new Comic.ComicBuilder().id(jsonObjectComic.getInt(Comic.ComicEntry.ID))
                    .name(jsonObjectComic.getString(Comic.ComicEntry.NAME))
                    .otherName(jsonObjectComic.getString(Comic.ComicEntry.OTHER_NAME))
                    .thumbnail(jsonObjectComic.getString(Comic.ComicEntry.THUMBNAIL))
                    .description(jsonObjectComic.getString(Comic.ComicEntry.DESCRIPTION))
                    .dateCreated(jsonObjectComic.getString(Comic.ComicEntry.DATE_CREATED))
                    .authors(authors)
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comic;
    }

    private static List<Author> parseJSonArrayAuthorToAuthorList(JSONArray jsonArrayAuthor) {
        Author author;
        List<Author> authors = new ArrayList<>();
        for (int index = 0; index < jsonArrayAuthor.length(); index++) {
            try {
                JSONObject jsonObjectAuthor = jsonArrayAuthor.getJSONObject(index);
                author = new Author.AuthorBuilder().authorId(
                        jsonObjectAuthor.getInt(Author.AuthorEntry.AUTHOR_ID))
                        .authorName(jsonObjectAuthor.getString(Author.AuthorEntry.AUTHOR_NAME))
                        .build();
                authors.add(author);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return authors;
    }

    static List<Chapter> parseJsonToChapterList(String json) {
        List<Chapter> chapters = new ArrayList<>();
        try {
            JSONObject jsonParentObject = new JSONObject(json);
            JSONArray jsonChapterArray = jsonParentObject.getJSONArray(Chapter.ChapterEntry.DATA);
            for (int index = 0; index < jsonChapterArray.length(); index++) {
                JSONObject jsonObject = jsonChapterArray.getJSONObject(index);
                Chapter chapter = parseJsonObjectToChapter(jsonObject);
                if (chapter != null) chapters.add(chapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chapters;
    }

    private static Chapter parseJsonObjectToChapter(JSONObject jsonObject) {
        Chapter chapter = null;
        try {
            chapter = new Chapter.ChapterBuilder().id(jsonObject.getInt(Chapter.ChapterEntry.ID))
                    .name(jsonObject.getString(Chapter.ChapterEntry.NAME))
                    .serial(jsonObject.getInt(Chapter.ChapterEntry.SERIAL))
                    .view(jsonObject.getLong(Chapter.ChapterEntry.VIEW))
                    .dateCreated(jsonObject.getString(Chapter.ChapterEntry.DATE_CREATE))
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chapter;
    }

    static List<String> parseJsonToContentChapterList(String data) {
        List<String> links = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonObjectData = jsonObject.optJSONObject(DATA);
            String imagesStr = jsonObjectData.optString(LINK_IMAGE).replace("\\", "");
            JSONArray jsonArray = new JSONArray(imagesStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                links.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return links;
    }

    static List<Comic> parseJsonToComicFollowList(String data) {
        List<Comic> comics = new ArrayList<>();
        Comic comic;
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonObjectData = jsonObject.getJSONObject(Comic.ComicEntry.DATA);
            JSONArray jsonArray = jsonObjectData.getJSONArray(Comic.ComicEntry.LIST_BOOKMARK);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                comic = new Comic.ComicBuilder().id(object.getInt(Comic.ComicEntry.ID_COMIC))
                        .name(object.optString(Comic.ComicEntry.NAME))
                        .thumbnail(object.optString(Comic.ComicEntry.IMG_COMIC))
                        .description(object.getString(Comic.ComicEntry.TITLE))
                        .idChapterCurrent(object.optInt(BookMark.BookMarkEntry.ID_CHAPTER_BOOKMARK))
                        .nameChapterCurrent(
                                object.optString(BookMark.BookMarkEntry.NAME_CHAPTER_BOOKMARK))
                        .build();
                comics.add(comic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comics;
    }
}
