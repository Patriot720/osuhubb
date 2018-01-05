package example.cerki.osuhub.Data.ApiDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import example.cerki.osuhub.Data.ApiDatabase.Dao.BeatmapDao;
import example.cerki.osuhub.Data.ApiDatabase.Dao.BestScoreDao;
import example.cerki.osuhub.Data.ApiDatabase.Dao.FollowingDao;
import example.cerki.osuhub.Data.ApiDatabase.Dao.UserDao;
import example.cerki.osuhub.Data.POJO.Beatmap;
import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.Data.POJO.Following;
import example.cerki.osuhub.Data.POJO.User;

/**
 * Created by cerki on 10-Dec-17.
 */

@Database(entities = {BestScore.class, Following.class, Beatmap.class,User.class},version = 11,exportSchema = false)
public abstract class ApiDatabase extends RoomDatabase {
    public abstract BestScoreDao bestScoreDao();
    public abstract FollowingDao followingDao();
    public abstract BeatmapDao beatmapDao();
    public abstract UserDao userDao();


    public static final String DATABASE_NAME ="api-db";
    private static ApiDatabase db;

    public static ApiDatabase createInstance(Context context){
        db = Room.databaseBuilder(context,ApiDatabase.class,DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        return db;
    }

    public static ApiDatabase getInstance(){
        return db;
    }
}
