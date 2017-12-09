package example.cerki.osuhub.List;

import android.database.sqlite.SQLiteDatabase;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.TestHelper;


/**
 * Created by cerki on 30-Nov-17.
 */
@RunWith(RobolectricTestRunner.class)
public class PlayersTableTest {

    private PlayersTable playersTable;

    @Before
    public void setUp() throws Exception {
        SQLiteDatabase writableDatabase = new OsuDb(RuntimeEnvironment.application).getWritableDatabase();
        playersTable = new PlayersTable(writableDatabase);
    }

    @Test
    public void insertion() throws Exception {
        Player p = TestHelper.getFakePlayer();
        playersTable.insertPlayer(p);
        Player player = playersTable.getPlayer(p.getId());
        TestHelper.assertFakePlayer(player);
    }
}