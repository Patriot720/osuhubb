package example.cerki.osuhub.Feed;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.ApiDatabase.BestScoreDao;
import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.*;
import example.cerki.osuhub.API.POJO.Beatmap;

/**
 * Created by cerki on 11-Dec-17.
 */

public  class NewFeedTaskNetwork extends AsyncTask<Void,Void,List<FeedItem>>{
    public interface WorkDoneListener{
        public void workDone(List<FeedItem> items);
    }
    private WorkDoneListener workDoneListener;

    public NewFeedTaskNetwork(WorkDoneListener workDoneListener) {
        this.workDoneListener = workDoneListener;
    }

    @Override
    protected List<FeedItem> doInBackground(Void... voids) {
        List<Following> all = ApiDatabase.getInstance().followingDao().getAll();
        List<FeedItem> feedItems = new ArrayList<>();
        BestScoreDao scoreDb = ApiDatabase.getInstance().bestScoreDao();
        for (Following following : all) {
            List<BestScore> scores = getScoresFor(following.id);
            List<BestScore> dbScores = scoreDb.getBy(following.id);
            if(dbScores.equals(scores))
                continue;
            for (BestScore score : scores) {
                if(score.isWeekOld()) { // Todo speedup with firebase
                    if(scoreDb.getBy(score.getUserId(),score.getDate()) == null) {
                        scoreDb.insert(score);
                        example.cerki.osuhub.API.POJO.Beatmap beatmap = getBeatmap(score.getBeatmapId());
                        FeedItem feeditem = FeedItemFactory.getFeeditem(following.username, score, beatmap);
                        feedItems.add(feeditem);
                    }
                }
            }
        }
        Collections.sort(feedItems);
        return feedItems;
    }

    private List<BestScore> getScoresFor(int userId) {
        return OsuAPI.getApi().getBestScoresBy(userId).blockingGet(); // todo okhttp3.internal.http2.StreamResetException: stream was reset: INTERNAL_ERROR
    }
    private Beatmap getBeatmap(int id){
        Beatmap dbBeatmap = ApiDatabase.getInstance().beatmapDao().getBy(id);
        if(dbBeatmap == null) { // TODO bad stuff
            Beatmap beatmap = OsuAPI.getApi().getBeatmapBy(id).blockingGet().get(0);
            ApiDatabase.getInstance().beatmapDao().insert(beatmap);
            return beatmap;
        }
        return dbBeatmap;
    }


    @Override
    protected void onPostExecute(List<FeedItem> feedItems) {
        workDoneListener.workDone(feedItems);
    }

}
