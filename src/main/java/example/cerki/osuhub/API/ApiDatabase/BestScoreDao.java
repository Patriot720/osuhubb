package example.cerki.osuhub.API.ApiDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import example.cerki.osuhub.API.Converters;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.Feed.FeedItem;

/**
 * Created by cerki on 10-Dec-17.
 */

@Dao
@TypeConverters({Converters.class})
public interface BestScoreDao {
    @Query("SELECT * FROM bestscore WHERE userId=:userId AND date=:date")
    BestScore getBy(int userId,Date date);
    @Query("SELECT * FROM bestscore WHERE userId=:userId")
    List<BestScore> getBy(int userId);
    @Query("SELECT * FROM bestscore WHERE date > :date")
    List<BestScore> getScoresAfter(Date date);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<BestScore> score);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BestScore score);
    @Delete
    void delete(BestScore score);
    @Query("DELETE FROM bestscore")
    void deleteEverything();
}
