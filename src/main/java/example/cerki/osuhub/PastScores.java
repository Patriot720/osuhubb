package example.cerki.osuhub;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class PastScores {
    private final Following following;
    private  OsuAPI api;

    public PastScores(Following following) {
        this.following = following;
        api = new OsuAPI();
    }// TODO change all followers to following

    public PastScores(Following following, OsuAPI api) {
        this.following = following;
        this.api = api;
    }

    public Collection<Score> getNewScores() throws IOException, JSONException, ParseException {
        Date lastDate = Util.parseSQLTime(following.timestamp);
        return getScoresAfter(lastDate);
    }

    public Collection<Score> getScoresAfter(Date lastDate) throws JSONException, ParseException, IOException {
        Collection<Score> scores = api.getPlayerBest(following.id);
        Collection<Score> newScores = new ArrayList<Score>();
        for (Score score : scores) {
            Date date = Util.parsePeppyTime(score.get("date"));// todo extract to Columns
            if (lastDate.compareTo(date) < 0) {
                newScores.add(score);
            }
        }
        return newScores;
    }

    public Collection<Score> getMonthOldScores() throws ParseException, IOException, JSONException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        return getScoresAfter(result);
    }
}