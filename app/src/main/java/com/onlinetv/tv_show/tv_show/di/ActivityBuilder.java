package com.onlinetv.tv_show.tv_show.di;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

import com.onlinetv.tv_show.tv_show.showdetail.ShowDetailActivity;
import com.onlinetv.tv_show.tv_show.showdetail.ShowDetailScreenComponent;
import com.onlinetv.tv_show.tv_show.tvlist.TvShowListActivity;
import com.onlinetv.tv_show.tv_show.tvlist.TvShowListScreenComponent;

/**
 * Activity Builder
 *
 * This class responibility is to build activities and bind them in the Activity Map
 *
 * @author Susana Pons
 */
@Module
abstract class ActivityBuilder {


    /**
     * Bind Activity to the component
     * @param builder Component Builder
     * @return  Activity Factory
     */
    @Binds
    @IntoMap
    @ActivityKey(TvShowListActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindShowTvListActivity(
            TvShowListScreenComponent.Builder builder);

    /**
     * Bind Activity to the component
     * @param builder Component Builder
     * @return  Activity Factory
     */
    @Binds
    @IntoMap
    @ActivityKey(ShowDetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindShowDetailActivity(
            ShowDetailScreenComponent.Builder builder);
}
