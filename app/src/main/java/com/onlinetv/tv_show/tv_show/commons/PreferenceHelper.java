package com.onlinetv.tv_show.tv_show.commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Preference Helper
 *
 * Class responsible to handle the preferences of the application
 *
 * @author Susana Pons
 */

public class PreferenceHelper {

    private static final String EXTRA_TV_SHOWS = "tvshows";
    private final SharedPreferences mSharedPreferences;

    public PreferenceHelper(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * The the SV shows saved on the preferences
     * @return TvShows stored locally
     */
    public List<TvShow> getCacheTvShows() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(EXTRA_TV_SHOWS, "");
        Type listType = new TypeToken<List<TvShow>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    /**
     * Set the TV show into preferences
     * @param tvShows TV Shows to store
     */
    public void setCacheTvShows(List<TvShow> tvShows) {
        Gson gson = new Gson();
        String json = gson.toJson(tvShows);
        mSharedPreferences.edit().putString(EXTRA_TV_SHOWS, json).apply();

    }

}
