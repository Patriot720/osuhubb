package example.cerki.osuhub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static example.cerki.osuhub.Columns.*;

/**
 * Created by cerki on 30-Nov-17.
 */

public class OsuDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "osudb";
    public static final String PLAYERS_TABLE_NAME = "PlayersTable";
    public static final String FOLLOWERS_TABLE_NAME = "FollowersTable";

    public static final String PLAYERS_TABLE_CREATE = "CREATE TABLE " + PLAYERS_TABLE_NAME + "("
            + ID + " NUMBER PRIMARY KEY,"
            + USERNAME + " TEXT,"
            + COUNTRY + " TEXT,"
            + ACTIVITY + " BOOL,"
            + PP + " NUMBER,"
            + PC + " NUMBER,"
            + ACC +  " FLOAT,"
            + RANK + " NUMBER)";
    public static final String FOLLOWERS_TABLE_CREATE = "CREATE TABLE " + FOLLOWERS_TABLE_NAME + "("
            + Columns.Following.ID + " NUMBER PRIMARY KEY,"
            + Columns.Following.USERNAME + " TEXT,"
            + Columns.Following.TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    private static final int VERSION = 1;

    public OsuDb(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PLAYERS_TABLE_CREATE);
        sqLiteDatabase.execSQL(FOLLOWERS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PLAYERS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FOLLOWERS_TABLE_NAME);
    }
}
