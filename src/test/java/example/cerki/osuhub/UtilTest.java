package example.cerki.osuhub;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cerki on 04-Dec-17.
 */
public class UtilTest {
    @Test
    public void calculateAcc() throws Exception {
        Score score = new Score();
        score.put("maxcombo","722");
        score.put("count50","1");
        score.put("count100","2");
        score.put("count300","549");
        score.put("countmiss","0");
        String s = Util.getAccuracyString(score);
        assertTrue(s != null);
        assertEquals("99.61%",s);
    }
    @Test
    public void collectionRemovalTest() throws Exception{
        Collection<Score> scores = new ArrayList<>();
        Score score1 = new Score();
        scores.add(score1);
        Score score = new Score();
        score.put("wat","nice");
        scores.add(score);
        scores.remove(score);
        for (Score score2 : scores) {
            assertEquals(score.get("wat"),"nice");
        }

    }
}