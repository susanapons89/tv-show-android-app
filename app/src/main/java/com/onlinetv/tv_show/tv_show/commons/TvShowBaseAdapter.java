package com.onlinetv.tv_show.tv_show.commons;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.onlinetv.tv_show.tv_show.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TV Show base adapter
 * An adapter to inflate the tv shows items with the corresponding data
 *
 * @author Susana Pons
 */
public abstract class TvShowBaseAdapter extends RecyclerView.Adapter<TvShowBaseAdapter.ViewHolder> {

    private static final String LOG_CAT = TvShowBaseAdapter.class.getSimpleName();
    private final ArrayList<TvShow> mtvShows;
    protected final Context mContext;
    private final TvShowBaseAdapterCallbacks mCallbacks;


    public TvShowBaseAdapter(ArrayList<TvShow> tvShow,
                             Context context,
                             TvShowBaseAdapterCallbacks callbacks) {
        mtvShows = tvShow;
        mContext = context;
        mCallbacks = callbacks;
    }


    /**
     * Set Title and score if necessary and its visibility
     */
    protected abstract void setTvShowInformation(ViewHolder holder);


    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.cleanUp();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TvShow tvShow = mtvShows.get(position);

        holder.mTvShow = tvShow;

        String posterUrl = tvShow.getPosterUrl();


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
                                    setTvShowInformation(holder);
                                }
                            }

                            @Override
                            public void onError() {
                                Log.e(LOG_CAT, "Unable to load images");
                                holder.cleanUp();
                            }
                        }
                );
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onUserClickedMovie(tvShow);
            }
        });
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
        public ImageView mPosterImage;
        @BindView(R.id.name_text)
        public TextView mTitleText;
        @BindView(R.id.score_text)
        public TextView mScoreText;
        public TvShow mTvShow;
        @BindView(R.id.rating_star)
        public RatingBar mRateStar;

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
            mRateStar.setVisibility(View.GONE);
        }

    }

}
