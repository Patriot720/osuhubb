package example.cerki.osuhub.PlayerFragment.RecentPlays;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.Beatmap;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.API.POJO.FeedItem;
import example.cerki.osuhub.Feed.FeedItemFactory;

/**
 * Created by cerki on 23.12.2017.
 */

public class RecentScoresTask extends AsyncTask<Integer,Void,List<FeedItem>> {
    public interface OnWorkDoneListener{
        void update(List<FeedItem> feedItems);
    }
    private OnWorkDoneListener listener;
    List<BestScore> mItems;

    public RecentScoresTask(OnWorkDoneListener listener, List<BestScore> list) {
        this.listener = listener;
        this.mItems = list;
    }

    @Override
    protected List<FeedItem> doInBackground(Integer... counters) {
        List<FeedItem> list = new ArrayList<>();
        int counter = counters[0];
        int internalCounter = 0;
        while(counter < mItems.size()){
            if(internalCounter != 0 && internalCounter % 10 == 0)
                return list;
            BestScore recentScore = mItems.get(counter);
            Beatmap beatmap = OsuAPI.getBeatmapBy(recentScore.getBeatmapId());
            FeedItem feeditem = FeedItemFactory.getFeeditem("", recentScore, beatmap);
            list.add(feeditem);
            counter++;
            internalCounter++;
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<FeedItem> feedItems) {
        listener.update(feedItems);
    }
}
