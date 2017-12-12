package example.cerki.osuhub;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import example.cerki.osuhub.Feed.Beatmap;

/**
 * Created by cerki on 07-Dec-17.
 */

@Deprecated
public class BeatmapsTable { // Todo make them static maybe with(OsuDb.getInstance()).insert(stuff).into(TABLE)
    private SQLiteDatabase mDb;

    public BeatmapsTable(SQLiteDatabase mDb) {
        this.mDb = mDb;
    }

    public Beatmap get(String mapId) {
        String selection = Beatmap.MAP_ID + "=" + mapId;
        Cursor query = mDb.query(OsuDb.BEATMAPS_TABLE_NAME, null, selection, null, null, null, null);
        Beatmap beatmap = new Beatmap();
        if(!query.moveToFirst()) {
            query.close();
            return beatmap;
        }
        for (int i = 0; i < query.getColumnCount(); i++) {
            String columnName = query.getColumnName(i);
            if(query.getString(i) != null)
                beatmap.put(columnName,query.getString(i));
        }
        query.close();
        return beatmap;
    }

    public void insertOrUpdate(Beatmap beatmap) {
        ContentValues contentValues = ContentValuesFactory.generate(beatmap);
        mDb.replaceOrThrow(OsuDb.BEATMAPS_TABLE_NAME,null,contentValues);
    }
    public void close(){
        mDb.close();
    }
}
