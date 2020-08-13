package js.technology.session.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import js.technology.session.di.DatabaseInfo;
import js.technology.session.data.local.db.AppDatabase;
import js.technology.session.data.local.db.AppDbHelper;
import js.technology.session.data.local.db.DbHelper;
import js.technology.session.utils.AppConstants;
import js.technology.session.rx.AppSchedulerProvider;
import js.technology.session.rx.SchedulerProvider;

@Module
public class AppModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName)
                .fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable(CompositeDisposable compositeDisposable) {
        return compositeDisposable;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
