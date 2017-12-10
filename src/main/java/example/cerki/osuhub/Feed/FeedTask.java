package example.cerki.osuhub.Feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateUtils;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import example.cerki.osuhub.BeatmapsTable;
import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.Following;
import example.cerki.osuhub.Mods;
import example.cerki.osuhub.NewDatabase;
import example.cerki.osuhub.OsuAPI;
import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.PastScores;
import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;

/**
 * Created by cerki on 05-Dec-17.
 */

public class FeedTask extends AsyncTask<Void, Void, List<FeedItem>> {
    private final Context mContext;

    interface WorkDoneListener {
        public void workDone(List<FeedItem> items);
    }

    WorkDoneListener workDoneListener;


    public FeedTask(Context context, WorkDoneListener workDoneListener) {
        this.workDoneListener = workDoneListener;
        this.mContext = context;
    }

    @Override
    protected List<FeedItem> doInBackground(Void... voids) {
        List<FeedItem> items = new ArrayList<>();
        FollowersTable table = new FollowersTable(new OsuDb(mContext).getWritableDatabase()); // Todo pain in the ass
        Collection<Following> all = table.getAll();
        table.close();
        try {
            for (Following following : all) { // TODO  Cache Beatmaps and Scores;
                // Todo add search for players
                Collection<Score> monthOldScores = new PastScores(following).getMonthOldScores(); // TODO get BY WEEKS Bottleneck
                for (Score monthOldScore : monthOldScores) {
                    FeedItem feedItem = NewDatabase.getInstance().feedItemDao().get(following.username, Util.parsePeppyTime(monthOldScore.get(Score.DATE))); // TODO delete this
                    if(feedItem == null) {
                         feedItem = getFeedItem(following.username, monthOldScore);
                    }
                        items.add(feedItem);
                }
            }
            Collections.sort(items);
            return items;
        } catch (ParseException | IOException | JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    public String getCoverUrl(String mapset_id){
        return "https://assets.ppy.sh//beatmaps/" + mapset_id + "/covers/card.jpg";
    }
    public Beatmap getBeatmap(String beatmap_id) throws IOException, JSONException {
        BeatmapsTable beatmapsTable = new BeatmapsTable(new OsuDb(mContext).getWritableDatabase());
        Beatmap beatmap = beatmapsTable.get(beatmap_id);
        if(!beatmap.isEmpty()) {
            beatmapsTable.close();
            return beatmap;
        }
        Beatmap serverBeatmap = new OsuAPI().getBeatmap(beatmap_id);
        beatmapsTable.insertOrUpdate(serverBeatmap);
        beatmapsTable.close();
        return serverBeatmap;
    }
    @SuppressLint("DefaultLocale")
    private FeedItem getFeedItem(String username, Score monthOldScore) throws IOException, JSONException, ParseException {
        FeedItem feedItem = new FeedItem();
        String beatmap_id = monthOldScore.get(Score.BEATMAP_ID);
        Beatmap beatmap = getBeatmap(beatmap_id);
        Date date = Util.parsePeppyTime(monthOldScore.get(Score.DATE));
        feedItem.performance = String.format("%sPP", monthOldScore.getAsInt(Score.PP));
        feedItem.accuracy = Util.getAccuracyString(monthOldScore);
        feedItem.missCount = String.format("%sxMiss", monthOldScore.get(Score.MISS));
        feedItem.mods = Mods.parseFlags(monthOldScore.get(Score.MODS));
        feedItem.starRate = String.format("%.2f",beatmap.getAsDouble(Beatmap.STAR_RATING));
        feedItem.mapName = String.format("%s[%s]",beatmap.get(Beatmap.TITLE),beatmap.get(Beatmap.DIFFICULTY_NAME));
        feedItem.combo = String.format("%s/%s", monthOldScore.get(Score.COMBO), beatmap.get(Beatmap.MAX_COMBO));
        feedItem.coverUrl = getCoverUrl(beatmap.get(Beatmap.MAPSET_ID));
        feedItem.username = username;
        String uri = "@drawable/" + monthOldScore.get(Score.RANK).toLowerCase();
        feedItem.rankResource = mContext.getResources().getIdentifier(uri,null,mContext.getPackageName());
        feedItem.relativeDate = (String) DateUtils.getRelativeTimeSpanString(date.getTime());
        feedItem.date = date;
        NewDatabase.getInstance().feedItemDao().insert(feedItem); // TODO change this
        return feedItem;
    }

    @Override
    protected void onPostExecute(List<FeedItem> feedItems) {
        workDoneListener.workDone(feedItems);
    }
}
