package com.onlinetv.tv_show.tv_show.tvlist;

import android.util.Log;

import com.onlinetv.tv_show.tv_show.commons.Constants;
import com.onlinetv.tv_show.tv_show.commons.MovieDbApi;
import com.onlinetv.tv_show.tv_show.commons.MovieDbResultModel;
import com.onlinetv.tv_show.tv_show.commons.PreferenceHelper;
import com.onlinetv.tv_show.tv_show.commons.TvShow;


import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TV Show List Screen Presenter
 *
 * Controller for the TvShowList Activity
 *
 * @author Susana Pons
 */
class TvShowListScreenPresenter {

    private static final String LOG_TAG =TvShowListScreenPresenter.class.getSimpleName();
    private static final int FIRST_PAGE = 1;

    private final MovieDbApi mMovieDbApi;
    private  final PreferenceHelper mPreferenceHelper;
    private TvShowListScreenView mTvShowListScreenView;
    private int mCurrentPage;
    private int mTotalPageNumber;
    private boolean mIsLoadingNewPages = false;


    /**
     * TV Show List Screen Presenter Constructor
     * @param movieDbApi
     */
    @Inject
    TvShowListScreenPresenter(MovieDbApi movieDbApi, PreferenceHelper preferenceHelper) {
        mMovieDbApi = movieDbApi;
        mPreferenceHelper = preferenceHelper;
    }

    /**
     * Attach the view with the presenter and get the Popular TV Shows
     */
    void onViewAttached(TvShowListScreenView tvShowListScreenView, boolean isInstancedSaved) {
        mTvShowListScreenView = tvShowListScreenView;
        if (!isInstancedSaved) {
            getPopularTvShows(FIRST_PAGE, false);
            mTvShowListScreenView.showLoadginPage();
        }
    }

    void onViewDetached() {
        mTvShowListScreenView = null;
    }

    void onPaused(List<TvShow> tvShows) {
        // Store movies on cache
        if (tvShows != null && !tvShows.isEmpty()) {
            mPreferenceHelper.setCacheTvShows(tvShows);
        }
    }

    void onUserFinishedTvShowPaged() {
        if (!mIsLoadingNewPages && mCurrentPage < mTotalPageNumber) {
            Log.d(LOG_TAG, "Show next page!!!");
            getPopularTvShows(mCurrentPage + 1, false);
            mTvShowListScreenView.showLoadginPage();
        }
    }

    void onUserRefreshedPage() {
        getPopularTvShows(FIRST_PAGE, true);
    }

    /**
     * Get Current page
     * @return
     */
    int getCurrentPage() {
        return mCurrentPage;
    }


    /**
     * Get total pages
     * @return
     */
    int getTotalPages() {
        return mTotalPageNumber;
    }

    /**
     * Set the current page. This is necessary for cases when the presenter restarts. An example
     * would be when change from portatrit to land
     * @param currentPage
     */
    void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    /**
     * Set the total pages. This is necessary for cases when the presenter restarts. An example
     * would be when change from portatrit to land
     * @param totalPages
     */
    void setTotalPages(int totalPages) {
        mTotalPageNumber = totalPages;
    }

    //////////  PRIVATE METHODS ////////

    private void getPopularTvShows(final int page, final boolean isPagedRefreshed) {
        mIsLoadingNewPages = true;
        Call<MovieDbResultModel> call = mMovieDbApi.getPopularTvShows(Constants.API_KEY_MOVIE_DB,
                Locale.getDefault().getLanguage(), page);

        call.enqueue(new Callback<MovieDbResultModel>() {
            @Override
            public void onResponse(Call<MovieDbResultModel> call,
                                   Response<MovieDbResultModel> response) {
                MovieDbResultModel popularMoviesResult = response.body();
                mCurrentPage = popularMoviesResult.getPage();
                mTotalPageNumber = popularMoviesResult.getTotal_pages();
                List<TvShow> tvShowList = popularMoviesResult.getResults();
                if (mTvShowListScreenView != null && !isPagedRefreshed) {
                    mTvShowListScreenView.showMostPopularTvShows(tvShowList);
                    mTvShowListScreenView.hideLoadingPage();
                } else if (mTvShowListScreenView != null) {
                    mTvShowListScreenView.updateTvShows(tvShowList);
                    mTvShowListScreenView.hideLoadingPage();
                }
                mIsLoadingNewPages = false;
            }

            @Override
            public void onFailure(Call<MovieDbResultModel> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage(), t);
                mIsLoadingNewPages = false;
                mTvShowListScreenView.hideLoadingPage();
                mTvShowListScreenView.showInternetError();
                // Check if we can get tvShows from cache
                List<TvShow> tvShows = mPreferenceHelper.getCacheTvShows();
                if (tvShows != null && !isPagedRefreshed && page == 1) {
                    // Only update shows if there is no show currently
                    mTvShowListScreenView.updateTvShows(tvShows);
                }
            }
        });
    }
}
