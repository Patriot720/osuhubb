package example.cerki.osuhub;

import org.junit.Test;

import java.util.Collection;

import example.cerki.osuhub.Feed.Beatmap;
import example.cerki.osuhub.List.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by cerki on 05-Dec-17.
 */


// TODO not consistent tests
public class OsuAPITest {
    @Test
    public void getBeatmap() throws Exception {
        String id = "1262832";
        OsuAPI osuAPI = new OsuAPI();
        Beatmap beatmap = osuAPI.getBeatmap(id);
        assertNotNull(beatmap);
        assertEquals(beatmap.get("total_length"),"87");
        assertEquals(beatmap.get("artist"),"ClariS");
    }

    @Test
    public void getBeatmapWrongId() throws Exception {
        String id = "0";
        OsuAPI osuAPI = new OsuAPI();
        Beatmap beatmap = osuAPI.getBeatmap(id);
        assertNotNull(beatmap);
    }
    @Test
    public void getPlayerBest() throws Exception {
        String id = "5187234";
        OsuAPI osuAPI = new OsuAPI();
        Collection<Score> scores = osuAPI.getPlayerBest(id);
        assertTrue(scores.size() == 100);
    }

    @Test
    public void getPlayer() throws Exception {
        String id ="5652163";
        OsuAPI osuAPI = new OsuAPI();
        Player p = osuAPI.getPlayer(id);
        assertEquals("Patriot720",p.getUsername());
    }


}