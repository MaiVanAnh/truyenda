package com.sun_asterisk.comics_01.screen.category.subfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import com.sun_asterisk.comics_01.data.source.local.ComicLocalDataSource;
import com.sun_asterisk.comics_01.data.source.local.sqlite.ComicLocalHandler;
import com.sun_asterisk.comics_01.data.source.remote.ComicRemoteDataSource;
import com.sun_asterisk.comics_01.screen.comic.ComicDetailActivity;
import com.sun_asterisk.comics_01.screen.home.HomeFragment;
import com.sun_asterisk.comics_01.screen.home.adapter.ComicAdapter;
import com.sun_asterisk.comics_01.utils.OnItemRecyclerViewClickListener;
import java.util.List;
import java.util.Objects;

public class SubCategoryFragment extends Fragment
        implements SubCategoryContract.View, OnItemRecyclerViewClickListener<Comic> {
    private static final String ARGUMENT_ID_CATEGORY = "ARGUMENT_ID_CATEGORY";
    private ProgressBar mProgressBar;
    private ComicAdapter mAdapter;
    private SubCategoryContract.Presenter mPresenter;

    public static SubCategoryFragment newInstance(int id) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_ID_CATEGORY, id);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        int idCategory = getArguments() != null ? getArguments().getInt(ARGUMENT_ID_CATEGORY) : 0;
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        initView(view);
        initData();
        getData(idCategory);
        return view;
    }

    private void initData() {

        ComicRepository comicRepository =
                ComicRepository.getInstance(ComicRemoteDataSource.getsInstance(),
                        null);
        mPresenter = new SubCategoryPresenter(comicRepository);
        mPresenter.setView(this);
    }

    private void getData(int idCategory) {
        mPresenter.getComicFollowCategory(idCategory);
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerPerCategory);
        mProgressBar = view.findViewById(R.id.progressBarPerCategory);
        mProgressBar.setVisibility(View.VISIBLE);
        mAdapter = new ComicAdapter(Objects.requireNonNull(getContext()));
        mAdapter.setOnItemRecyclerViewClickListener(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), HomeFragment.SPAN_COUNT));
    }

    @Override
    public void onGetComicFollowCategorySuccess(List<Comic> comics) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.setData(comics);
    }

    @Override
    public void onGetComicFollowCategoryError(Exception exception) {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNoComicFollowCategoryAvailable() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClickListener(Comic item) {
        startActivity(ComicDetailActivity.getComicDetailIntent(getContext(), item));
    }
}
