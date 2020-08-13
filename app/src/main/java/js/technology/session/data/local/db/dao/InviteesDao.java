package js.technology.session.data.local.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;
import js.technology.session.data.model.db.Invitees;

@Dao
public interface InviteesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Invitees invitees);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Invitees> invitees);

    @Query("SELECT * FROM invitees ORDER BY name")
    Single<List<Invitees>> loadAll();
}
