package example.cerki.osuhub.Feed;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.Mods;
import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;

public class FeedItemFactory {

    public static String getCoverUrl(String mapset_id){
        return "https://assets.ppy.sh//beatmaps/" + mapset_id + "/covers/card.jpg";
    }

    @SuppressLint("DefaultLocale")
    @Deprecated
    static FeedItem getFeedItem(String username, Score monthOldScore,Beatmap beatmap) throws IOException, JSONException, ParseException {
        FeedItem feedItem = new FeedItem();
        Date date = Util.parsePeppyTime(monthOldScore.get(Score.DATE));
        feedItem.performance = String.format("%sPP", monthOldScore.getAsInt(Score.PP));
        feedItem.accuracy = Util.getAccuracyString(monthOldScore);
        feedItem.missCount = String.format("%sxMiss", monthOldScore.get(Score.MISS));
        feedItem.mods = Mods.parseFlags(monthOldScore.get(Score.MODS));
        feedItem.starRate = String.format("%.2f", beatmap.getAsDouble(Beatmap.STAR_RATING));
        feedItem.mapName = String.format("%s[%s]", beatmap.get(Beatmap.TITLE), beatmap.get(Beatmap.DIFFICULTY_NAME));
        feedItem.combo = String.format("%s/%s", monthOldScore.get(Score.COMBO), beatmap.get(Beatmap.MAX_COMBO));
        feedItem.coverUrl = getCoverUrl(beatmap.get(Beatmap.MAPSET_ID));
        feedItem.username = username;
        feedItem.rankURI = "@drawable/" + monthOldScore.get(Score.RANK).toLowerCase();
        feedItem.relativeDate = (String) DateUtils.getRelativeTimeSpanString(date.getTime());
        feedItem.date = date;
        return feedItem;
    }
    @SuppressLint("DefaultLocale")
    static FeedItem getFeeditem(String username, BestScore score, example.cerki.osuhub.API.POJO.Beatmap beatmap){
        FeedItem feedItem = new FeedItem();
        feedItem.performance = String.format("%.0fPP",score.getPp());
        feedItem.accuracy = Util.getAccuracyString(score);
        feedItem.missCount = String.format("%dxMiss", score.getCountmiss());
        feedItem.mods = Mods.parseFlags(score.getEnabledMods());
        feedItem.starRate = String.format("%.2f",beatmap.getDifficultyrating());
        feedItem.mapName = String.format("%s[%s]",beatmap.getTitle(),beatmap.getVersion());
        feedItem.combo = String.format("%s/%s",score.getMaxcombo(),beatmap.getMaxCombo());
        feedItem.coverUrl = getCoverUrl(beatmap.getBeatmapsetId()); // Todo change this
        feedItem.username = username;
        feedItem.rank =  score.getRank();
        feedItem.relativeDate = (String) DateUtils.getRelativeTimeSpanString(score.getDate().getTime());
        feedItem.date = score.getDate();
        return feedItem;
    }
}