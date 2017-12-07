package example.cerki.osuhub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static example.cerki.osuhub.Columns.ACC;
import static example.cerki.osuhub.Columns.ACTIVITY;
import static example.cerki.osuhub.Columns.COUNTRY;
import static example.cerki.osuhub.Columns.ID;
import static example.cerki.osuhub.Columns.PC;
import static example.cerki.osuhub.Columns.PP;
import static example.cerki.osuhub.Columns.RANK;
import static example.cerki.osuhub.Columns.USERNAME;
import static example.cerki.osuhub.Feed.Beatmap.APPROVED;
import static example.cerki.osuhub.Feed.Beatmap.APPROVED_DATE;
import static example.cerki.osuhub.Feed.Beatmap.AR;
import static example.cerki.osuhub.Feed.Beatmap.ARTIST;
import static example.cerki.osuhub.Feed.Beatmap.BEATMAP_CREATOR;
import static example.cerki.osuhub.Feed.Beatmap.BPM;
import static example.cerki.osuhub.Feed.Beatmap.CS;
import static example.cerki.osuhub.Feed.Beatmap.DIFFICULTY_NAME;
import static example.cerki.osuhub.Feed.Beatmap.FAVOURITE_COUNT;
import static example.cerki.osuhub.Feed.Beatmap.FILE_MD5;
import static example.cerki.osuhub.Feed.Beatmap.GAME_MODE;
import static example.cerki.osuhub.Feed.Beatmap.GENRE_ID;
import static example.cerki.osuhub.Feed.Beatmap.HIT_LENGTH;
import static example.cerki.osuhub.Feed.Beatmap.HP;
import static example.cerki.osuhub.Feed.Beatmap.LANGUAGE_ID;
import static example.cerki.osuhub.Feed.Beatmap.LAST_UPDATE;
import static example.cerki.osuhub.Feed.Beatmap.LENGTH;
import static example.cerki.osuhub.Feed.Beatmap.MAPSET_ID;
import static example.cerki.osuhub.Feed.Beatmap.MAP_ID;
import static example.cerki.osuhub.Feed.Beatmap.MAX_COMBO;
import static example.cerki.osuhub.Feed.Beatmap.OD;
import static example.cerki.osuhub.Feed.Beatmap.PASSCOUNT;
import static example.cerki.osuhub.Feed.Beatmap.PLAYCOUNT;
import static example.cerki.osuhub.Feed.Beatmap.SOURCE;
import static example.cerki.osuhub.Feed.Beatmap.STAR_RATING;
import static example.cerki.osuhub.Feed.Beatmap.TAGS;
import static example.cerki.osuhub.Feed.Beatmap.TITLE;

/**
 * Created by cerki on 30-Nov-17.
 */

public class OsuDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "osudb";
    public static final String PLAYERS_TABLE_NAME = "PlayersTable";
    public static final String FOLLOWERS_TABLE_NAME = "FollowersTable";
    public static final String BEATMAPS_TABLE_NAME = "BeatmapsTable";

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
    public static final String BEATMAPS_TABLE_CREATE = "CREATE TABLE " + BEATMAPS_TABLE_NAME + "("
            + MAP_ID + " NUMBER PRIMARY KEY,"
            + MAPSET_ID + " NUMBER,"
            + LAST_UPDATE + " TEXT,"
            + TITLE + " TEXT,"
            + MAX_COMBO + " NUMBER,"
            + DIFFICULTY_NAME + " TEXT,"
            + STAR_RATING + " NUMBER,"
            + FAVOURITE_COUNT + " NUMBER,"
            + PLAYCOUNT + " NUMBER,"
            + PASSCOUNT + " NUMBER,"
            + GAME_MODE + " NUMBER,"
            + LENGTH + " NUMBER,"
            + ARTIST + " TEXT,"
            + APPROVED + " BOOL,"
            + APPROVED_DATE + " TEXT,"
            + BEATMAP_CREATOR + " TEXT,"
            + SOURCE + " TEXT,"
            + GENRE_ID + " TEXT,"
            + LANGUAGE_ID + " TEXT,"
            + FILE_MD5 + " TEXT,"
            + TAGS + " TEXT,"
            + HIT_LENGTH + " NUMBER,"
            + BPM + " NUMBER,"
            + CS + " NUMBER,"
            + OD + " NUMBER,"
            + HP + " NUMBER,"
            + AR + " NUMBER)";

    private static final int VERSION = 1;

    public OsuDb(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PLAYERS_TABLE_CREATE);
        sqLiteDatabase.execSQL(FOLLOWERS_TABLE_CREATE);
        sqLiteDatabase.execSQL(BEATMAPS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PLAYERS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FOLLOWERS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BEATMAPS_TABLE_NAME);
    }
}
