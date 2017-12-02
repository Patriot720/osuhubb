package example.cerki.osuhub;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import example.cerki.osuhub.List.Player;

import static example.cerki.osuhub.Columns.ACTIVITY;
import static example.cerki.osuhub.Columns.COUNTRY;
import static example.cerki.osuhub.Columns.ID;
import static example.cerki.osuhub.Columns.RANK;
import static example.cerki.osuhub.Columns.USERNAME;

/**
 * Created by cerki on 30-Nov-17.
 */

public class PlayersTable implements  PlayersTableWrapper {

    private final SQLiteDatabase mDb;

    public PlayersTable(SQLiteDatabase db) {
        mDb = db;
    }

    private ContentValues generateContentValues(Player p){
        ContentValues cv = new ContentValues();
        cv.put(ID,p.getId());
        cv.put(USERNAME,p.getUsername());
        cv.put(COUNTRY,p.getCountry());
        cv.put(ACTIVITY,p.getActivity());
        for(String key : p.getKeySet()){
            String val = p.getString(key);
            if(val.contains("."))
                 cv.put(key,Float.parseFloat(val));
            else
                cv.put(key,Integer.parseInt(val));
        }
        return cv;
    }

    @Override
    public void insertPlayer(Player player) {
        mDb.replaceOrThrow(OsuDb.PLAYERS_TABLE_NAME,null,generateContentValues(player));
    }

    @Override
    public Player getPlayer(int userId) {
        String selection = ID + "=" + userId;
        Cursor query = mDb.query(OsuDb.PLAYERS_TABLE_NAME, null, selection, null, null, null, null);
        return new Player(query);
    }

}
