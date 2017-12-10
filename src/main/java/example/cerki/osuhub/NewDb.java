package example.cerki.osuhub;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import example.cerki.osuhub.Feed.FeedItem;
import example.cerki.osuhub.Feed.FeedItemDao;

/**
 * Created by cerki on 09-Dec-17.
 */
@Database(entities = {FeedItem.class},version = 1,exportSchema = false)
public abstract class NewDb extends RoomDatabase {
    public abstract FeedItemDao feedItemDao();
}
