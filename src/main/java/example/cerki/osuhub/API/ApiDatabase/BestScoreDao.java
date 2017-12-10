package example.cerki.osuhub.API.ApiDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
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
    @Query("SELECT * FROM bestscore WHERE userId=:userId AND timestamp=:date")
    BestScore getBy(String userId,Date date); // TOdo change to Date
    @Query("SELECT * FROM bestscore WHERE userId=:userId")
    List<BestScore> getBy(String userId);
    @Insert
    void insert(List<BestScore> score);
    @Delete
    void delete(BestScore score);
    @Query("DELETE FROM bestscore")
    void deleteEverything();
}
