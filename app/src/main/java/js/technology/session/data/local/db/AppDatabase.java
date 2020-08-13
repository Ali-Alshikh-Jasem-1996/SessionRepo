package js.technology.session.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import js.technology.session.data.local.db.dao.InviteesDao;
import js.technology.session.data.local.db.dao.SessionsDao;
import js.technology.session.data.model.db.Invitees;
import js.technology.session.data.model.db.Session;

@Database(entities = {Invitees.class, Session.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract InviteesDao inviteesDao();

    public abstract SessionsDao sessionsDao();
}
