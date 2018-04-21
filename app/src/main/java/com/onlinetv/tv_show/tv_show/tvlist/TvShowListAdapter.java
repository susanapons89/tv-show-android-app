package com.onlinetv.tv_show.tv_show.tvlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlinetv.tv_show.tv_show.R;
import com.onlinetv.tv_show.tv_show.commons.Constants;
import com.onlinetv.tv_show.tv_show.commons.TvShow;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An adapter to inflate the tv shows items with the corresponding data
 */

public class TvShowListAdapter  extends RecyclerView.Adapter<TvShowListAdapter.ViewHolder> {


    private final ArrayList<TvShow> mtvShows;
    private final Context mContext;


    public TvShowListAdapter(ArrayList<TvShow> tvShow, Context context) {
        mtvShows = tvShow;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.tv_show_item, parent, false);

        view.getLayoutParams().height = (int) (parent.getWidth() / 2 * Constants.POSTER_ASPECT_RATIO);

        return new ViewHolder(view);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.cleanUp();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TvShow tvShow = mtvShows.get(position);

        holder.mTvShow = tvShow;
        holder.mTitleText.setText(tvShow.getName());
        holder.mScoreText.setText(String.valueOf(tvShow.getVote_average()));

        String posterUrl = tvShow.getPosterUrl();

        if (posterUrl == null) {
            // TODO: Handle error in this case
        }

        Picasso.with(mContext)
                .load(posterUrl)
                .config(Bitmap.Config.RGB_565)
                .into(holder.mPosterImage,
                        new Callback() {
                            @Override
                            public void onSuccess() {
                                if (holder.mTvShow.getId() != tvShow.getId()) {
                                    holder.cleanUp();
                                } else {
                                    holder.mPosterImage.setVisibility(View.VISIBLE);
                                    holder.mTitleText.setVisibility(View.VISIBLE);
                                    holder.mScoreText.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onError() {
                                // TODO: Handle if error here
                            }
                        }
                );
    }

    /**
     * Add new items to tle list view
     * @param tvShows
     */
    public void addAll(List<TvShow> tvShows) {
        mtvShows.addAll(tvShows);
        notifyDataSetChanged();
    }

    public void updateAll(List<TvShow> tvShows) {
        mtvShows.clear();
        mtvShows.addAll(tvShows);
        notifyDataSetChanged();

    }

    public ArrayList<TvShow> getAllShows() {
        return mtvShows;
    }

    @Override
    public int getItemCount() {
        return mtvShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.show_image)
        ImageView mPosterImage;
        @BindView(R.id.name_text)
        TextView mTitleText;
        @BindView(R.id.score_text)
        TextView mScoreText;
        public TvShow mTvShow;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }

        public void cleanUp() {
            final Context context = mView.getContext();
            Picasso.with(context).cancelRequest(mPosterImage);
            mPosterImage.setImageBitmap(null);
            mPosterImage.setVisibility(View.INVISIBLE);
            mTitleText.setVisibility(View.GONE);
            mScoreText.setVisibility(View.GONE);
        }

    }

}
