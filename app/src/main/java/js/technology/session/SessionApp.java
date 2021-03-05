package js.technology.session;

import android.app.Activity;
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import js.technology.session.di.component.DaggerAppComponent;

public class SessionApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Inject Session App(Application)
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        Fresco.initialize(this);
    }
}
