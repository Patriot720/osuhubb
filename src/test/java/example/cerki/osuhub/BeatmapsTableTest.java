package example.cerki.osuhub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import example.cerki.osuhub.Feed.Beatmap;

import static example.cerki.osuhub.TestHelper.assertFakeBeatmap;
import static example.cerki.osuhub.TestHelper.getFakeBeatmap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class BeatmapsTableTest {

    private BeatmapsTable mTable;

    @Before
    public void setUp() throws Exception {
        Context context = RuntimeEnvironment.application;
        context.deleteDatabase(OsuDb.DATABASE_NAME);
        SQLiteDatabase mDb = new OsuDb(context).getWritableDatabase();
        mTable = new BeatmapsTable(mDb);
    }

    @Test
    public void insertion() throws Exception {
        mTable.insertOrUpdate(getFakeBeatmap());
        Beatmap beatmap = mTable.get(getFakeBeatmap().get(Beatmap.MAP_ID));
        assertFakeBeatmap(beatmap);
    }

    @Test
    public void getNonExistentBeatmap() throws Exception {
        Beatmap beatmap = mTable.get("23");
        assertTrue(beatmap.isEmpty());
    }

    @Test
    public void insertionEmptyBeatmap() throws Exception {
        Beatmap beatmap = new Beatmap();
        beatmap.put(Beatmap.MAP_ID,"1");
        mTable.insertOrUpdate(beatmap);
        Beatmap beatmap1 = mTable.get(beatmap.get(Beatmap.MAP_ID));
        assertEquals(beatmap1.size(), 1);
    }
}