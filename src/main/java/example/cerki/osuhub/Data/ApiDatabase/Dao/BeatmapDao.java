package example.cerki.osuhub.Data.ApiDatabase.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import example.cerki.osuhub.Data.POJO.Beatmap;


/**
 * Created by cerki on 11-Dec-17.
 */

@Dao
public interface BeatmapDao {
    @Query("SELECT * FROM Beatmap WHERE beatmapId=:mapId")
    Beatmap getBy(int mapId);
    @Insert
    void insert(Beatmap beatmap);
    @Delete
    void delete(Beatmap beatmap);
}
