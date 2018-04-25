package com.onlinetv.tv_show.tv_show.commons;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Movie DB API interface
 *
 * This class is responsible for defining all the necessary get and post to the
 * movie DB API
 *
 * @author Susana Pons
 */

public interface MovieDbApi {

    @GET("3/tv/popular")
    Call<MovieDbResultModel> getPopularTvShows(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") Integer page
    );

    @GET("3/tv/{tv_id}/similar")
    Call<MovieDbResultModel> getSimilarTvShows(
            @Path("tv_id") Integer tvId,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") Integer page
    );
}
