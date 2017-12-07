package example.cerki.osuhub.Feed;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by cerki on 05-Dec-17.
 */

public class FeedItem implements  Comparable<FeedItem>{
    String coverUrl;
    String username;
    String relativeDate;
    String performance;
    String accuracy;
    String combo;
    String mods;
    String mapName;
    String starRate;
    String missCount;
    int rankResource;
    Date date;


    public FeedItem() {

    }

    @Override
    public int compareTo(@NonNull FeedItem feedItem) { // TODO sort by other things than date;
        if(date == null || feedItem.date == null)
            return 0;
        return feedItem.date.compareTo(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedItem feedItem = (FeedItem) o;

        if (coverUrl != null ? !coverUrl.equals(feedItem.coverUrl) : feedItem.coverUrl != null)
            return false;
        if (username != null ? !username.equals(feedItem.username) : feedItem.username != null)
            return false;
        if (performance != null ? !performance.equals(feedItem.performance) : feedItem.performance != null)
            return false;
        if (accuracy != null ? !accuracy.equals(feedItem.accuracy) : feedItem.accuracy != null)
            return false;
        if (combo != null ? !combo.equals(feedItem.combo) : feedItem.combo != null) return false;
        if (mods != null ? !mods.equals(feedItem.mods) : feedItem.mods != null) return false;
        if (mapName != null ? !mapName.equals(feedItem.mapName) : feedItem.mapName != null)
            return false;
        if (starRate != null ? !starRate.equals(feedItem.starRate) : feedItem.starRate != null)
            return false;
        return missCount != null ? missCount.equals(feedItem.missCount) : feedItem.missCount == null;
    }

    @Override
    public int hashCode() {
        int result = coverUrl != null ? coverUrl.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (performance != null ? performance.hashCode() : 0);
        result = 31 * result + (accuracy != null ? accuracy.hashCode() : 0);
        result = 31 * result + (combo != null ? combo.hashCode() : 0);
        result = 31 * result + (mods != null ? mods.hashCode() : 0);
        result = 31 * result + (mapName != null ? mapName.hashCode() : 0);
        result = 31 * result + (starRate != null ? starRate.hashCode() : 0);
        result = 31 * result + (missCount != null ? missCount.hashCode() : 0);
        return result;
    }
}
