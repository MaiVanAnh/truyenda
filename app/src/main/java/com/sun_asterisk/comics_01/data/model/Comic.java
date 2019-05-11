package com.sun_asterisk.comics_01.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.sun_asterisk.comics_01.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class Comic implements Parcelable {
    private int mId;
    private String mName;
    private String mOtherName;
    private String mThumbnail;
    private String mDescription;
    private String mDateCreated;
    private List<Author> mAuthors;
    private int mIdChapterCurrent;
    private String mNameChapterCurrent;

    private Comic(ComicBuilder comicBuilder) {
        mId = comicBuilder.mId;
        mName = comicBuilder.mName;
        mOtherName = comicBuilder.mOtherName;
        mThumbnail = comicBuilder.mThumbnail;
        mDescription = comicBuilder.mDescription;
        mDateCreated = comicBuilder.mDateCreated;
        mAuthors = comicBuilder.mAuthors;
        mIdChapterCurrent = comicBuilder.mIdChapterCurrent;
        mNameChapterCurrent = comicBuilder.mNameChapterCurrent;
    }

    protected Comic(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mOtherName = in.readString();
        mThumbnail = in.readString();
        mDescription = in.readString();
        mDateCreated = in.readString();
        mAuthors = in.createTypedArrayList(Author.CREATOR);
        mIdChapterCurrent = in.readInt();
        mNameChapterCurrent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mOtherName);
        dest.writeString(mThumbnail);
        dest.writeString(mDescription);
        dest.writeString(mDateCreated);
        dest.writeTypedList(mAuthors);
        dest.writeInt(mIdChapterCurrent);
        dest.writeString(mNameChapterCurrent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOtherName() {
        return mOtherName;
    }

    public void setOtherName(String otherName) {
        mOtherName = otherName;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }

    public List<Author> getAuthors() {
        return mAuthors;
    }

    public int getIdChapterCurrent() {
        return mIdChapterCurrent;
    }

    public void setIdChapterCurrent(int idChapterCurrent) {
        mIdChapterCurrent = idChapterCurrent;
    }

    public String getNameChapterCurrent() {
        return mNameChapterCurrent;
    }

    public void setNameChapterCurrent(String nameChapterCurrent) {
        mNameChapterCurrent = nameChapterCurrent;
    }

    public String showAuthor() {
        StringBuilder authorName = new StringBuilder();
        for (Author author : mAuthors)
            authorName.append(author.getAuthorName()).append(StringUtils.SEPARATE_SPACE);
        return authorName.toString();
    }

    public String convertAuthorListToAuthorsStr() {
        StringBuilder authorsStr = new StringBuilder();
        for (Author author : mAuthors)
            authorsStr.append(author.getAuthorId())
                    .append(StringUtils.SEPARATE_DOLLAR)
                    .append(author.getAuthorName())
                    .append(StringUtils.SEPARATE_COMMA);
        return authorsStr.toString();
    }

    public static List<Author> convertAuthorsStrToAuthorList(String authorsStr) {
        List<Author> authors = new ArrayList<>();
        Author author;
        String[] idAndNameStrSplit = authorsStr.split(StringUtils.SEPARATE_COMMA);
        for (String s : idAndNameStrSplit) {
            String[] split = s.split(StringUtils.SEPARATE_DOLLAR);
            author = new Author.AuthorBuilder().authorId(Integer.parseInt(split[0]))
                    .authorName(split[1])
                    .build();
            authors.add(author);
        }
        return authors;
    }

    public static class ComicBuilder {
        private int mId;
        private String mName;
        private String mOtherName;
        private String mThumbnail;
        private String mDescription;
        private String mDateCreated;
        private List<Author> mAuthors;
        private int mIdChapterCurrent = -1;
        private String mNameChapterCurrent;

        public ComicBuilder() {
        }

        public ComicBuilder id(int id) {
            mId = id;
            return this;
        }

        public ComicBuilder name(String name) {
            mName = name;
            return this;
        }

        public ComicBuilder otherName(String otherName) {
            mOtherName = otherName;
            return this;
        }

        public ComicBuilder thumbnail(String thumbnail) {
            mThumbnail = thumbnail;
            return this;
        }

        public ComicBuilder description(String description) {
            mDescription = description;
            return this;
        }

        public ComicBuilder dateCreated(String dateCreated) {
            mDateCreated = dateCreated;
            return this;
        }

        public ComicBuilder authors(List<Author> authors) {
            mAuthors = authors;
            return this;
        }

        public ComicBuilder idChapterCurrent(int idChapterCurrent) {
            mIdChapterCurrent = idChapterCurrent;
            return this;
        }

        public ComicBuilder nameChapterCurrent(String nameChapterCurrent) {
            mNameChapterCurrent = nameChapterCurrent;
            return this;
        }

        public Comic build() {
            return new Comic(this);
        }
    }

    public final class ComicEntry {
        public static final String ID = "Id";
        public static final String ID_COMIC = "Id_Truyen";
        public static final String NAME = "TenTruyen";
        public static final String OTHER_NAME = "TenKhac";
        public static final String THUMBNAIL = "AnhDaiDien";
        public static final String IMG_COMIC = "AnhTruyen";
        public static final String DESCRIPTION = "MoTa";
        public static final String TITLE = "title";
        public static final String DATE_CREATED = "NgayTao";
        public static final String DATA = "Data";
        public static final String LIST_COMIC = "listComic";
        public static final String LIST_BOOKMARK = "ListBookmark";
    }
}
