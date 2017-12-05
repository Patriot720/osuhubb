package example.cerki.osuhub.Feed;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by cerki on 05-Dec-17.
 */

public class Beatmap extends HashMap<String,String> {
    public Beatmap(JSONObject jsonObject) throws JSONException {
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String key = it.next();
            put(key,jsonObject.getString(key));
        }
    }

    public Beatmap() {

    }
}
