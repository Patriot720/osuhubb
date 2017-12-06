package example.cerki.osuhub;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by cerki on 06-Dec-17.
 */
public class ScoreTest {
    @Test
    public void getAsInt() throws Exception {
        Score score = new Score();
        score.put("key","522.344");
        int key = score.getAsInt("key");
        assertEquals(522,key);
    }
}