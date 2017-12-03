package example.cerki.osuhub;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;

/**
 * Created by cerki on 03-Dec-17.
 */

public class FollowersNotificator {
    public static final String API_KEY = "b40b7a7a8207b1ebd870eaf1f74bd2995f1a2cb6";
    private static final String BASE_URL = "https://osu.ppy.sh/api/";

    public static JSONArray getJsonObject(int id) throws IOException, JSONException {
        URL url = new  URL(BASE_URL + "get_user_best?k=" + API_KEY + "&u=" + id + "&limit=100");
        URLConnection inputStream = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null)
            stringBuilder.append(line).append("\n");
        return new JSONArray(stringBuilder.toString());
    }
    public static Collection<Score> getNewScores(Following follower) throws IOException, JSONException, ParseException {
        JSONArray jsonArray = getJsonObject(follower.id);
        Date lastDate = Util.parseTimestamp(follower.timestamp, TimeZone.getDefault());
        Collection<Score> scores = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Date date = Util.parseTimestamp(jsonObject.getString("date"),TimeZone.getTimeZone("GMT+8"));
            if(lastDate.compareTo(date) <= 0){
                Score score = new Score();
                Log.d(TAG, "getNewScores: " + date.toString() + " " + date.toString());
                for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                    String name = it.next();
                    String value = jsonObject.getString(name);
                    score.put(name,value);
                }
                scores.add(score);
            }
        }
        return scores;
    }
}
