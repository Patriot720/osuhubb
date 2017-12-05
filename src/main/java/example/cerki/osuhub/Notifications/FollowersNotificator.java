package example.cerki.osuhub.Notifications;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import example.cerki.osuhub.OsuAPI;
import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;


class FollowersNotificator {

    public static Collection<Score> getNewScores(Following following) throws IOException, JSONException, ParseException {
        Date lastDate = Util.parseTimestamp(following.timestamp,TimeZone.getTimeZone("GMT"));
        return getScoresAfter(lastDate,following);
    }

    public static Collection<Score> getScoresAfter(Date lastDate,Following following) throws JSONException, ParseException, IOException {
        Collection<Score> scores = new OsuAPI().getPlayerBest(following.id);
        Collection<Score> newScores = new ArrayList<>();
        for (Score score : scores) {
            Date date = Util.parseTimestamp(score.get("date"),TimeZone.getTimeZone("GMT+8"));// todo extract to Columns
            if(lastDate.compareTo(date) < 0){
                newScores.add(score);
            }
        }
        return newScores;
    }
    public static Collection<Score> getMonthOldScores(Following following) throws ParseException, IOException, JSONException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        return getScoresAfter(result,following);
    }
}
