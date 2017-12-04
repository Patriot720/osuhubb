package example.cerki.osuhub.Notifications;

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

import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;

/**
 * Created by cerki on 03-Dec-17.
 */

class FollowersNotificator {

    public static JSONArray getPlayerJsonArray(int id) throws IOException, JSONException {
        URL url = new  URL(Util.BASE_URL + "get_user_best?k=" + Util.API_KEY + "&u=" + id + "&limit=100");
        URLConnection inputStream = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null)
            stringBuilder.append(line).append("\n");
        return new JSONArray(stringBuilder.toString());
    }
    public static Collection<Score> getNewScores(Following follower) throws IOException, JSONException, ParseException {
        JSONArray jsonArray = getPlayerJsonArray(follower.id); // TODO get follower to constructor
        Date lastDate = Util.parseTimestamp(follower.timestamp,TimeZone.getTimeZone("GMT"));
        Collection<Score> scores = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Date date = Util.parseTimestamp(jsonObject.getString("date"),TimeZone.getTimeZone("GMT+8"));
            if(lastDate.compareTo(date) < 0){
                Score score = new Score();
                for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                    String key = it.next(); // todo refactor
                    String value = jsonObject.getString(key);
                    score.put(key,value);
                }
                scores.add(score);
            }
        }
        return scores;
    }
}
