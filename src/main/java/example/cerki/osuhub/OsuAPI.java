package example.cerki.osuhub;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import example.cerki.osuhub.Feed.Beatmap;
import example.cerki.osuhub.List.Player;

/**
 * Created by cerki on 05-Dec-17.
 */

public class OsuAPI {
    public static final String BASE_URL = "https://osu.ppy.sh/api/";
    private String API_KEY = "b40b7a7a8207b1ebd870eaf1f74bd2995f1a2cb6";
    private int LIMIT = 100;

    public OsuAPI(String API_KEY) {
        this.API_KEY = API_KEY;
    }
    public OsuAPI(){
    }
     public  URL generateURL(String method, String query) throws MalformedURLException {
        String url =  BASE_URL + method + "?k=" + API_KEY + "&" + query;
        return new URL(url);
    }
    public Beatmap getBeatmap(String id) throws IOException, JSONException {
        JSONArray jsonArray = getFromApi(generateURL("get_beatmaps","b=" + id));
        if (jsonArray.length() == 0)
            return new Beatmap();
        return new Beatmap(jsonArray.getJSONObject(0));
    }
     public  JSONArray getFromApi(URL url) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null)
            stringBuilder.append(line).append("\n");
        return new JSONArray(stringBuilder.toString());
    }

    public Collection<Score> getPlayerBest(int id) throws IOException, JSONException {
        JSONArray scoreJsonArray = getFromApi(generateURL("get_user_best","u=" + id + "&limit=" + LIMIT)); // TODO refactor this
        Collection<Score> scores = new ArrayList<>();
        for (int i = 0; i < scoreJsonArray.length(); i++) {
            JSONObject scoreJson = scoreJsonArray.getJSONObject(i);
            scores.add(new Score(scoreJson));
        }
        return scores;
    }
    public Collection<Score> getPlayerBest(String id) throws IOException, JSONException {
        return getPlayerBest(Integer.parseInt(id)); // Todo better other way around
    }
    public Player getPlayer(int id) throws IOException, JSONException {
       JSONArray playerJson = getFromApi(generateURL("get_user","u=" + id));
       if(playerJson.length() == 0)
           return new Player();
       return new Player(playerJson.getJSONObject(0));
    }

    public Player getPlayer(String id) throws IOException, JSONException {
        return getPlayer(Integer.parseInt(id));
    }
}
