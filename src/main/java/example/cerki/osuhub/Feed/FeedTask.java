package example.cerki.osuhub.Feed;

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

import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.Following;
import example.cerki.osuhub.OsuAPI;
import example.cerki.osuhub.OsuDb;
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
        Collection<Following> all = new FollowersTable(new OsuDb(mContext).getWritableDatabase()).getAll();
        try {
            for (Following following : all) { // TODO  Cache Beatmaps and Scores;
                // Todo add search for players
                Collection<Score> monthOldScores = following.getMonthOldScores();
                for (Score monthOldScore : monthOldScores) {
                    FeedItem item = getFeedItem(following.username, monthOldScore);
                    items.add(item);
                }
            }
            Collections.sort(items);
            return items;
        } catch (ParseException | IOException | JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    private FeedItem getFeedItem(String username, Score monthOldScore) throws IOException, JSONException, ParseException {
        OsuAPI osuAPI = new OsuAPI();
        String beatmap_id = monthOldScore.get(Score.BEATMAP_ID);
        Beatmap beatmap = osuAPI.getBeatmap(beatmap_id);
        String cover_url = osuAPI.getCoverUrl(beatmap.get(Beatmap.MAPSET_ID));
        Date date = Util.parsePeppyTime(monthOldScore.get(Score.DATE));
        String relativeDate = (String) DateUtils.getRelativeTimeSpanString(date.getTime());
        return new FeedItem( username, monthOldScore, beatmap, cover_url, relativeDate);
    }

    @Override
    protected void onPostExecute(List<FeedItem> feedItems) {
        workDoneListener.workDone(feedItems);
    }
}
