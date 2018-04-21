package com.onlinetv.tv_show.tv_show.tvlist;

import com.onlinetv.tv_show.tv_show.commons.TvShow;

import java.util.List;

/**
 * TV Show List Screen View API
 *
 * @author Susana Pons
 */

public interface TvShowListScreenView {

    /**
     * Show the most popular TV shows after are downloaded
     */
    void showMostPopularTvShows(List<TvShow> tvShows);

    /**
     * Show that a page is being loaded
     */
    void showLoadginPage();

    /**
     * Hide loading page
     */
    void hideLoadingPage();

    /**
     * Afetr refreshing the list of TV shows, clean up and update it with new items
     */
    void updateTvShows(List<TvShow> tvShows);


    void showInternetError();


}
