package js.technology.session.data.local.db;

import java.util.List;

import io.reactivex.Observable;
import js.technology.session.data.model.db.Invitees;
import js.technology.session.data.model.db.Session;

public interface DbHelper {
    Observable<Boolean> isSessionEmpty();

    Observable<Boolean> isInviteesEmpty();

    Observable<Boolean> saveInviteesList(List<Invitees> inviteesList);

    Observable<Boolean> saveSessionList(List<Session> sessionList);

    Observable<List<Invitees>> getAllInvitees();

    Observable<List<Session>> getAllSessions();

    Observable<Session> loadSessionById(Long sessionId);
}
