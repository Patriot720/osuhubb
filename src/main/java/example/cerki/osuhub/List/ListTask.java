package example.cerki.osuhub.List;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.cerki.osuhub.PlayerParser;


public class ListTask extends AsyncTask<String,Void,List<Player>> {
    public static final String REQUEST_URL = "https://osu.ppy.sh/rankings/osu/performance";
    private WorkDoneListener workDoneListener;

    public ListTask(WorkDoneListener workDoneListener) {
        this.workDoneListener = workDoneListener;
    }

    @Override
    protected List<Player> doInBackground(String... url) {
        ArrayList<Player> players = new ArrayList<>();
        try {
            Document page = Jsoup.connect(url[0]).get();
            Elements tbody = page.select("tbody").first().children();
            for (Element tr : tbody) {
                players.add(PlayerParser.parsePlayer(tr));
            }
            return players;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    protected void onPostExecute(List<Player> players) {
        workDoneListener.workDone(players);
    }

    public void loadPlayersFromPage(int number) {
        execute(REQUEST_URL + "?page=" + number);
    }

    public void loadPlayers() {
        execute(REQUEST_URL);
    }
}
