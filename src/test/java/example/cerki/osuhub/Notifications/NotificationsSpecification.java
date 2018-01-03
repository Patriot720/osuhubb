package example.cerki.osuhub.Notifications;

import android.arch.persistence.room.Room;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Date;
import java.util.List;

import example.cerki.osuhub.Data.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.Data.Api.OsuAPI;
import example.cerki.osuhub.Data.Api.OsuApiService;
import example.cerki.osuhub.Data.POJO.Beatmap;
import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.Data.POJO.Following;
import example.cerki.osuhub.Logic.NotificationsService;
import example.cerki.osuhub.Util.Util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

/**
 * Created by cerki on 16.12.2017.
 */

@RunWith(RobolectricTestRunner.class)
public class NotificationsSpecification {

    private ApiDatabase db;
    private OsuApiService api;

    @Before
    public void setUp() throws Exception {
        db = Room.databaseBuilder(RuntimeEnvironment.application, ApiDatabase.class, ApiDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build();
        api = OsuAPI.getApi();
    }

    @Test
    public void shouldSendNotificationWhenTheresANewerScoreThanTimestamp() throws Exception {
        BestScore bestScore = new BestScore();
        bestScore.setBeatmapId(1);
        bestScore.setDate(new Date());
        db.bestScoreDao().insert(bestScore);
        db.followingDao().insert(new Following(557197,Util.getMonthOldDate().getTime(),"user"));
        NotificationsService notificationsService = new NotificationsService(){
            @Override
            public void pushNotification(String username, BestScore score, Beatmap beatmap) {
                assertEquals(score.getBeatmapId(),1);
                assertEquals(username,"user");
            }
        };
        notificationsService.notifyAll(db);
    }

    @Test
    public void isNewerThanTest() throws Exception {
        BestScore bestScore = new BestScore();
        bestScore.setDate(new Date());
        boolean newerThan = bestScore.isNewerThan(Util.getWeekOldDate().getTime());
        assertTrue(newerThan);
    }

    @Test
    public void databaseDates() throws Exception {
        db.followingDao().insert(new Following(2,Util.getMonthOldDate().getTime(),"username"));
        Following by = db.followingDao().getBy(2);
        List<BestScore> totoki = api.getBestScoresBy("totoki").blockingGet();
        BestScore bestScore = totoki.get(0);
        assertTrue(bestScore.isNewerThan(by.realTimestamp));
    }
}
