package com.onlinetv.tv_show.tv_show.commons;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * TV Show Model
 *
 * This class represents a single result for a TV show
 *
 * @author Susana Pons
 */
public class TvShow implements Parcelable {

    private String poster_path;
    private Number popularity;
    private Integer id;
    private String backdrop_path;
    private Number vote_average;
    private String overview;
    private String first_air_date;
    private String[] origin_country;
    private Integer[] genre_ids;
    private String original_language;
    private Integer vote_count;
    private String name;
    private String original_name;

    public TvShow(String poster_path, Number popularity, Integer id,
                  String backdrop_path, Number vote_average, String overview,
                  String first_air_date, String[] origin_country, Integer[] genre_ids,
                  String original_language, Integer vote_count, String name,
                  String original_name) {

        this.poster_path = poster_path;
        this.popularity = popularity;
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;

        this.overview = overview;
        this.first_air_date = first_air_date;
        this.origin_country = origin_country;
        this.genre_ids = genre_ids;
        this.original_language = original_language;
        this.vote_count = vote_count;
        this.name = name;
        this.original_name = original_name;
    }

    private TvShow() {

    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Creator<TvShow>() {
        public TvShow createFromParcel(Parcel source) {
            TvShow tvShow = new TvShow();
            tvShow.poster_path = source.readString();
            tvShow.popularity = (Number) source.readSerializable();
            tvShow.id = source.readInt();
            tvShow.vote_count = source.readInt();
            tvShow.backdrop_path = source.readString();
            tvShow.overview = source.readString();
            tvShow.first_air_date = source.readString();
            tvShow.original_language = source.readString();
            tvShow.name = source.readString();
            tvShow.original_language = source.readString();
            tvShow.origin_country = (String[]) source.readArray(String[].class.getClassLoader());
            tvShow.genre_ids = (Integer[]) source.readArray(Integer[].class.getClassLoader());
            tvShow.vote_average = (Number) source.readSerializable();

            return tvShow;
        }

        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public Integer[] getGenre_ids() {
        return genre_ids;
    }

    public String[] getOrigin_country() {
        return origin_country;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getPosterUrl() {
        return Constants.POSTER_URL + poster_path;
    }

    public Number getPopularity() {
        return popularity;
    }

    public Integer getId() {
        return id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public Number getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeSerializable(popularity);
        parcel.writeInt(id);
        parcel.writeInt(vote_count);
        parcel.writeString(backdrop_path);
        parcel.writeString(overview);
        parcel.writeString(first_air_date);
        parcel.writeString(original_language);
        parcel.writeString(name);
        parcel.writeString(original_name);
        parcel.writeArray(origin_country);
        parcel.writeArray(genre_ids);
        parcel.writeSerializable(vote_average);

    }
}
