package com.onlinetv.tv_show.tv_show.showdetail;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * TV Show List Component
 *
 * Interface defining TV Show Screen Component
 *
 * @author Susana Pons
 */
@Subcomponent(modules = ShowDetailActivityModule.class)
public interface ShowDetailScreenComponent extends AndroidInjector<ShowDetailActivity> {

    /**
     * Tv Show List Screen Component Builder
     */
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ShowDetailActivity>{

    }
}
