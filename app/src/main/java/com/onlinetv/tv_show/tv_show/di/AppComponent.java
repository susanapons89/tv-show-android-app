package com.onlinetv.tv_show.tv_show.di;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import com.onlinetv.tv_show.tv_show.TvShowApp;

/**
 * Application component
 *
 * Interface defining the Global App component
 *
 * @author Susana Pons
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ApiModule.class,
        ActivityBuilder.class
})
public interface AppComponent {


    /**
     * Application Component Builder
     */
    @Component.Builder
    interface  Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    /**
     * Inject Application dependency
     * @param app App instance
     */
    void inject (TvShowApp app);
}
