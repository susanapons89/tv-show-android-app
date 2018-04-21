package com.onlinetv.tv_show.tv_show.commons;

import java.util.List;

/**
 * Created by ponssusa on 21/04/2018.
 */

public class MovieDbResultModel {

    private final Integer page;
    private final List<TvShow> results;
    private final Integer total_results;
    private final Integer total_pages;


    public MovieDbResultModel(Integer page, List<TvShow> results, Integer total_results,
                              Integer total_pages) {
        this.page = page;
        this.results = results;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public Integer getPage() {
        return page;
    }

    public List<TvShow> getResults() {
        return results;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }
}
