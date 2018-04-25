package com.onlinetv.tv_show.tv_show.tvlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;

import com.onlinetv.tv_show.tv_show.BaseActivity;
import com.onlinetv.tv_show.tv_show.R;
import com.onlinetv.tv_show.tv_show.commons.Constants;
import com.onlinetv.tv_show.tv_show.commons.TvShow;
import com.onlinetv.tv_show.tv_show.commons.TvShowBaseAdapterCallbacks;
import com.onlinetv.tv_show.tv_show.showdetail.ShowDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class TvShowListActivity extends BaseActivity
        implements TvShowListScreenView, TvShowBaseAdapterCallbacks{

    private static final String ARG_CURRENT_PAGE = "currentPage";
    private static final String ARG_TOTAL_PAGES = "totalPages";

    @Inject
    TvShowListScreenPresenter mTvShowListScreenPresenter;

    @BindView(R.id.tv_show_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressBar_cyclic)
    ProgressBar mProgressBar;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private TvShowListAdapter mAdapter;

    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeRecicleView();
        mSwipeRefresh.setOnRefreshListener(mSwipeListener);
        mTvShowListScreenPresenter.onViewAttached(this, savedInstanceState != null);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.ARG_TV_SHOW)) {
                List<TvShow> tvShows = savedInstanceState.getParcelableArrayList(Constants.ARG_TV_SHOW);
                mAdapter.addAll(tvShows);
            }
            if (savedInstanceState.containsKey(ARG_CURRENT_PAGE)) {
                mTvShowListScreenPresenter.setCurrentPage(savedInstanceState.getInt(ARG_CURRENT_PAGE));
            }
            if (savedInstanceState.containsKey(ARG_TOTAL_PAGES)) {
                mTvShowListScreenPresenter.setTotalPages(savedInstanceState.getInt(ARG_TOTAL_PAGES));
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeListeners();
        mTvShowListScreenPresenter.onViewDetached();
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        mTvShowListScreenPresenter.onPaused(mAdapter.getAllShows());
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<TvShow> tvShows = mAdapter.getAllShows();
        if (tvShows != null && !tvShows.isEmpty()) {
            outState.putParcelableArrayList(Constants.ARG_TV_SHOW, tvShows);
        }

        outState.putInt(ARG_CURRENT_PAGE, mTvShowListScreenPresenter.getCurrentPage());
        outState.putInt(ARG_TOTAL_PAGES, mTvShowListScreenPresenter.getTotalPages());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tv_show_list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getToolbarTitle() {
        return getString(R.string.tv_list_activity_name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tv_show_list, menu);
        return true;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void showMostPopularTvShows(List<TvShow> tvShows) {
        mAdapter.addAll(tvShows);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showLoadginPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideLoadingPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTvShows(List<TvShow> tvShows) {
        mSwipeRefresh.setRefreshing(false);
        mAdapter.updateAll(tvShows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showInternetError() {
        mSwipeRefresh.setRefreshing(false);
        this.showToast("There was a problem with the internet connection");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUserClickedMovie(TvShow tvShow) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        intent.putExtra(Constants.ARG_TV_SHOW, tvShow);
        startActivity(intent);
    }

    private void initializeRecicleView() {
        mLayoutManager = new GridLayoutManager(this, getResources()
                .getInteger(R.integer.grid_number_cols));
        mRecyclerView.setLayoutManager(mLayoutManager);
        // To avoid "E/RecyclerView: No adapter attached; skipping layout"
        mAdapter = new TvShowListAdapter(new ArrayList<TvShow>(), this.getApplicationContext(), this);
        mRecyclerView.setAdapter(mAdapter);
        // Listen to scroll events
        mRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

    }

    private void removeListeners() {
        mRecyclerView.removeOnScrollListener(recyclerViewOnScrollListener);
    }

    // region Listeners
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                mTvShowListScreenPresenter.onUserFinishedTvShowPaged();
            }
        }
    };

    private SwipeRefreshLayout.OnRefreshListener mSwipeListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mTvShowListScreenPresenter.onUserRefreshedPage();

        }
    };


}
