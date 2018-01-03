package example.cerki.osuhub.Feed;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Calendar;
import java.util.Date;

import example.cerki.osuhub.Data.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.Util.Util;

import static org.junit.Assert.*;

/**
 * Created by cerki on 11-Dec-17.
 */
@RunWith(RobolectricTestRunner.class)
public class NewFeedTaskDbTest {
    private ApiDatabase db;
    private BestScore goodScore;

    @Before
    public void setUp() throws Exception {
        db = ApiDatabase.createInstance(RuntimeEnvironment.application);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH,-2);
        Date time = instance.getTime();
        goodScore = new BestScore();
        goodScore.setDate(time);
        goodScore.setBeatmapId(1);
        db.bestScoreDao().insert(goodScore);
        BestScore badScore = new BestScore();
        instance.add(Calendar.DAY_OF_MONTH,-10);
        goodScore.setDate(instance.getTime());
        db.bestScoreDao().insert(badScore);
    }

    @Test
    public void testDateCalculationDb() throws Exception {
        int size = db.bestScoreDao().getScoresAfter(Util.getWeekOldDate()).size();
        assertEquals(size,1);
    }

}