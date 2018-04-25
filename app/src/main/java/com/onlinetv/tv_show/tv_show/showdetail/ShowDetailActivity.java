package com.onlinetv.tv_show.tv_show.showdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.animation.OvershootInterpolator;

import com.onlinetv.tv_show.tv_show.BaseActivity;
import com.onlinetv.tv_show.tv_show.R;
import com.onlinetv.tv_show.tv_show.commons.Constants;
import com.onlinetv.tv_show.tv_show.commons.TvShow;
import com.onlinetv.tv_show.tv_show.commons.TvShowBaseAdapterCallbacks;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static android.widget.LinearLayout.HORIZONTAL;


/**
 * Show Details Activity
 *
 * This class implements the Activity for showing movie details
 *
 * @author Susana Pons
 */
public class ShowDetailActivity extends BaseActivity
        implements ShowDetailScreenView ,TvShowBaseAdapterCallbacks{

    private static final String ARG_RELATED = "relatedTv";
    private static final String ARG_CURRENT_PAGE = "currentPage";
    private static final String ARG_TOTAL_PAGES = "totalPages";

    @Inject
    ShowDetailScreenPresenter mShowDetailScreenPresenter;

    @BindView(R.id.similar_tv_show_list)
    RecyclerView mRecyclerView;

    private ShowDetailAdapter mAdapter;

    private LinearLayoutManager mLayoutManager;

    private TvShow mTvShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvShow = getIntent().getParcelableExtra(Constants.ARG_TV_SHOW);
        initializeRecicleView();
        mShowDetailScreenPresenter.onViewAttached(this,
                mTvShow.getId(),
                savedInstanceState != null);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(Constants.ARG_TV_SHOW,
                    mTvShow);
            ShowDetailsFragment fragment = new ShowDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        } else {
            if  (savedInstanceState.containsKey(ARG_RELATED)) {
                List<TvShow> tvShows = savedInstanceState.getParcelableArrayList(ARG_RELATED);
                mAdapter.addAll(tvShows);
            }
            if (savedInstanceState.containsKey(ARG_CURRENT_PAGE)) {
                mShowDetailScreenPresenter.setCurrentPage(savedInstanceState.getInt(ARG_CURRENT_PAGE));
            }
            if (savedInstanceState.containsKey(ARG_TOTAL_PAGES)) {
                mShowDetailScreenPresenter.setTotalPages(savedInstanceState.getInt(ARG_TOTAL_PAGES));
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecyclerView.removeOnScrollListener(recyclerViewOnScrollListener);
        mShowDetailScreenPresenter.onViewDetached();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<TvShow> tvShows = mAdapter.getAllShows();
        if (tvShows != null && !tvShows.isEmpty()) {
            outState.putParcelableArrayList(ARG_RELATED, tvShows);
        }
        outState.putInt(ARG_CURRENT_PAGE, mShowDetailScreenPresenter.getCurrentPage());
        outState.putInt(ARG_TOTAL_PAGES, mShowDetailScreenPresenter.getTotalPages());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_show;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getToolbarTitle() {
        return getString(R.string.show_details_activity_name);
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
    public void showRelatedTvShows(List<TvShow> tvShows) {
        mAdapter.addAll(tvShows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showInternetError() {
        this.showToast("There was a problem with the internet connection");

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
                mShowDetailScreenPresenter.onUserFinishedTvShowPaged();
            }
        }
    };


    ///// PRIVATE METHODS ///////////

    private void initializeRecicleView() {
        mLayoutManager = new LinearLayoutManager(this.getBaseContext(), HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // To avoid "E/RecyclerView: No adapter attached; skipping layout"
        mAdapter = new ShowDetailAdapter(new ArrayList<TvShow>(), this.getApplicationContext(), this);
        mRecyclerView.setAdapter(mAdapter);
        // Listen to scroll events
        mRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

    }

    @Override
    public void onUserClickedMovie(TvShow tvShow) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        intent.putExtra(Constants.ARG_TV_SHOW, tvShow);
        startActivity(intent);
        finish();
    }
}
