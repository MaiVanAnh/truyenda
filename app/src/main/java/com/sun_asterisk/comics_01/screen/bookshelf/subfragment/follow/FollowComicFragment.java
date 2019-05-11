package com.sun_asterisk.comics_01.screen.bookshelf.subfragment.follow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.model.Cache;
import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import com.sun_asterisk.comics_01.data.source.remote.ComicRemoteDataSource;
import com.sun_asterisk.comics_01.screen.bookshelf.subfragment.readhistory.OnDeleteItemListener;
import com.sun_asterisk.comics_01.screen.bookshelf.subfragment.readhistory.adapter.ComicBookShelfAdapter;
import com.sun_asterisk.comics_01.screen.comic.ComicDetailActivity;
import com.sun_asterisk.comics_01.utils.OnItemRecyclerViewClickListener;
import java.util.List;

public class FollowComicFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, OnDeleteItemListener,
        OnItemRecyclerViewClickListener<Comic>, FollowContract.View {
    private RecyclerView mRecyclerView;
    private FollowContract.Presenter mPresenter;
    private ComicBookShelfAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mPositionComicWillDelete;

    public static FollowComicFragment getInstance() {
        return new FollowComicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_bookshelf, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        ComicRepository comicRepository =
                ComicRepository.getInstance(ComicRemoteDataSource.getsInstance(), null);
        mPresenter = new FollowPresenter(comicRepository);
        mPresenter.setView(this);
        mAdapter = new ComicBookShelfAdapter();
        mAdapter.setDeleteItemListener(this);
        mAdapter.setOnItemRecyclerViewClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        if (Cache.getInstance().isLogin) {
            mPresenter.getComicFollow();
        }
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerViewReadHistory);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (Cache.getInstance().isLogin) {
            mAdapter.clear();
            mPresenter.getComicFollow();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void deleteItemComic(int idComic, int position) {
        mPositionComicWillDelete = position;
        mPresenter.deleteComicFollow(idComic);
    }

    @Override
    public void onItemClickListener(Comic comic) {
        startActivity(ComicDetailActivity.getComicDetailIntent(getContext(), comic));
    }

    @Override
    public void onGetComicFollowSuccess(List<Comic> comics) {
        mAdapter.setComics(comics);
    }

    @Override
    public void onGetComicFollowFail(Exception exception) {
    }

    @Override
    public void onDeleteComicFollowSuccess() {
        mAdapter.deleteItemAt(mPositionComicWillDelete);
    }

    @Override
    public void onDeleteComicFollowFail() {
    }
}
