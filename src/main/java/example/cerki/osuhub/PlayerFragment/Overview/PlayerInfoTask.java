package example.cerki.osuhub.PlayerFragment.Overview;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import example.cerki.osuhub.List.Player;
import example.cerki.osuhub.OsuAPI;

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
           return new OsuAPI().getPlayer(userId[0]);
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
