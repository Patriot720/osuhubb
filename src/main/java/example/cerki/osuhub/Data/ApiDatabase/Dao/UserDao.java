package example.cerki.osuhub.Data.ApiDatabase.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import example.cerki.osuhub.Data.POJO.User;

/**
 * Created by cerki on 12-Dec-17.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE userId=:userId")
    User getUserBy(int userId);
    @Query("SELECT * FROM user WHERE username=:username")
    User getUserBy(String username);
    @Query("SELECT * FROM user ORDER BY ppRank")
    List<User> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);
    @Delete
    void delete(User user);
}
