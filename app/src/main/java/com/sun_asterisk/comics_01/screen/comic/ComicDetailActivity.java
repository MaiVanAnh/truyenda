package com.sun_asterisk.comics_01.screen.comic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.model.BookMark;
import com.sun_asterisk.comics_01.data.model.Cache;
import com.sun_asterisk.comics_01.data.model.Chapter;
import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ChapterRepository;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import com.sun_asterisk.comics_01.data.source.local.ComicLocalDataSource;
import com.sun_asterisk.comics_01.data.source.local.sqlite.ComicLocalHandler;
import com.sun_asterisk.comics_01.data.source.remote.ChapterRemoteDataSource;
import com.sun_asterisk.comics_01.screen.comic.adapter.ChapterAdapter;
import com.sun_asterisk.comics_01.screen.read.ReadComicActivity;
import com.sun_asterisk.comics_01.utils.OnItemRecyclerViewClickListener;
import com.sun_asterisk.comics_01.utils.StringUtils;
import java.util.List;

public class ComicDetailActivity extends AppCompatActivity
        implements ComicDetailContract.View, OnItemRecyclerViewClickListener<Chapter> {
    private final static String BUNDLE_COMIC = "BUNDLE_COMIC";
    private final static String ARGUMENT_COMIC = "ARGUMENT_COMIC";
    private ComicDetailContract.Presenter mPresenter;
    private Comic mComic;
    private ImageView mImgThumbnail;
    private TextView mTvName;
    private TextView mTvAuthors;
    private TextView mTvDateCreated;
    private TextView mTvDescription;
    private ChapterAdapter mAdapter;
    private ProgressBar mProgressBar;
    private ImageView mImgFollow;
    private Boolean mIsFollow = false;
    private LinearLayout mViewBookMarkContainer;
    private TextView mTvNameChapterBookMark;
    private int mIdChapterBookMark;
    private String mNameChapterBookMark;
    private Boolean mIsComeBack = false;

    public static Intent getComicDetailIntent(Context context, Comic comic) {
        Intent intent = new Intent(context, ComicDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_COMIC, comic);
        intent.putExtra(BUNDLE_COMIC, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        receiveData();
        initView();
        initData();
        getRemoteData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsComeBack) {
            if (Cache.getInstance().isLogin) {
                mPresenter.checkFollow(mComic.getId());
            }
        }
        mIsComeBack = false;
    }

    private void receiveData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BUNDLE_COMIC);
        mComic = bundle.getParcelable(ARGUMENT_COMIC);
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarChapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mImgThumbnail = findViewById(R.id.imgThumbComicDetail);
        mTvName = findViewById(R.id.tvNameComicDetail);
        mTvAuthors = findViewById(R.id.tvAuthorsComicDetail);
        mTvDateCreated = findViewById(R.id.tvDateCreatedComicDetail);
        mTvDescription = findViewById(R.id.tvDescriptionComicDetail);
        mTvDescription.setMovementMethod(new ScrollingMovementMethod());
        mImgFollow = findViewById(R.id.imgFollow);
        mImgFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFollow();
            }
        });
        mViewBookMarkContainer = findViewById(R.id.viewBookMarkContainer);
        mTvNameChapterBookMark = findViewById(R.id.tvNameChapterBookMark);
        TextView tvReadContinue = findViewById(R.id.tvReadContinue);
        tvReadContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readContinue();
            }
        });
        RecyclerView recyclerChapter = findViewById(R.id.recyclerChapter);
        mAdapter = new ChapterAdapter();
        mAdapter.setOnItemRecyclerViewClickListener(this);
        recyclerChapter.setAdapter(mAdapter);
        recyclerChapter.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progressBarDetail);
    }

    private void readContinue() {
        startActivity(ReadComicActivity.getReadComicIntent(this, mIdChapterBookMark,
                mNameChapterBookMark));
    }

    private void updateFollow() {
        if (!mIsFollow) {
            mPresenter.follow(mComic.getId());
        } else {
            mPresenter.unFollow(mComic.getId());
        }
    }

    private void initData() {
        if (mComic != null) {
            Glide.with(this)
                    .load(mComic.getThumbnail())
                    .centerCrop()
                    .placeholder(R.drawable.img_not_found)
                    .into(mImgThumbnail);
            mTvName.setText(mComic.getName());
            if (mComic.getAuthors() != null) mTvAuthors.setText(mComic.showAuthor());
            if (mComic.getDateCreated() != null) {
                mTvDateCreated.setText(StringUtils.formatDate(mComic.getDateCreated()));
            }
            mTvDescription.setText(mComic.getDescription());
        }
    }

    private void getRemoteData() {
        if (mComic != null) {
            ChapterRepository chapterRepository =
                    ChapterRepository.getInstance(ChapterRemoteDataSource.getInstance());
            ComicLocalHandler comicLocalHandler = new ComicLocalHandler(getApplicationContext());
            ComicRepository comicRepository = ComicRepository.getInstance(null,
                    ComicLocalDataSource.getsInstance(comicLocalHandler));
            mPresenter =
                    new ComicDetailPresenter(chapterRepository, comicRepository, mComic.getId());
            mPresenter.setView(this);
            mPresenter.getChapters();
            if (Cache.getInstance().isLogin) {
                mPresenter.checkFollow(mComic.getId());
            }
        }
    }

    @Override
    public void onGetChapterSuccess(List<Chapter> chapters) {
        if (chapters != null) mAdapter.setData(chapters);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(Exception exception) {
        if (exception != null) mProgressBar.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCheckFollowSuccess(BookMark bookMark) {
        mIsFollow = true;
        mImgFollow.setImageResource(R.drawable.ic_heart_click);
        if (!bookMark.getNameChapter().equals("null") && bookMark.getIdChapter() != 0) {
            mIdChapterBookMark = bookMark.getIdChapter();
            mNameChapterBookMark = bookMark.getNameChapter();
            mTvNameChapterBookMark.setText(bookMark.getNameChapter());
            mViewBookMarkContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCheckFollowFail() {
        mAdapter.setIdChapterCurrent(mComic.getIdChapterCurrent());
    }

    @Override
    public void onFollowSuccess() {
        mIsFollow = true;
        mImgFollow.setImageResource(R.drawable.ic_heart_click);
        Toast.makeText(getApplicationContext(), getString(R.string.follow), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onFollowFail() {
    }

    @Override
    public void onUnFollowSuccess() {
        mImgFollow.setImageResource(R.drawable.ic_heart_not_click);
        mIsFollow = false;
        mViewBookMarkContainer.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), getString(R.string.un_follow), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onUnFollowFail() {
    }

    @Override
    public void onItemClickListener(Chapter chapter) {
        if (mIsFollow) {
            mPresenter.bookmark(chapter.getId(), mComic.getId());
            mIsComeBack = true;
        } else {
            mComic.setIdChapterCurrent(chapter.getId());
            mComic.setNameChapterCurrent(chapter.getName());
            mPresenter.saveComicCurrent(mComic);
        }

        startActivity(
                ReadComicActivity.getReadComicIntent(this, chapter.getId(), chapter.getName()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
