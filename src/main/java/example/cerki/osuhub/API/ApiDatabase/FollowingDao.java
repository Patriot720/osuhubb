package example.cerki.osuhub.API.ApiDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import example.cerki.osuhub.API.POJO.Following;
import example.cerki.osuhub.Columns;

/**
 * Created by cerki on 11-Dec-17.
 */

@Dao
public interface FollowingDao {
    @Query("SELECT * FROM following")
    List<Following> getAll();
    @Query("SELECT * FROM following WHERE id =:id")
    Following getBy(int id);
    @Insert
    void insert(Following following);
    @Delete
    void delete(Following following);
}
