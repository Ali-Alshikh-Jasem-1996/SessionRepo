package js.technology.session.data.local.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;
import js.technology.session.data.model.db.Invitees;
import js.technology.session.data.model.db.Session;

@Dao
public interface SessionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Session session);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Session> sessions);

    @Query("SELECT * FROM session ORDER BY ActivityStartDate DESC")
    Single<List<Session>> loadAll();

    @Query("SELECT * FROM session WHERE SessionId = :id")
    Single<Session> loadSessionById(Long id);

}
