package example.cerki.osuhub;

import org.junit.Test;

import static org.junit.Assert.*;

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
        String s = Util.calculateAccuracy(score);
        assertTrue(s != null);
        assertEquals("99.61%",s);
    }
}