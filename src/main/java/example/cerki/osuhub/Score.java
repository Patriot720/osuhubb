package example.cerki.osuhub;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;


// TODO comparable only works for dates !!!;
public class Score extends  HashMap<String,String>{
    public static final String MISS = "countmiss" ;
    public static final String PP = "pp" ;
    public static final String COMBO = "maxcombo" ;
    public static final String RANK = "rank";
    public static final String BEATMAP_ID = "beatmap_id";
    public static final String MODS = "enabled_mods";
    public static final String DATE = "date";

    public Score(JSONObject jsonObject) throws JSONException {
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String key = it.next(); // todo refactor
            String value = jsonObject.getString(key);
            this.put(key,value);
        }
    }

    protected Score() {

    }

    public int getAsInt(String key) {
        Double v = Double.parseDouble(get(key));
        return  v.intValue();
    }

    @NonNull
    public String generateScoreString(String username) {
        StringBuilder builder = new StringBuilder();
        builder.append(username)
                .append(" ")
                .append(get("pp"))
                .append(" ")
                .append(Util.getAccuracyString(this))
                .append("\n");
        // TODO FETCH MAP DATA
        // TODO GetUsername from follower, add username to follower
        return builder.toString();
    }


}
