package example.cerki.osuhub.Notifications;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.Following;
import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class FollowersNotificatorTest {


    @Test
    public void ShouldCheckEachFollowerIfTheyHaveNewerScoreAndReturnsTheseScoresCollection() throws Exception {
        Following follower = new Following(124493, "2013-06-22 9:11:16");
        Collection<Score> scores = follower.getNewScores();
        assertTrue(scores.size() > 0);
         Score score = (Score) scores.toArray()[0];
         assertEquals(score.get("rank"),"SH");
         assertEquals(Float.parseFloat(score.get("pp")),817,3); // TODO these test will fail if cookiezi has a better score
    }
    @Test
    public void ShouldNotReturnAnything() throws Exception{
        Following follower = new Following(124493,"2017-12-03 16:02:22");
        Collection<Score> newScores = follower.getNewScores();
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
        Collection<Score> scores = follower.getNewScores();
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
        FollowersTable table = new FollowersTable(new OsuDb(RuntimeEnvironment.application).getWritableDatabase());
        table.insertOrUpdate(1);
        String timestamp = table.getTimestamp(1);
        Date date1 = Util.parseTimestamp(timestamp, TimeZone.getTimeZone("GMT"));
        Date date2 = new Date();
        assertTrue(date1.compareTo(date2) <= 0);

    }
    @Test
    public void testDbDateZone() throws Exception{
        FollowersTable table = new FollowersTable(new OsuDb(RuntimeEnvironment.application).getWritableDatabase());
        table.insertOrUpdate(1);
        String timestamp = table.getTimestamp(1);
        Date date = Util.parseTimestamp(timestamp, TimeZone.getDefault());
        Date date2 = new Date();
        assertTrue(date.compareTo(date2) != 0);
    }

    @Test
    public void getScoresMonthOld() throws Exception {
        Collection<Score> scores = new Following(5187234,"","alko-chan").getMonthOldScores();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        Date monthOldDate = calendar.getTime();
        assertTrue(scores.size() > 0); // TODO not a consistent test
        for (Score score : scores) {
            Date date = Util.parseTimestamp(score.get("date"),TimeZone.getTimeZone("GMT+8"));
            assertTrue(date.compareTo(monthOldDate) > 0);
        }
        }
    @Test
    public void getScoresDayOld() throws Exception{
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_MONTH,-1);
        Date dayOldDate = date.getTime(); // TODO not a consistent test
        Collection<Score> scoresAfter = new Following(5652163, "", "alko-chan").getScoresAfter(dayOldDate);
        assertTrue(scoresAfter.size() == 0);
    }

}