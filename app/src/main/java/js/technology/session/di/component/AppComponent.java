package js.technology.session.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import js.technology.session.SessionApp;
import js.technology.session.di.builder.ActivityBuilder;
import js.technology.session.di.module.AppModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(SessionApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
