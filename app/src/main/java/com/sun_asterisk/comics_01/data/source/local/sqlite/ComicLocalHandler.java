package com.sun_asterisk.comics_01.data.source.local.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sun_asterisk.comics_01.data.model.Author;
import com.sun_asterisk.comics_01.data.model.Comic;
import java.util.ArrayList;
import java.util.List;

public class ComicLocalHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "COMICS_01";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "COMIC";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String OTHER_NAME = "OTHER_NAME";
    private static final String THUMBNAIL = "THUMBNAIL";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String DATE_CREATED = "DATECREATED";
    private static final String AUTHORS = "AUTHORS";
    private static final String ID_CHAPTER_CURRENT = "ID_CHAPTER_CURRENT";
    private static final String NAME_CHAPTER_CURRENT = "NAME_CHAPTER_CURRENT";
    private static final String LIKE_CHARACTER = " = ?";
    private static final String EQUAL_CHARACTER = " = ";
    private SQLiteDatabase mSQLiteDB;

    public ComicLocalHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mSQLiteDB = getWritableDatabase();
        mSQLiteDB = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (" + ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT, "
                + OTHER_NAME + " TEXT, " + THUMBNAIL + " TEXT, "
                + DESCRIPTION + " TEXT, " + DATE_CREATED + " TEXT, "
                + AUTHORS + " TEXT, " + ID_CHAPTER_CURRENT + " TEXT, "
                + NAME_CHAPTER_CURRENT + " TEXT)";
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        database.execSQL(sql);
        onCreate(database);
    }

    public void addComic(Comic comic) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, comic.getId());
        contentValues.put(NAME, comic.getName());
        contentValues.put(OTHER_NAME, comic.getOtherName());
        contentValues.put(THUMBNAIL, comic.getThumbnail());
        contentValues.put(DESCRIPTION, comic.getDescription());
        contentValues.put(DATE_CREATED, comic.getDateCreated());
        contentValues.put(AUTHORS, comic.convertAuthorListToAuthorsStr());
        contentValues.put(ID_CHAPTER_CURRENT, comic.getIdChapterCurrent());
        contentValues.put(NAME_CHAPTER_CURRENT, comic.getNameChapterCurrent());
        Cursor cursor;
        String sql =
                "SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + ID + EQUAL_CHARACTER + comic.getId();
        cursor = mSQLiteDB.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            updateComic(contentValues, comic.getId());
        } else {
            mSQLiteDB.insert(TABLE_NAME, "", contentValues);
        }
    }

    public void updateComic(ContentValues contentValues, int idComic) {
        String[] selectionArs = { Integer.toString(idComic) };
        mSQLiteDB.update(TABLE_NAME, contentValues, ID + LIKE_CHARACTER, selectionArs);
    }

    public List<Comic> getAllComic() {
        List<Comic> comics = new ArrayList<>();
        Comic comic;
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = mSQLiteDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                List<Author> authors = Comic.convertAuthorsStrToAuthorList(
                        cursor.getString(cursor.getColumnIndex(AUTHORS)));
                comic = new Comic.ComicBuilder().id(cursor.getInt(cursor.getColumnIndex(ID)))
                        .name(cursor.getString(cursor.getColumnIndex(NAME)))
                        .otherName(cursor.getString(cursor.getColumnIndex(OTHER_NAME)))
                        .thumbnail(cursor.getString(cursor.getColumnIndex(THUMBNAIL)))
                        .description(cursor.getString(cursor.getColumnIndex(DESCRIPTION)))
                        .dateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)))
                        .authors(authors)
                        .idChapterCurrent(cursor.getInt(cursor.getColumnIndex(ID_CHAPTER_CURRENT)))
                        .nameChapterCurrent(
                                cursor.getString(cursor.getColumnIndex(NAME_CHAPTER_CURRENT)))
                        .build();
                comics.add(comic);
            } while (cursor.moveToNext());
        }
        return comics;
    }

    public void removeComic(int id) {
        String[] selectionArs = { Integer.toString(id) };
        mSQLiteDB.delete(TABLE_NAME, ID + LIKE_CHARACTER, selectionArs);
    }
}
