package example.cerki.osuhub;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;


import example.cerki.osuhub.API.POJO.Following;

import static example.cerki.osuhub.Columns.Following.ID;
import static example.cerki.osuhub.Columns.Following.TIMESTAMP;
import static example.cerki.osuhub.Columns.Following.USERNAME;
import static example.cerki.osuhub.OsuDb.FOLLOWERS_TABLE_NAME;

/**
 * Created by cerki on 02-Dec-17.
 */
// TODO mDb leaks bug;
    // TODO extract Table class parent
public class FollowersTable {
    SQLiteDatabase mDb;

    public FollowersTable(SQLiteDatabase db) {

        mDb = db;
    }



    public void insertOrUpdate(int userId) {
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
            String username = query.getString(query.getColumnIndex(USERNAME));
            followers.add(new Following(id, timestamp,username));
        }
        query.close();
        return followers;
    }
    public void close(){
        mDb.close();
    }



    public void insertOrUpdate(int id, String username) {
        ContentValues cv = new ContentValues();
        cv.put(ID,id);
        cv.put(USERNAME,username);
        mDb.replaceOrThrow(OsuDb.FOLLOWERS_TABLE_NAME,null,cv);
    }
}
