package example.cerki.osuhub.Feed;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.Following;
import example.cerki.osuhub.OsuAPI;
import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.Score;

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
        Collection<Following> all = new FollowersTable(new OsuDb(mContext).getWritableDatabase()).getAll();
        OsuAPI osuAPI = new OsuAPI();
        Collection<Score> monthOldScores = new ArrayList<>();
        try {
            for (Following following : all) {
                monthOldScores = osuAPI.getMonthOldScores(following); // Todo get username to each feed_item
                for (Score monthOldScore : monthOldScores) {
                    final FeedItem feedItem = new FeedItem();
                    feedItem.score = monthOldScore;
                    String beatmap_id = monthOldScore.get(Score.BEATMAP_ID);
                    Beatmap beatmap = osuAPI.getBeatmap(beatmap_id);
                    feedItem.beatmap = beatmap;
                    String url= osuAPI.getCoverUrl(beatmap.get(Beatmap.MAPSET_ID));
                    feedItem.beatmapImageUrl = url;
                    feedItem.username = following.username;
                    items.add(feedItem);
                }
            }
            Collections.sort(items);
            return items;
        } catch (ParseException | IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<FeedItem> feedItems) {
        workDoneListener.workDone(feedItems);
    }
}
