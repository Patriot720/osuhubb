package example.cerki.osuhub.Notifications;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by cerki on 03-Dec-17.
 */
public class FollowersNotificatorTest {

    private FollowersNotificator followersNotificator;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getJsonObject() throws Exception{
        JSONArray jsonArray = FollowersNotificator.getPlayerJsonArray(124493);
        assertNotNull(jsonArray);
    }

    @Test
    public void getJsonFakeId() throws Exception {
        JSONArray jsonArray = FollowersNotificator.getPlayerJsonArray(0);
        assertTrue(jsonArray.length() == 0);
    }

    @Test
    public void ShouldCheckEachFollowerIfTheyHaveNewerScoreAndReturnsTheseScoresCollection() throws Exception {
        Following follower = new Following(124493, "2013-06-22 9:11:16");
        Collection<Score> scores = FollowersNotificator.getNewScores(follower);
        assertTrue(scores.size() > 0);
         Score score = (Score) scores.toArray()[0];
         assertEquals(score.get("rank"),"SH");
         assertEquals(Float.parseFloat(score.get("pp")),817,3); // TODO these test will fail if cookiezi has a better score
    }
    @Test
    public void ShouldNotReturnAnything() throws Exception{
        Following follower = new Following(124493,"2017-12-03 16:02:22");
        Collection<Score> newScores = FollowersNotificator.getNewScores(follower);
        assertTrue(newScores.size() == 0);
    }

    @Test
    public void testDateComparison() throws Exception {
        Date date = Util.parseTimestamp("2017-12-03 20:49:10", TimeZone.getTimeZone("GMT+8"));
        Date date1 = Util.parseTimestamp("2017-12-03 17:02:22", TimeZone.getDefault());
        assertTrue(date.compareTo(date1) < 0);
    }
    @Test
    public void ShouldReturnEmpty() throws Exception {
        Following follower = new Following(1, "2013-06-22 9:11:16");
        Collection<Score> scores = FollowersNotificator.getNewScores(follower);
        assertTrue(scores.size() == 0);
    }

    @Test
    public void testGmtDate() throws Exception {
        Date date = Util.parseTimestamp("2017-12-03 20:49:10", TimeZone.getDefault());
        Date date1 = Util.parseTimestamp("2017-12-03 20:49:10", TimeZone.getTimeZone("GMT+0"));
        assertTrue(date.compareTo(date1) < 0);
    }
    @Test
    public void testDbDate() throws  Exception{
        FollowersTable table = new FollowersTable(new OsuDb(getTargetContext()).getWritableDatabase());
        table.insertOrUpdate(1);
        String timestamp = table.getTimestamp(1);
        Date date1 = Util.parseTimestamp(timestamp, TimeZone.getTimeZone("GMT"));
        Date date2 = new Date();
        assertTrue(date1.compareTo(date2) <= 0);

    }
    @Test
    public void testDbDateZone() throws Exception{
        FollowersTable table = new FollowersTable(new OsuDb(getTargetContext()).getWritableDatabase());
        table.insertOrUpdate(1);
        String timestamp = table.getTimestamp(1);
        Date date = Util.parseTimestamp(timestamp, TimeZone.getDefault());
        Date date2 = new Date();
        assertTrue(date.compareTo(date2) != 0);
    }


}