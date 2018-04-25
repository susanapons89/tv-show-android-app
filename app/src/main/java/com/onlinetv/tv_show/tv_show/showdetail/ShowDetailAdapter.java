package com.onlinetv.tv_show.tv_show.showdetail;

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

public class ShowDetailAdapter extends TvShowBaseAdapter {



    public ShowDetailAdapter(ArrayList<TvShow> tvShow, Context context,
                             TvShowBaseAdapterCallbacks callbacks) {
        super(tvShow, context, callbacks);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        View view = inflater.inflate(R.layout.tv_show_item, parent, false);

        view.getLayoutParams().width = (int)(parent.getHeight() / TvShow.POSTER_ASPECT_RATIO);

        return new ViewHolder(view);
    }


    @Override
    protected void setTvShowInformation(ViewHolder holder) {
        // DO nothing in this case. We do not want to show any title or score
    }


}
