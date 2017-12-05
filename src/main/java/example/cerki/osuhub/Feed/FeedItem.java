package example.cerki.osuhub.Feed;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;

/**
 * Created by cerki on 05-Dec-17.
 */

public class FeedItem implements  Comparable<FeedItem>{
    String beatmapImageUrl;
    Score score;
    Beatmap beatmap;
    String username;
    @Override
    public int compareTo(@NonNull FeedItem feedItem) {
        Score score = feedItem.score;
        String dateString = score.get("date");
        String thisDateString = this.score.get("date");
        if(dateString == null || thisDateString == null)
            return 0;
        try {
            Date date = Util.parseTimestamp(dateString, TimeZone.getTimeZone("GMT+8")); // TODO extract second argument it's always gmt+8
            Date thisDate = Util.parseTimestamp(thisDateString, TimeZone.getTimeZone("GMT+8"));
            return thisDate.compareTo(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
