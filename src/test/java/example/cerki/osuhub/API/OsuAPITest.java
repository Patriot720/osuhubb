package example.cerki.osuhub.API;

import android.arch.persistence.room.Room;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.POJO.Beatmap;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.API.POJO.User;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;

import static org.junit.Assert.*;

/**
 * Created by cerki on 10-Dec-17.
 */
@Ignore
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
    public void getUser() throws Exception {
        Single<List<User>> cookiezi = osuApiService.getUserBy("cookiezi");
        User body = cookiezi.blockingGet().get(0);
        assertEquals(body.getUsername(),"Cookiezi");
    }

    @Test
    public void getUserBest() throws Exception {
        Single<List<BestScore>> cookiezi = osuApiService.getBestScoresBy("cookiezi");
        int size = cookiezi.blockingGet().size();
        assertEquals(size,10);
    }

    @Test
    public void testDatabase() throws Exception {
        List<BestScore> cookiezi = osuApiService.getBestScoresBy("cookiezi").blockingGet();
        osuApiDb.bestScoreDao().insert(cookiezi);
        List<BestScore> by = osuApiDb.bestScoreDao().getBy(cookiezi.get(0).getUserId());
        assertEquals(by.size(),10);
    }
}