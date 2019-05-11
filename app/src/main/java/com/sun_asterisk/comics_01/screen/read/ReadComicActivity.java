package com.sun_asterisk.comics_01.screen.read;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.repository.ChapterRepository;
import com.sun_asterisk.comics_01.data.source.remote.ChapterRemoteDataSource;
import com.sun_asterisk.comics_01.screen.read.adapter.ContentAdapter;
import com.sun_asterisk.comics_01.screen.search.SearchActivity;
import java.util.List;

public class ReadComicActivity extends AppCompatActivity implements ReadContract.View {
    private static final String ARGUMENT_CHAPTER_ID = "ARGUMENT_CHAPTER_ID";
    private static final String ARGUMENT_CHAPTER_NAME = "ARGUMENT_CHAPTER_NAME";
    private int mChapterId;
    private String mChapterName;
    private RecyclerView mRecyclerView;
    private ContentAdapter mAdapter;
    private ReadContract.Presenter mPresenter;

    public static Intent getReadComicIntent(Context context, int idChapter, String nameChapter) {
        Intent intent = new Intent(context, ReadComicActivity.class);
        intent.putExtra(ARGUMENT_CHAPTER_ID, idChapter);
        intent.putExtra(ARGUMENT_CHAPTER_NAME, nameChapter);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);
        receiveData();
        initView();
        getData();
    }

    private void getData() {
        if (mChapterId != 0) {
            mPresenter.getContentChapter(mChapterId);
        }
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarContentChapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mChapterName);
        mRecyclerView = findViewById(R.id.recyclerContent);

        mAdapter = new ContentAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mPresenter = new ReadPresenter(
                ChapterRepository.getInstance(ChapterRemoteDataSource.getInstance()));
        mPresenter.setView(this);
    }

    private void receiveData() {
        Intent intent = getIntent();
        if (intent != null) {
            mChapterId = intent.getIntExtra(ARGUMENT_CHAPTER_ID, 0);
            mChapterName = intent.getStringExtra(ARGUMENT_CHAPTER_NAME);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onLoadContentSuccess(List<String> data) {
        if (data != null) {
            mAdapter.setData(data);
        }
    }

    @Override
    public void onLoadContentFail(Exception exception) {

    }
}
