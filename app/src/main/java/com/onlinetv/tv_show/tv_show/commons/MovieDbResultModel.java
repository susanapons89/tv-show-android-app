package com.onlinetv.tv_show.tv_show.commons;

import java.util.List;

/**
 * Movie DB Result Model
 *
 * This class is responsible for defining the response structure of the Movie DB API
 *
 * @author Susana Pons
 */
public class MovieDbResultModel {

    private final Integer page;
    private final List<TvShow> results;
    private final Integer total_pages;



    public MovieDbResultModel(List<TvShow> results, Integer page, Integer total_pages) {
        this.results = results;
        this.page = page;
        this.total_pages = total_pages;
    }

    public Integer getPage() {
        return page;
    }

    public List<TvShow> getResults() {
        return results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }
}
