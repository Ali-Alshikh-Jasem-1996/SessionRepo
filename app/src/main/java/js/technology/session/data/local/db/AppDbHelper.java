package js.technology.session.data.local.db;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import js.technology.session.data.model.db.Invitees;
import js.technology.session.data.model.db.Session;

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<Boolean> isSessionEmpty() {
        return mAppDatabase.inviteesDao().loadAll()
                .flatMapObservable(sessions -> Observable.just(sessions.isEmpty()));
    }

    @Override
    public Observable<Boolean> isInviteesEmpty() {
        return mAppDatabase.inviteesDao().loadAll()
                .flatMapObservable(invitees -> Observable.just(invitees.isEmpty()));
    }

    @Override
    public Observable<Boolean> saveInviteesList(List<Invitees> inviteesList) {
        return Observable.fromCallable(() -> {
            mAppDatabase.inviteesDao().insertAll(inviteesList);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveSessionList(List<Session> sessionList) {
        return Observable.fromCallable(() -> {
            mAppDatabase.sessionsDao().insertAll(sessionList);
            return true;
        });
    }

    @Override
    public Observable<List<Invitees>> getAllInvitees() {
        return mAppDatabase.inviteesDao().loadAll().toObservable();
    }

    @Override
    public Observable<List<Session>> getAllSessions() {
        return mAppDatabase.sessionsDao().loadAll().toObservable();
    }

    @Override
    public Observable<Session> loadSessionById(Long sessionId) {
        return mAppDatabase.sessionsDao().loadSessionById(sessionId).toObservable();
    }
}
