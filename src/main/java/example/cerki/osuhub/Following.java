package example.cerki.osuhub;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created by cerki on 03-Dec-17.
 */

public class Following {
    public int id;
    public String timestamp;
    public String username;

    public Following(int id, String timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public Following(int id, String timestamp, String username) {
        this.id = id;
        this.timestamp = timestamp;
        this.username = username;
    }
        // TODO change all followers to following
    public Collection<Score> getNewScores() throws IOException, JSONException, ParseException {
        Date lastDate = Util.parseSQLTime(timestamp);
        return getScoresAfter(lastDate);
    }

    public Collection<Score> getScoresAfter(Date lastDate) throws JSONException, ParseException, IOException {
        Collection<Score> scores = new OsuAPI().getPlayerBest(id);
        Collection<Score> newScores = new ArrayList<>();
        for (Score score : scores) {
            Date date = Util.parsePeppyTime(score.get("date"));// todo extract to Columns
            if(lastDate.compareTo(date) < 0){
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
