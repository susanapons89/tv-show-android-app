package com.onlinetv.tv_show.tv_show;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

/**
 * Base Screen Activty
 *
 * This class is responsible for the view layer for the Base screen
 *
 * @author Susana Pons
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.my_toolbar)
    AppBarLayout myToolbar;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Nullable
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    /**
     * Set up style
     *
     * @param layoutId Layout ID to be applied
     */
    protected  void setupStype(int layoutId) {
        setContentView(layoutId);

        // Butterknife binding
        ButterKnife.bind(this);

        // Styling up
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (mToolbar != null) {
            mToolbar.setTitle("");
            if (mTitle != null) {
                mTitle.setText(getToolbarTitle());
            }
        }
        setSupportActionBar(mToolbar);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected  void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setupStype(getLayoutId());
    }

    /**
     * Show toast
     * @param msg text to show on toast
     */
    protected  void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }

    /**
     * Get the Layout Id
     * @return the id of the layout in use
     */
    protected abstract int getLayoutId();

    /**
     * Returns the title of the activity
     * @return
     */
    protected abstract String getToolbarTitle();

    protected void setToolbarTitle(int resId) {
        mTitle.setText(resId);
    }

    protected void setToolbarTitle(String text) {
        mTitle.setText(text);
    }

}
