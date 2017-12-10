package example.cerki.osuhub.Notifications;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.Nonnull;

import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.Following;
import example.cerki.osuhub.OsuAPI;
import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.PastScores;
import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class FollowersNotificatorTest {


    @Test
    public void ShouldCheckEachFollowerIfTheyHaveNewerScoreAndReturnsTheseScoresCollection() throws Exception {
        Following follower = new Following(124493, "2013-06-22 9:11:16");
        Collection<Score> scores = new PastScores(follower,getOsuAPI()).getNewScores();
        assertTrue(scores.size() > 0);
    }
    @Test
    public void ShouldNotReturnAnything() throws Exception{
        Following follower = new Following(124493,"3017-12-03 16:02:22");
        Collection<Score> newScores = new PastScores(follower,getOsuAPI()).getNewScores();
        assertTrue(newScores.size() == 0);
    }

    @Test
    public void testDateComparison() throws Exception {
        Date date = Util.parseTimestamp("2017-12-03 20:49:10", TimeZone.getTimeZone("GMT+8"));
        Date date1 = Util.parseTimestamp("2017-12-03 17:02:22", TimeZone.getDefault());
        assertTrue(date.compareTo(date1) < 0);
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
        OsuAPI osuAPI = getOsuAPI();
        Collection<Score> scores = new PastScores(new Following(228,"","kaka"),osuAPI).getMonthOldScores();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        Date monthOldDate = calendar.getTime();
        assertTrue(scores.size() > 0);
        for (Score score : scores) {
            Date date = Util.parseTimestamp(score.get("date"),TimeZone.getTimeZone("GMT+8"));
            assertTrue(date.compareTo(monthOldDate) > 0);
        }
        }

    @Nonnull
    private OsuAPI getOsuAPI() throws IOException, JSONException {
        OsuAPI osuAPI = Mockito.mock(OsuAPI.class);
        when(osuAPI.getPlayerBest(anyInt())).then(invocation -> {
            ArrayList<Score> scores = new ArrayList<>();
            Score score = new Score();
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DAY_OF_MONTH,-2);
            Date time1 = instance.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat(Util.PATTERN);
            String format = dateFormat.format(time1);
            score.put("date",format);
            scores.add(score);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,-2);
            Date time = calendar.getTime();
            String moreThanAMonthDate = dateFormat.format(time);
            Score score1 = new Score();
            score1.put("date",moreThanAMonthDate);
            scores.add(score1);
            return scores;
        });
        return osuAPI;
    }

}