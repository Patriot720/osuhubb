package example.cerki.osuhub.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cerki on 30-Nov-17.
 */
public class PlayerTest {

    private Player player;

    @Before
    public void setUp() throws Exception {

        player = new Player(21);
    }

    @Test
    public void shouldAlwaysHaveAnId() throws Exception {
        assertEquals(21,player.getId());
    }


    @Test
    public void getStringShouldReturnIntValuesWithoutFloatingPoint() throws Exception {
        player.set("performance","23");
        String performance = player.getString("performance");
        assertEquals("23",performance);
    }

    @Test
    public void getStringShouldReturnFloatValuesWithFLoatingPointsUpTo2Digits() throws Exception {
        player.set("acc","23.32");
        String acc = player.getString("acc");
        assertEquals("23.32",acc);
    }
    @Test
    public void setShouldParseStringValuesAccordingly(){
        player.set("m","wtf23.32%");
        player.set("l","wtf44pp");
        String m = player.getString("m");
        String l = player.getString("l");
        assertEquals("23.32",m);
        assertEquals("44",l);
    }
}