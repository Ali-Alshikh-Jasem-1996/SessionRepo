package js.technology.session.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import js.technology.session.ui.details.DetailsActivity;
import js.technology.session.ui.session.SessionsActivity;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract SessionsActivity bindMainActivity();

    @ContributesAndroidInjector()
    abstract DetailsActivity bindDetailsActivity();
}
