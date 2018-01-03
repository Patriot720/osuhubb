package example.cerki.osuhub.Data.ApiDatabase.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import example.cerki.osuhub.Data.POJO.Following;

/**
 * Created by cerki on 11-Dec-17.
 */

@Dao
public interface FollowingDao {
    @Query("SELECT * FROM following")
    List<Following> getAll();
    @Query("SELECT * FROM following WHERE id =:id")
    Following getBy(int id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Following following);
    @Delete
    void delete(Following following);
}
