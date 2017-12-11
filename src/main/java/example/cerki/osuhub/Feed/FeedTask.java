package example.cerki.osuhub.Feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import example.cerki.osuhub.BeatmapsTable;
import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.API.POJO.Following;
import example.cerki.osuhub.OsuAPI;
import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.PastScores;
import example.cerki.osuhub.Score;

/**
 * Created by cerki on 05-Dec-17.
 */
@Deprecated
public class FeedTask extends AsyncTask<Void, Void, List<FeedItem>> {
    private final Context mContext;
    private final FeedItemFactory feedItemFactory = new FeedItemFactory(this);

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
                    FeedItem feedItem = getFeedItem(following.username,monthOldScore);
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
        return feedItemFactory.getFeedItem(username, monthOldScore,getBeatmap(monthOldScore.get(Score.BEATMAP_ID)));
    }

    @Override
    protected void onPostExecute(List<FeedItem> feedItems) {
        workDoneListener.workDone(feedItems);
    }
}
