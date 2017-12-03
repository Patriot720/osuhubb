package example.cerki.osuhub;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;

import static example.cerki.osuhub.Columns.Following.ID;
import static example.cerki.osuhub.Columns.Following.TIMESTAMP;
import static example.cerki.osuhub.OsuDb.FOLLOWERS_TABLE_NAME;

/**
 * Created by cerki on 02-Dec-17.
 */

public class FollowersTable {
    SQLiteDatabase mDb;

    public FollowersTable(SQLiteDatabase db) {
        mDb = db;
    }

    public FollowersTable() {

    }

    public void insertOrUpdateFollower(int userId) {
        ContentValues cv = new ContentValues();
        cv.put(ID, userId);
        mDb.replaceOrThrow(FOLLOWERS_TABLE_NAME, null, cv);
    }

    public String getTimestamp(int userId) {
        String columns[] = {TIMESTAMP};
        String selection = ID + "=" + userId;
        Cursor query = mDb.query(FOLLOWERS_TABLE_NAME, columns, selection, null, null, null, null);
        if (!query.moveToNext())
            return "";
        String res = query.getString(query.getColumnIndex(TIMESTAMP));
        query.close();
        return res;
    }

    public void deleteFollower(int userId) {
        String selection = ID + "=" + userId;
        mDb.delete(FOLLOWERS_TABLE_NAME, selection, null);
    }

    public Collection<Following> getAll() {
        Collection<Following> followers = new ArrayList<>();
        Cursor query = mDb.query(FOLLOWERS_TABLE_NAME, null, null, null, null, null, null);
        while (query.moveToNext()) {
            int id = query.getInt(query.getColumnIndex(ID));
            String timestamp = query.getString(query.getColumnIndex(TIMESTAMP));
            followers.add(new Following(id, timestamp));
        }
        query.close();
        return followers;
    }
}
