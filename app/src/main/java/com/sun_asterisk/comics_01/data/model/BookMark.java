package com.sun_asterisk.comics_01.data.model;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class BookMark {
    private int mIdChapter;
    private String mNameChapter;
    private int mIdComic;


    private BookMark(BookMarkBuilder bookMarkBuilder) {
        mIdChapter = bookMarkBuilder.mIdChapter;
        mNameChapter = bookMarkBuilder.mNameChapter;
        mIdComic = bookMarkBuilder.mIdComic;
    }

    public int getIdChapter() {
        return mIdChapter;
    }

    public String getNameChapter() {
        return mNameChapter;
    }

    public int getIdComic() {
        return mIdComic;
    }

    public static class BookMarkBuilder {
        private int mIdChapter;
        private String mNameChapter;
        private int mIdComic;

        public BookMarkBuilder() {
        }

        public BookMarkBuilder idChapter(int id) {
            mIdChapter = id;
            return this;
        }

        public BookMarkBuilder nameChapter(String name) {
            mNameChapter = name;
            return this;
        }

        public BookMarkBuilder idComic(int idComic) {
            mIdComic = idComic;
            return this;
        }

        public BookMark build() {
            return new BookMark(this);
        }
    }

    public static class BookMarkEntry {
        public static final String ID_CHAPTER_BOOKMARK = "Id_ChuongDanhDau";
        public static final String NAME_CHAPTER_BOOKMARK = "TenChuongDanhDau";
        public static final String ID_COMIC_FOLLOW = "Id_Truyen";
        public static final String DATA = "Data";
    }
}
