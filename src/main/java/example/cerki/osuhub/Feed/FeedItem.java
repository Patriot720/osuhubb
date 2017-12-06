package example.cerki.osuhub.Feed;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.Date;

import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;

/**
 * Created by cerki on 05-Dec-17.
 */

public class FeedItem implements  Comparable<FeedItem>{
    String coverUrl;
    Score score;
    Beatmap beatmap;
    String username;
    String relativeDate;

    public FeedItem(String username,Score score, Beatmap beatmap,String coverUrl,String relativeDate) {
        this.coverUrl = coverUrl;
        this.score = score;
        this.beatmap = beatmap;
        this.username = username;
        this.relativeDate = relativeDate;
    }

    @Override
    public int compareTo(@NonNull FeedItem feedItem) { // TODO sort by other things than date;
        Score score = feedItem.score;
        String dateString = score.get("date");
        String thisDateString = this.score.get("date");
        if(dateString == null || thisDateString == null)
            return 0;
        try {
            Date date = Util.parsePeppyTime(dateString);
            Date thisDate = Util.parsePeppyTime(thisDateString);
            return date.compareTo(thisDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
