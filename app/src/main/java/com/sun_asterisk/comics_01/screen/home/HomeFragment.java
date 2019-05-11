package com.sun_asterisk.comics_01.screen.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import com.sun_asterisk.comics_01.data.source.local.ComicLocalDataSource;
import com.sun_asterisk.comics_01.data.source.local.sqlite.ComicLocalHandler;
import com.sun_asterisk.comics_01.data.source.remote.ComicRemoteDataSource;
import com.sun_asterisk.comics_01.screen.comic.ComicDetailActivity;
import com.sun_asterisk.comics_01.screen.home.adapter.ComicAdapter;
import com.sun_asterisk.comics_01.screen.search.SearchActivity;
import com.sun_asterisk.comics_01.utils.OnItemRecyclerViewClickListener;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment
        implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener,
        OnItemRecyclerViewClickListener<Comic> {
    public static final int SPAN_COUNT = 2;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerComic;
    private ComicAdapter mAdapter;
    private HomeContract.Presenter mPresenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        addControls(view);
        addEvents();
        initView();
        initData();
        return view;
    }

    private void addEvents() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initView() {
        mAdapter = new ComicAdapter(Objects.requireNonNull(getContext()));
        mAdapter.setOnItemRecyclerViewClickListener(this);
        mRecyclerComic.setAdapter(mAdapter);
        mRecyclerComic.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
    }

    private void initData() {
        ComicLocalHandler comicLocalHandler = new ComicLocalHandler(getContext());
        ComicRepository repository =
                ComicRepository.getInstance(ComicRemoteDataSource.getsInstance(),
                        ComicLocalDataSource.getsInstance(comicLocalHandler));
        mPresenter = new HomePresenter(repository);
        mPresenter.setView(this);
        mPresenter.getComics();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void addControls(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        mRecyclerComic = view.findViewById(R.id.recyclerComic);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.option_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSearch:
                startActivity(SearchActivity.getSearchIntent(getContext()));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetComicsSuccess(List<Comic> comics) {
        if (comics != null) {
            mAdapter.setData(comics);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetComicsError(Exception exception) {
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mPresenter.getComics();
    }

    @Override
    public void onItemClickListener(Comic comic) {
        Intent intent = ComicDetailActivity.getComicDetailIntent(getContext(), comic);
        startActivity(intent);
    }
}
