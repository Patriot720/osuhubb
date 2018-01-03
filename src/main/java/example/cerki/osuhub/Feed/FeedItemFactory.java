package example.cerki.osuhub.Feed;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;

import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.API.POJO.FeedItem;
import example.cerki.osuhub.Mods;
import example.cerki.osuhub.Util;

public class FeedItemFactory {

    public static String getCoverUrl(String mapset_id){
        return "https://assets.ppy.sh//beatmaps/" + mapset_id + "/covers/card.jpg";
    }

    @SuppressLint("DefaultLocale")
    public static FeedItem getFeeditem(String username, BestScore score, example.cerki.osuhub.API.POJO.Beatmap beatmap){
        FeedItem feedItem = new FeedItem();
        feedItem.performance = String.format("%.0fPP",score.getPp());
        feedItem.accuracy = Util.getAccuracyString(score);
        feedItem.missCount = String.format("%dxMiss", score.getCountmiss());
        feedItem.mods = Mods.parseFlags(score.getEnabledMods());
        feedItem.starRate = String.format("%.2f",beatmap.getDifficultyrating());
        feedItem.mapName = String.format("%s[%s]",beatmap.getTitle(),beatmap.getVersion());
        feedItem.combo = String.format("%s/%s",score.getMaxcombo(),beatmap.getMaxCombo());
        feedItem.coverUrl = getCoverUrl(beatmap.getBeatmapsetId());
        feedItem.username = username;
        feedItem.rank =  score.getRank();
        feedItem.relativeDate = (String) DateUtils.getRelativeTimeSpanString(score.getDate().getTime());
        feedItem.date = score.getDate();
        feedItem.beatmap_id = score.getBeatmapId();
        return feedItem;
    }
}