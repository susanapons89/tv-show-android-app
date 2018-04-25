package com.onlinetv.tv_show.tv_show.showdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlinetv.tv_show.tv_show.R;
import com.onlinetv.tv_show.tv_show.commons.Constants;
import com.onlinetv.tv_show.tv_show.commons.TvShow;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

/**
 * This Fragment is responsible to show all the UI for the movie details
 */

public class ShowDetailsFragment extends Fragment {

    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.poster)              ImageView mPoster;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.release_date)
    TextView mReleaseDate;
    @BindView(R.id.rating)
    TextView mRating;
    @BindView(R.id.rating_container)
    ViewGroup mRatingContainer;
    @BindView(R.id.synopsis)            TextView mSynopsis;

    private TvShow mTvShow;

    public ShowDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(Constants.ARG_TV_SHOW)) {
            mTvShow = getArguments().getParcelable(Constants.ARG_TV_SHOW);
        }
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_show_details, container, false);
        ButterKnife.bind(this, view);

        TvShow tvShow = getArguments().getParcelable(Constants.ARG_TV_SHOW);


        Picasso picasso = Picasso.with(getActivity());

        picasso.load(tvShow.getBackdropUrl())
                .config(Bitmap.Config.RGB_565)
                .into(mBackdrop);

        int posterWidth = getResources().getDimensionPixelSize(R.dimen.details_poster_width);
        int posterHeight = getResources().getDimensionPixelSize(R.dimen.details_poster_height);
        picasso.load(tvShow.getPosterUrl())
                .resize(posterWidth, posterHeight)
                .centerCrop()
                .into(mPoster);

        mTitle.setText(tvShow.getName());
        mReleaseDate.setText(mTvShow.getFirst_air_date());

        mRating.setText(String.format(Locale.getDefault(),"%1$2.1f", tvShow.getVote_average()));
        mSynopsis.setText(tvShow.getOverview());
        if (Build.VERSION.SDK_INT >= 26) {
            mSynopsis.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                animateContent();
                return true;
            }
        });

        return view;
    }

    public void animateContent() {
        View[] animatedViews = new View[] {
                mTitle, mReleaseDate, mRatingContainer, mSynopsis
        };
        Interpolator interpolator = new DecelerateInterpolator();
        for (int i = 0; i < animatedViews.length; ++i) {
            View v = animatedViews[i];
            v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            v.setAlpha(0f);
            v.setTranslationY(75);
            v.animate()
                    .setInterpolator(interpolator)
                    .alpha(1.0f)
                    .translationY(0)
                    .setStartDelay(100 + 75 * i)
                    .start();
        }
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}