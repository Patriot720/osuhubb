import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import java.util.TooManyListenersException;

import example.cerki.osuhub.Columns;
import example.cerki.osuhub.Following;
import example.cerki.osuhub.FollowersNotificator;
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
        followersNotificator = new FollowersNotificator();
    }

    @Test
    public void getJsonObject() throws Exception{
        JSONArray jsonArray = FollowersNotificator.getJsonObject(124493);
        assertNotNull(jsonArray);
    }

    @Test
    public void getJsonFakeId() throws Exception {
        JSONArray jsonArray = followersNotificator.getJsonObject(0);
        assertTrue(jsonArray.length() == 0);
    }

    @Test
    public void ShouldCheckEachFollowerIfTheyHaveNewerScoreAndReturnsTheseScoresCollection() throws Exception {
        Following follower = new Following(124493, "2013-06-22 9:11:16");
        Collection<Score> scores = followersNotificator.getNewScores(follower);
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
    public void testDateComparsion() throws Exception {
        Date date = Util.parseTimestamp("2017-12-03 20:49:10", TimeZone.getTimeZone("GMT+8"));
        Date date1 = Util.parseTimestamp("2017-12-03 17:02:22", TimeZone.getDefault());
        assertTrue(date.compareTo(date1) < 0);

    }

    @Test
    public void ShouldReturnEmpty() throws Exception {
        Following follower = new Following(1, "2013-06-22 9:11:16");
        Collection<Score> scores = followersNotificator.getNewScores(follower);
        assertTrue(scores.size() == 0);
    }
}