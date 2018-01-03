package example.cerki.osuhub.List;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.ApiDatabase.UserDao;
import example.cerki.osuhub.API.POJO.User;
import example.cerki.osuhub.UserParser;


public class Task extends AsyncTask<String,Void,List<User>> {
    public static final String REQUEST_URL = "https://osu.ppy.sh/rankings/osu/performance";
    public interface WorkDoneListener{
        void workDone(List<User> users);
    }
    private WorkDoneListener workDoneListener;

    public Task(WorkDoneListener workDoneListener) {
        this.workDoneListener = workDoneListener;
    }

    @Override
    protected List<User> doInBackground(String... url) {
        ArrayList<User> players = new ArrayList<>();
        UserDao userDao = ApiDatabase.getInstance().userDao();
        try {
            Document page = Jsoup.connect(url[0]).get();
            Elements tbody = page.select("tbody").first().children();
            for (Element tr : tbody) {
                User user = UserParser.parseUser(tr);
                User dbUser = userDao.getUserBy(user.getUserId());
                if(dbUser != null)
                    user.compare(dbUser);
                userDao.insert(user);
                players.add(user);
            }
            return players;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    protected void onPostExecute(List<User> players) {
        workDoneListener.workDone(players);
    }

    public void loadUsersFromPage(int number) {
        execute(REQUEST_URL + "?page=" + number);
    }

    public void loadUsers() {
        execute(REQUEST_URL);
    }
}
