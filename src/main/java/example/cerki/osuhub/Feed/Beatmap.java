package example.cerki.osuhub.Feed;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by cerki on 05-Dec-17.
 */

public class Beatmap extends HashMap<String,String> {
    public static final String MAX_COMBO = "max_combo";
    public static final String MAP_NAME = "title";
    public static final String DIFFICULTY_NAME = "version" ;
    public static final String STAR_RATING = "difficultyrating" ;
    public static final String MAPSET_ID = "beatmapset_id";

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
