package example.cerki.osuhub.Feed;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import example.cerki.osuhub.Converters;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by cerki on 09-Dec-17.
 */

@Dao
@TypeConverters({Converters.class})
public interface FeedItemDao {
    @Query("SELECT * FROM feeditem WHERE username=:username AND date=:date")
    FeedItem get(String username, Date date);
    @Query("SELECT * FROM feeditem")
    List<FeedItem> getAll();
    @Query("SELECT * FROM feeditem WHERE username=:username")
    List<FeedItem> getByUsername(String username);
    @Insert
    void insert(FeedItem feedItem);
    @Delete
    void delete(FeedItem feedItem);
    @Query("DELETE FROM feeditem")
    void deleteAll();
}
