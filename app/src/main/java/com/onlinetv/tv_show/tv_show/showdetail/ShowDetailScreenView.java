package com.onlinetv.tv_show.tv_show.showdetail;

import com.onlinetv.tv_show.tv_show.commons.TvShow;

import java.util.List;

/**
 * TV Show List Screen View API
 *
 * @author Susana Pons
 */

public interface ShowDetailScreenView {

    /**
     * Show the related tv shows
     */
    void showRelatedTvShows(List<TvShow> tvShows);

    /**
     * Show that there has been an error loading the TV Shows
     */
    void showInternetError();


}
