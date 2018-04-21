package com.onlinetv.tv_show.tv_show;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import com.onlinetv.tv_show.tv_show.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;

/**
 * TV Show Application
 *
 * This class is responsible for creating the Activity Injector
 *
 * @author Susana Pons
 */
public class TvShowApp  extends Application implements HasActivityInjector, HasServiceInjector{

    @Inject
    DispatchingAndroidInjector<Activity> mActivityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Service> mServiceDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return mServiceDispatchingAndroidInjector;
    }
}
