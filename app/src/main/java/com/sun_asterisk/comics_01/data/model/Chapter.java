package com.sun_asterisk.comics_01.data.model;

public class Chapter {

    private int mId;
    private String mName;
    private int mSerial;
    private long mView;
    private String mDateCreated;
    private String mImagesLink;

    public Chapter() {
    }

    private Chapter(ChapterBuilder chapterBuilder) {
        mId = chapterBuilder.mId;
        mName = chapterBuilder.mName;
        mSerial = chapterBuilder.mSerial;
        mView = chapterBuilder.mView;
        mDateCreated = chapterBuilder.mDateCreated;
        mImagesLink = chapterBuilder.mImagesLink;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getSerial() {
        return mSerial;
    }

    public long getView() {
        return mView;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public String getImagesLink() {
        return mImagesLink;
    }

    public static class ChapterBuilder {
        private int mId;
        private String mName;
        private int mSerial;
        private long mView;
        private String mDateCreated;
        private String mImagesLink;

        public ChapterBuilder() {
        }

        public ChapterBuilder id(int id) {
            mId = id;
            return this;
        }

        public ChapterBuilder name(String name) {
            mName = name;
            return this;
        }

        public ChapterBuilder serial(int serial) {
            mSerial = serial;
            return this;
        }

        public ChapterBuilder view(long view) {
            mView = view;
            return this;
        }

        public ChapterBuilder dateCreated(String dateCreated) {
            mDateCreated = dateCreated;
            return this;
        }

        public ChapterBuilder imagesLink(String imagesLink) {
            mImagesLink = imagesLink;
            return this;
        }

        public Chapter build() {
            return new Chapter(this);
        }
    }

    public static class ChapterEntry {
        public static final String ID = "Id";
        public static final String NAME = "TenChuong";
        public static final String SERIAL = "SoThuTu";
        public static final String VIEW = "LuotXem";
        public static final String DATE_CREATE = "NgayTao";
        public static final String IMAGES_LINK = "LinkAnh";
        public static final String DATA = "Data";
        public static final String CHAPTER_LIST = "listChapter";
    }
}
