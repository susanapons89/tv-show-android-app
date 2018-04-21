package com.onlinetv.tv_show.tv_show.tvlist;

import android.content.Context;

import com.onlinetv.tv_show.tv_show.commons.MovieDbApi;
import com.onlinetv.tv_show.tv_show.commons.PreferenceHelper;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * TV Show List Activity Module
 *
 * This class is responsible for providing the Tv Show List Dependencies
 *
 * @author Susana Pons
 */
@Module
public class TvShowListActivityModule {


    /**
     * Provide Movie DB API
     * @param retrofit
     * @return
     */
    @Provides
    MovieDbApi provideMovieDbApi(Retrofit retrofit) {
        return  retrofit.create(MovieDbApi.class);

    }

    @Provides
    TvShowListScreenPresenter provideTvShowListScreenPresenter(MovieDbApi movieDbApi,
                                                               PreferenceHelper preferenceHelper) {
        return new TvShowListScreenPresenter(movieDbApi, preferenceHelper);
    }
}
