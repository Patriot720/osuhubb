package example.cerki.osuhub.Logic.Tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import example.cerki.osuhub.Data.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.Data.POJO.*;
import example.cerki.osuhub.Data.POJO.Beatmap;
import example.cerki.osuhub.Data.Factories.FeedItemFactory;
import example.cerki.osuhub.Logic.Tasks.FeedNetworkTask.WorkDoneListener;
import example.cerki.osuhub.Util.Util;

/**
 * Created by cerki on 11-Dec-17.
 */

public class FeedDbTask extends AsyncTask<Void,Void,List<FeedItem>>{
    private WorkDoneListener workDoneListener;

    public FeedDbTask(WorkDoneListener workDoneListener) {
        this.workDoneListener = workDoneListener;
    }

    protected List<BestScore> getScores() {
        List<BestScore> scoresAfter = ApiDatabase.getInstance().bestScoreDao().getScoresAfter(Util.getMonthOldDate());
        if(scoresAfter != null)
            return scoresAfter;
        return new ArrayList<>();
    }
    public Beatmap getBeatmap(int id) {
        return ApiDatabase.getInstance().beatmapDao().getBy(id);
    }
    public String getFollowingUsername(int id){
        return ApiDatabase.getInstance().followingDao().getBy(id).username;
    }

    @Override
    protected List<FeedItem> doInBackground(Void... voids) {
        List<FeedItem> feedItems = new ArrayList<>();
        for (BestScore bestScore : getScores()) {
            Beatmap beatmap = getBeatmap(bestScore.getBeatmapId());
            String username = getFollowingUsername(bestScore.getUserId());
            if(beatmap == null)
                continue;
            FeedItem feeditem = FeedItemFactory.getFeeditem(username, bestScore, beatmap);
            feedItems.add(feeditem);
        }
        Collections.sort(feedItems);
        return feedItems;
    }

    @Override
    protected void onPostExecute(List<FeedItem> feedItems) {
        workDoneListener.workDone(feedItems);
    }
}
