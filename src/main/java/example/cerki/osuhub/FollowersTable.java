package example.cerki.osuhub;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static example.cerki.osuhub.Columns.Following.ID;
import static example.cerki.osuhub.Columns.Following.TIMESTAMP;

/**
 * Created by cerki on 02-Dec-17.
 */

public class FollowersTable {
    SQLiteDatabase mDb;
    public FollowersTable(SQLiteDatabase db) {
        mDb = db;
    }

    public void insertOrUpdateFollower(int userId){
        ContentValues cv = new ContentValues();
        cv.put(ID,userId);
        mDb.replaceOrThrow(OsuDb.FOLLOWERS_TABLE_NAME,null,cv);
    }
    public String getTimestamp(int userId){
        String columns[] = {TIMESTAMP};
        String selection = ID + "=" + userId;
        Cursor query = mDb.query(OsuDb.FOLLOWERS_TABLE_NAME, columns, selection, null, null, null, null);
        if(!query.moveToNext())
            return "";
        String res = query.getString(query.getColumnIndex(TIMESTAMP));
        query.close();
        return res;
    }

    public void deleteFollower(int userId) {
        String selection = ID + "=" + userId;
        mDb.delete(OsuDb.FOLLOWERS_TABLE_NAME,selection,null);
    }
}
