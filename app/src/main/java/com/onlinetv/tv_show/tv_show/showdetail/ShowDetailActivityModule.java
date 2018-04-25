package com.onlinetv.tv_show.tv_show.showdetail;

import com.onlinetv.tv_show.tv_show.commons.MovieDbApi;

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
public class ShowDetailActivityModule {


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
    ShowDetailScreenPresenter provideTvShowDetailScreenPresenter(MovieDbApi movieDbApi) {
        return new ShowDetailScreenPresenter(movieDbApi);
    }
}
