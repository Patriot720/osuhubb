package example.cerki.osuhub;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by cerki on 03-Dec-17.
 */

public class Score extends  HashMap<String,String>{
    public Score(JSONObject jsonObject) throws JSONException {
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String key = it.next(); // todo refactor
            String value = jsonObject.getString(key);
            this.put(key,value);
        }
    }

    public int getAsInt(String key) {
        return Integer.parseInt(get(key));
    }

    @NonNull
    public String generateScoreString(String username) {
        StringBuilder builder = new StringBuilder();
        builder.append(username)
                .append(" ")
                .append(get("pp"))
                .append(" ")
                .append(Util.calculateAccuracy(this))
                .append("\n");
        // TODO FETCH MAP DATA
        // TODO GetUsername from follower, add username to follower
        return builder.toString();
    }
}
