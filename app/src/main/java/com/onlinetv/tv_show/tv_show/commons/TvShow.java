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

    public static final float POSTER_ASPECT_RATIO = 1.5f;

    private String poster_path;
    private Integer id;
    private String backdrop_path;
    private Number vote_average;
    private String overview;
    private String first_air_date;
    private String name;

    public TvShow(String poster_path, Integer id,
                  String backdrop_path, Number vote_average, String overview,
                  String first_air_date, String name) {

        this.poster_path = poster_path;
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;

        this.overview = overview;
        this.first_air_date = first_air_date;
        this.name = name;
    }

    private TvShow() {

    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Creator<TvShow>() {
        public TvShow createFromParcel(Parcel source) {

            TvShow tvShow = new TvShow();
            tvShow.poster_path = source.readString();
            tvShow.id = source.readInt();
            tvShow.backdrop_path = source.readString();
            tvShow.overview = source.readString();
            tvShow.first_air_date = source.readString();
            tvShow.name = source.readString();
            tvShow.vote_average = (Number) source.readSerializable();

            return tvShow;
        }

        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public String getPosterUrl() {
        return Constants.POSTER_URL + poster_path;
    }

    public Integer getId() {
        return id;
    }

    public String getBackdropUrl() {
        return Constants.POSTER_URL + backdrop_path;
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

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeInt(id);
        parcel.writeString(backdrop_path);
        parcel.writeString(overview);
        parcel.writeString(first_air_date);
        parcel.writeString(name);
        parcel.writeSerializable(vote_average);
    }
}
