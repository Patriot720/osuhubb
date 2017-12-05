package example.cerki.osuhub.Feed;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by cerki on 05-Dec-17.
 */
public class BeatmapTaskTest {
    @Test
    public void getBeatmap() throws Exception {
        String id = "1262832";// TODO not a consistent test;
        BeatmapTask beatmapTask = new BeatmapTask();
        Beatmap beatmap = beatmapTask.getBeatmap(id);
        assertNotNull(beatmap);
        assertEquals(beatmap.get("total_length"),"87");
        assertEquals(beatmap.get("artist"),"ClariS");
    }

    @Test
    public void getBeatmapWrongId() throws Exception {
        String id = "0";
        BeatmapTask beatmapTask = new BeatmapTask();
        Beatmap beatmap = beatmapTask.getBeatmap(id);
        assertNotNull(beatmap);
    }
}