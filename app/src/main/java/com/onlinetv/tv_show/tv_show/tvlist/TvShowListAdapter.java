package com.onlinetv.tv_show.tv_show.tvlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlinetv.tv_show.tv_show.R;
import com.onlinetv.tv_show.tv_show.commons.TvShow;
import com.onlinetv.tv_show.tv_show.commons.TvShowBaseAdapter;
import com.onlinetv.tv_show.tv_show.commons.TvShowBaseAdapterCallbacks;

import java.util.ArrayList;

/**
 * An adapter to inflate the tv shows items with the corresponding data
 */

public class TvShowListAdapter  extends TvShowBaseAdapter {

    private static final int MAX_VOTE_NUM = 10;



    public TvShowListAdapter(ArrayList<TvShow> tvShow, Context context,
                             TvShowBaseAdapterCallbacks callbacks) {
        super(tvShow, context, callbacks);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        View view = inflater.inflate(R.layout.tv_show_item, parent, false);
        int gridColsNumber = mContext.getResources().getInteger(R.integer.grid_number_cols);

        view.getLayoutParams().height =
                (int) (parent.getWidth() / gridColsNumber * TvShow.POSTER_ASPECT_RATIO);

        return new ViewHolder(view);
    }


    @Override
    protected void setTvShowInformation(ViewHolder holder) {
        holder.mTitleText.setText(holder.mTvShow.getName());
        holder.mScoreText.setText(String.valueOf(holder.mTvShow.getVote_average()));
        holder.mTitleText.setVisibility(View.VISIBLE);
        holder.mScoreText.setVisibility(View.VISIBLE);
        holder.mRateStar.setRating(holder.mTvShow.getVote_average().floatValue() / MAX_VOTE_NUM);
        holder.mRateStar.setVisibility(View.VISIBLE);
    }

}
