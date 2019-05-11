package com.sun_asterisk.comics_01.screen.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import com.sun_asterisk.comics_01.data.repository.SearchHistoryRepository;
import com.sun_asterisk.comics_01.data.source.local.ComicLocalDataSource;
import com.sun_asterisk.comics_01.data.source.local.SearchHistoryDataSource;
import com.sun_asterisk.comics_01.data.source.local.sharedprefs.SharedPrefsHelper;
import com.sun_asterisk.comics_01.data.source.local.sqlite.ComicLocalHandler;
import com.sun_asterisk.comics_01.data.source.remote.ComicRemoteDataSource;
import com.sun_asterisk.comics_01.screen.comic.ComicDetailActivity;
import com.sun_asterisk.comics_01.screen.home.HomeFragment;
import com.sun_asterisk.comics_01.screen.home.adapter.ComicAdapter;
import com.sun_asterisk.comics_01.utils.OnItemRecyclerViewClickListener;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity
        implements SearchContract.View, OnItemRecyclerViewClickListener<Comic>,
        View.OnClickListener {
    private static final int MAX_SIZE = 20;
    private static final int THRESH_OLD = 1;
    private AutoCompleteTextView mAutoCompleteTvName;
    private Button mBtnSearch;
    private TextView mTvNotify;
    private RecyclerView mRvComic;
    private ProgressBar mProgressSearch;
    private ComicAdapter mAdapter;
    private SearchPresenter mPresenter;
    private Set<String> mHistorySet;
    private ArrayAdapter<String> mAutoTvAdapter;

    public static Intent getSearchIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
        bindView();
    }

    private void bindView() {
        mHistorySet = mPresenter.getSearchHistoryLocal();
        mAutoTvAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                mHistorySet.toArray(new String[mHistorySet.size()]));
        mAutoCompleteTvName.setAdapter(mAutoTvAdapter);
        mAutoCompleteTvName.setThreshold(THRESH_OLD);
    }

    private void initData() {
        ComicLocalHandler comicLocalHandler = new ComicLocalHandler(getApplicationContext());
        ComicRepository comicRepository =
                ComicRepository.getInstance(ComicRemoteDataSource.getsInstance(),
                        ComicLocalDataSource.getsInstance(comicLocalHandler));
        SearchHistoryDataSource searchHistoryDataSource = SearchHistoryDataSource.getInstance(
                SharedPrefsHelper.getInstance(getApplicationContext()));
        SearchHistoryRepository searchHistoryRepository =
                SearchHistoryRepository.getInstance(searchHistoryDataSource);
        mPresenter = new SearchPresenter(comicRepository, searchHistoryRepository);
        mPresenter.setView(this);
    }

    private void initView() {
        mAutoCompleteTvName = findViewById(R.id.autoCompleteTvNameComic);
        mBtnSearch = findViewById(R.id.btnSearch);
        mBtnSearch.setOnClickListener(this);
        mTvNotify = findViewById(R.id.tvNotify);
        mRvComic = findViewById(R.id.recyclerSearchComic);
        mAdapter = new ComicAdapter(this);
        mAdapter.setOnItemRecyclerViewClickListener(this);
        mRvComic.setAdapter(mAdapter);
        mRvComic.setLayoutManager(new GridLayoutManager(this, HomeFragment.SPAN_COUNT));
        mProgressSearch = findViewById(R.id.progressSearch);
        mProgressSearch.setVisibility(View.GONE);
    }

    @Override
    public void onSearchComicSuccess(List<Comic> comics) {
        mProgressSearch.setVisibility(View.GONE);
        if (comics != null) mAdapter.setData(comics);
    }

    @Override
    public void onSearchComicError(Exception exception) {
        mProgressSearch.setVisibility(View.GONE);
    }

    @Override
    public void onNoDataAvailable() {
        mProgressSearch.setVisibility(View.GONE);
        mTvNotify.setText(getResources().getString(R.string.not_find));
        mAdapter.clear();
    }

    @Override
    public void onItemClickListener(Comic comic) {
        mPresenter.saveSearchHistoryLocal(mHistorySet);
        startActivity(ComicDetailActivity.getComicDetailIntent(this, comic));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                mTvNotify.setText("");
                String name = mAutoCompleteTvName.getText().toString().trim();
                if (name.equals("")) {
                    mAutoCompleteTvName.requestFocus();
                } else {
                    mProgressSearch.setVisibility(View.VISIBLE);
                    mPresenter.searchComic(name);
                    addSearchInput(name);
                }
                break;
        }
    }

    private void addSearchInput(String input) {
        if (!mHistorySet.contains(input)) {
            if (mHistorySet.size() > MAX_SIZE) {
                mHistorySet.remove(mHistorySet.iterator().next());
            }
            mHistorySet.add(input);
        }
    }
}
