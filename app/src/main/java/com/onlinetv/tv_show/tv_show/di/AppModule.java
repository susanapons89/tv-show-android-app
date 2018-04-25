package com.onlinetv.tv_show.tv_show.di;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import com.onlinetv.tv_show.tv_show.commons.PreferenceHelper;
import com.onlinetv.tv_show.tv_show.showdetail.ShowDetailScreenComponent;
import com.onlinetv.tv_show.tv_show.tvlist.TvShowListScreenComponent;

/**
 * Application Module
 *
 * This class is responsible for instantiating the global App dependencies
 *
 * @author Susana Pons
 */
@Module(subcomponents = {
        TvShowListScreenComponent.class,
        ShowDetailScreenComponent.class
})
class AppModule {


    /**
     * Provides Application Context
     * @param application Application Instance
     * @return Application Context
     */
    @Provides
    @Singleton
    Context provideContext (Application application) {
        return application;
    }

    /**
     * Provides preference helper
     * @param context Application Context
     * @return Prefernce Helper
     */
    @Provides
    @Singleton
    PreferenceHelper providePrefernceHelper(Context context) {
        return new PreferenceHelper(context);
    }
}
