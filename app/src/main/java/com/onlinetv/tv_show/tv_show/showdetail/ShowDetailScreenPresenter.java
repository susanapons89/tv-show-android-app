package com.onlinetv.tv_show.tv_show.showdetail;

import android.util.Log;

import com.onlinetv.tv_show.tv_show.commons.Constants;
import com.onlinetv.tv_show.tv_show.commons.MovieDbApi;
import com.onlinetv.tv_show.tv_show.commons.MovieDbResultModel;
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
class ShowDetailScreenPresenter {

    private static final String LOG_TAG = ShowDetailScreenPresenter.class.getSimpleName();
    private static final int FIRST_PAGE = 1;


    private final MovieDbApi mMovieDbApi;

    private ShowDetailScreenView mShowDetailScreenView;

    private boolean mIsLoadingNewPages = false;
    private int mCurrentPage;
    private int mTotalPageNumber;
    private int mTvId;


    /**
     * TV Show List Screen Presenter Constructor
     * @param movieDbApi
     */
    @Inject
    ShowDetailScreenPresenter(MovieDbApi movieDbApi) {
        mMovieDbApi = movieDbApi;
    }

    void onViewAttached (ShowDetailScreenView showDetailScreenView,
                         Integer tvId,
                         boolean isInstancedSaved) {
        mShowDetailScreenView = showDetailScreenView;
        mTvId = tvId;
        if (!isInstancedSaved) {
            getSimilarTvShows(tvId, FIRST_PAGE);
        }

    }


    void onViewDetached() {
        mShowDetailScreenView = null;
    }


    void onUserFinishedTvShowPaged() {

        if (!mIsLoadingNewPages && mCurrentPage < mTotalPageNumber) {
            Log.d(LOG_TAG, "Show next page!!!");
            mIsLoadingNewPages = true;
            getSimilarTvShows(mTvId, mCurrentPage + 1);
        }
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

    private void getSimilarTvShows(Integer tvId, int page) {

        Call<MovieDbResultModel> call = mMovieDbApi.getSimilarTvShows(tvId, Constants.API_KEY_MOVIE_DB,
                Locale.getDefault().getLanguage(), page);

        call.enqueue(new Callback<MovieDbResultModel>() {
            @Override
            public void onResponse(Call<MovieDbResultModel> call,
                                   Response<MovieDbResultModel> response) {
                MovieDbResultModel popularMoviesResult = response.body();
                mCurrentPage = popularMoviesResult.getPage();
                mTotalPageNumber = popularMoviesResult.getTotal_pages();
                List<TvShow> tvShowList = popularMoviesResult.getResults();
                if (mShowDetailScreenView != null) {
                    mShowDetailScreenView.showRelatedTvShows(tvShowList);
                }
                mIsLoadingNewPages = false;
            }

            @Override
            public void onFailure(Call<MovieDbResultModel> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage(), t);
                mIsLoadingNewPages = false;
                mShowDetailScreenView.showInternetError();
            }
        });
    }
}
