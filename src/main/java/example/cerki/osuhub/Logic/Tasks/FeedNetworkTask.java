package example.cerki.osuhub.Logic.Tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import example.cerki.osuhub.Data.Api.OsuAPI;
import example.cerki.osuhub.Data.Api.OsuApiService;
import example.cerki.osuhub.Data.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.Data.ApiDatabase.Dao.BeatmapDao;
import example.cerki.osuhub.Data.ApiDatabase.Dao.BestScoreDao;
import example.cerki.osuhub.Data.ApiDatabase.Dao.FollowingDao;
import example.cerki.osuhub.Data.Factories.FeedItemFactory;
import example.cerki.osuhub.Data.POJO.Beatmap;
import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.Data.POJO.FeedItem;
import example.cerki.osuhub.Data.POJO.Following;


public class FeedNetworkTask {

    public static List<FeedItem> getFeedItems() {
        ApiDatabase instance = ApiDatabase.getInstance();
        return getFeedItems(instance.followingDao(), instance.bestScoreDao(), OsuAPI.getApi(), instance.beatmapDao());
    }

    public static List<FeedItem> getFeedItems(FollowingDao followingDao, BestScoreDao scoreDb, OsuApiService api, BeatmapDao beatmapDao) {
        List<Following> all = followingDao.getAll();
        List<FeedItem> feedItems = new ArrayList<>();
        for (Following following : all) {
            int pp = Math.round(api.getUserBy(following.id).blockingGet().get(0).getPpRaw());
            if (following.pp == pp)
                continue;
            following.pp = pp;
            followingDao.insert(following);
            List<BestScore> scores = api.getBestScoresBy(following.id).blockingGet();
            for (BestScore score : scores) {
                if (score.isWeekOld()) { // Todo speedup with firebase
                    if (scoreDb.getBy(score.getUserId(), score.getDate()) == null)
                        scoreDb.insert(score);
                    Beatmap beatmap = beatmapDao.getBy(score.getBeatmapId());
                    if (beatmap == null) {
                        beatmap = api.getBeatmapBy(score.getBeatmapId()).blockingGet().get(0);
                        beatmapDao.insert(beatmap);
                    }
                    FeedItem feeditem = FeedItemFactory.getFeeditem(following.username, score, beatmap);
                    feedItems.add(feeditem);
                }
            }
        }
        Collections.sort(feedItems);
        return feedItems;

    }
}
