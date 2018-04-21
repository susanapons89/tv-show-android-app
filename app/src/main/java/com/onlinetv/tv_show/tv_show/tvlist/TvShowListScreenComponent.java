package com.onlinetv.tv_show.tv_show.tvlist;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * TV Show List Component
 *
 * Interface defining TV Show Screen Component
 *
 * @author Susana Pons
 */
@Subcomponent(modules = TvShowListActivityModule.class)
public interface TvShowListScreenComponent extends AndroidInjector<TvShowListActivity> {

    /**
     * Tv Show List Screen Component Builder
     */
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TvShowListActivity>{

    }
}
