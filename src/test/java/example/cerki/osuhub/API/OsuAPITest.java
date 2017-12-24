package example.cerki.osuhub.API;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.POJO.Beatmap;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.API.POJO.User;
import example.cerki.osuhub.Feed.FeedItem;
import example.cerki.osuhub.Feed.FeedItemFactory;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;

import static org.junit.Assert.*;

/**
 * Created by cerki on 10-Dec-17.
 */
@RunWith(RobolectricTestRunner.class)
public class OsuAPITest {

    private OsuApiService osuApiService;
    private ApiDatabase osuApiDb;

    @Before
    public void setUp() throws Exception {
        osuApiService = OsuAPI.getApi();
        osuApiDb = Room.databaseBuilder(RuntimeEnvironment.application,ApiDatabase.class,ApiDatabase.DATABASE_NAME)
            .allowMainThreadQueries().build();
    }

    @Test
    public void getBeatmap() throws Exception {
        Single<List<Beatmap>> beatmapBy = osuApiService.getBeatmapBy("1262832");
        List<Beatmap> body = beatmapBy.blockingGet();
        assertEquals(body.get(0).getArtist(),"ClariS");
    }

    @Test
    public void getRecentTestEmptyRecent() throws Exception {
        List<BestScore> rafis = osuApiService.getRecentScoresBy("rafis").blockingGet();
        assertEquals(rafis.size(),0);

    }

    @Test
    public void getRecentTest() throws Exception {
        List<BestScore> filsdelama = osuApiService.getRecentScoresBy("filsdelama").blockingGet();
        assertTrue(filsdelama.size() > 0);
        BestScore bestScore = filsdelama.get(0);
        Beatmap beatmap = osuApiService.getBeatmapBy(bestScore.getBeatmapId()).blockingGet().get(0);
        FeedItem feedItem = FeedItemFactory.getFeeditem("filsdelama", bestScore, beatmap);
        assertTrue(feedItem.missCount != null);
    }


    @Test
    public void getUser() throws Exception {
        Single<List<User>> cookiezi = osuApiService.getUserBy("cookiezi");
        User body = cookiezi.blockingGet().get(0);
        assertEquals(body.getUsername(),"Cookiezi");
    }

    @Test
    public void getUserBest() throws Exception {
        Single<List<BestScore>> cookiezi = osuApiService.getBestScoresBy("cookiezi");
        int size = cookiezi.blockingGet().size();
        assertEquals(size,50);
    }

    @Test // Todo not a consistent test
    public void testDateConversionFromApi() throws Exception{
        Single<List<BestScore>> cookiezi = osuApiService.getBestScoresBy("cookiezi");
        Date date = cookiezi.blockingGet().get(0).getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int i = cal.get(Calendar.DATE);
        assertEquals(i,30);
    }

    @Test
    public void testDatabaseInsertion() throws Exception {
        List<BestScore> cookiezi = osuApiService.getBestScoresBy("cookiezi").blockingGet();
        osuApiDb.bestScoreDao().insert(cookiezi);
        List<BestScore> by = osuApiDb.bestScoreDao().getBy(cookiezi.get(0).getUserId());
        assertEquals(by.size(),50);
    }
    @Test // Todo not a consistent test
    public void testDatabaseDateConversion() throws Exception {
        List<BestScore> cookiezi = osuApiService.getBestScoresBy("cookiezi").blockingGet();
        osuApiDb.bestScoreDao().insert(cookiezi);
        List<BestScore> by = osuApiDb.bestScoreDao().getBy(cookiezi.get(0).getUserId());
        Date date = by.get(0).getDate();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = instance.get(Calendar.DATE);
        assertEquals(30,i);
    }
    @Test // Todo not a consistent test
    public void testDatabaseGetByUserId() throws Exception{
        List<BestScore> cookiezi = osuApiService.getBestScoresBy("cookiezi").blockingGet();
        osuApiDb.bestScoreDao().insert(cookiezi);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-2);
        Date time = calendar.getTime();
        int size = osuApiDb.bestScoreDao().getScoresAfter(time).size();
        assertEquals(1,size);
    }

    @Test
    public void testIntegersHandling() throws Exception {
        List<BestScore> cookiezi = osuApiService.getBestScoresBy("cookiezi").blockingGet();
        int count50 = cookiezi.get(0).getCount50();
        assertEquals(count50,0);
    }

    @Test
    public void testBeatmapFetchingAndDatabaseHandling() throws Exception {
        Single<List<Beatmap>> beatmapBy = osuApiService.getBeatmapBy("1262832");
        List<Beatmap> beatmaps = beatmapBy.blockingGet();
        Beatmap beatmap = beatmaps.get(0);
        osuApiDb.beatmapDao().insert(beatmap);
        Beatmap by = osuApiDb.beatmapDao().getBy(1262832);
        assertEquals(by.getArtist(),"ClariS");

    }

    @Test
    public void bestScoreHashCode() throws Exception {
        Single<List<BestScore>> cookiezi = osuApiService.getBestScoresBy("cookiezi");
        BestScore bestScore = cookiezi.blockingGet().get(0);
        osuApiDb.bestScoreDao().insert(bestScore);
        osuApiDb.bestScoreDao().insert(bestScore);
        List<BestScore> by = osuApiDb.bestScoreDao().getBy(bestScore.getUserId());
        assertEquals(by.size(),1);
    }
}