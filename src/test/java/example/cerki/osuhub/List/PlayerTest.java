package example.cerki.osuhub.List;

import org.junit.Before;
import org.junit.Test;

import static example.cerki.osuhub.Columns.ACC;
import static example.cerki.osuhub.Columns.PC;
import static example.cerki.osuhub.Columns.PP;
import static example.cerki.osuhub.TestHelper.getFakePlayer;
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
    public void testEqualsSamePlayers() throws Exception {
        Player p = getFakePlayer();
        Player p2 = getFakePlayer();
        assertTrue(p.equals(p2));
    }

    @Test
    public void testEqualsDifferentPlayers() throws Exception {
        Player p = getFakePlayer();
        Player p2 = getFakePlayer(2);
        assertTrue(!p.equals(p2));
    }
    @Test
    public void testEqualsDifferentDifferenceInPlayers() throws Exception{
        Player p = getFakePlayer();
        Player p2 = getFakePlayer();
        p.compare(getFakePlayer(2));
        assertTrue(!p.equals(p2));
    }

    @Test
    public void comparison() throws Exception {
        Player player1 = getFakePlayer();
        Player player2 = getFakePlayer(2);
        player1.compare(player2);
        assertEquals("-1000",player1.getDifferenceString(PP));
        assertEquals("-10.32",player1.getDifferenceString(ACC));
    }

    @Test
    public void comparsionEmptyPlayer() throws Exception {
        Player player1 = getFakePlayer();
        Player player2 = new Player();
        player1.compare(player2);
        assertTrue(player1.getDifferenceString(PP).equals(""));
        assertTrue(player1.getDifferenceString(ACC).equals(""));
        assertTrue(player1.getDifferenceString(PC).equals(""));
    }

    @Test
    public void shouldAlwaysHaveAnId() throws Exception {
        assertEquals(21,player.getId());
    }


    @Test
    public void getStringShouldReturnIntValuesWithoutFloatingPoint() throws Exception {
        player.setComparable("performance","23");
        String performance = player.getString("performance");
        assertEquals("23",performance);
    }

    @Test
    public void getStringShouldReturnFloatValuesWithFLoatingPointsUpTo2Digits() throws Exception {
        player.setComparable("acc","23.32");
        String acc = player.getString("acc");
        assertEquals("23.32",acc);
    }
    @Test
    public void setShouldParseStringValuesAccordingly(){
        player.setComparable("m","wtf23.32%");
        player.setComparable("l","wtf44pp");
        String m = player.getString("m");
        String l = player.getString("l");
        assertEquals("23.32",m);
        assertEquals("44",l);
    }
}