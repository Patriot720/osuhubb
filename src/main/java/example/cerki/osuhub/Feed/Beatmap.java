package example.cerki.osuhub.Feed;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by cerki on 05-Dec-17.
 */

public class Beatmap extends HashMap<String,String> {
    public static final String TITLE = "title";
    public static final String DIFFICULTY_NAME = "version" ;
    public static final String STAR_RATING = "difficultyrating" ;
    public static final String MAPSET_ID = "beatmapset_id";
    public static final String MAP_ID = "beatmap_id";
    public static final String FAVOURITE_COUNT = "favourite_count";
    public static final String PLAYCOUNT = "playcount";
    public static final String PASSCOUNT = "passcount";
    public static final String MAX_COMBO = "max_combo";
    public static final String GAME_MODE = "mode";
    public static final String LENGTH = "total_length";
    public static final String ARTIST = "artist";
    public static final String BPM = "bpm";
    public static final String CS = "diff_size";
    public static final String OD = "diff_overall";
    public static final String AR = "diff_approach";
    public static final String HP = "diff_drain";
    public static final String APPROVED = "approved";
    public static final String APPROVED_DATE = "approved_date";
    public static final String LAST_UPDATE = "last_update";
    public static final String BEATMAP_CREATOR = "creator";
    public static final String HIT_LENGTH = "hit_length";
    public static final String SOURCE = "source";
    public static final String GENRE_ID = "genre_id";
    public static final String LANGUAGE_ID = "language_id";
    public static final String FILE_MD5 = "file_md5";
    public static final String TAGS = "tags";



    public Beatmap(JSONObject jsonObject) throws JSONException {
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String key = it.next();
            put(key,jsonObject.getString(key));
        }
    }

    public Beatmap() {

    }

    public Double getAsDouble(String key) {
        return Double.parseDouble(get(key));
    }
}
