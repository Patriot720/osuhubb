package example.cerki.osuhub;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import example.cerki.osuhub.List.Player;

import static example.cerki.osuhub.Columns.ID;

/**
 * Created by cerki on 30-Nov-17.
 */

public class PlayersTable implements  PlayersTableWrapper {

    private final SQLiteDatabase mDb;

    public PlayersTable(SQLiteDatabase db) {
        mDb = db;
    }

    @Override
    public void insertPlayer(Player player) {
        mDb.replaceOrThrow(OsuDb.PLAYERS_TABLE_NAME,null, ContentValuesGenerator.generateContentValues(player));
    }

    @Override
    public Player getPlayer(int userId) {
        String selection = ID + "=" + userId;
        Cursor query = mDb.query(OsuDb.PLAYERS_TABLE_NAME, null, selection, null, null, null, null);
        Player p = new Player(query);
        query.close();
        return p;
    }

}
