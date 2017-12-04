package example.cerki.osuhub.PlayerFragment.Overview;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import example.cerki.osuhub.List.Player;
import example.cerki.osuhub.Util;

/**
 * Created by cerki on 02-Dec-17.
 */

class PlayerInfoTask extends AsyncTask<Integer, Void, Player> {
    workDoneListener workDoneListener;

    public PlayerInfoTask(PlayerInfoTask.workDoneListener workDoneListener) {
        this.workDoneListener = workDoneListener;
    }

    public  interface workDoneListener {
        void workDone(Player player);
    }
    // TODO test this
    @Override
    protected Player doInBackground(Integer... userId) {
        try {
            Player player = new Player();
            String urlString = Util.BASE_URL + "get_user?k=" + Util.API_KEY + "&u=" + userId[0];
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null)
                builder.append(line).append("\n");
            JSONArray array = new JSONArray(builder.toString());
            if(array.length() == 0) {
                return player;
            }
            JSONObject jsonObject = array.getJSONObject(0);
            return new Player(jsonObject);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Player player) {
        workDoneListener.workDone(player);
    }
}
