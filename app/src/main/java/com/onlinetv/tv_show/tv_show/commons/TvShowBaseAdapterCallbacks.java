package com.onlinetv.tv_show.tv_show.commons;

/**
 * This interface defines the callbacks from the TvShowListAdapter
 *
 * @author Susana Pons
 */

public interface TvShowBaseAdapterCallbacks {

    /**
     * Inform Activity that the user has clicked on a Tv Show
     * @param tvShow
     */
    void onUserClickedMovie(TvShow tvShow);
}
