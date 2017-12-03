package example.cerki.osuhub;

import org.junit.Before;
import org.junit.Test;


import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

/**
 * Created by cerki on 02-Dec-17.
 */

public class FollowersTableTest {

    private FollowersTable mTable;

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(OsuDb.DATABASE_NAME);
        OsuDb osudb = new OsuDb(getTargetContext());
        mTable = new FollowersTable(osudb.getWritableDatabase());
    }

    @Test
    public void insertOrUpdateFollower() throws Exception {
        mTable.insertOrUpdateFollower(1);
        String timestamp = mTable.getTimestamp(1);
        assertTrue(timestamp.length() > 1);
    }

    @Test
    public void getDifferentFollower() throws Exception {
        mTable.insertOrUpdateFollower(2);
        String timestamp = mTable.getTimestamp(4);
        assertEquals("",timestamp);
    }

    @Test
    public void updatingTimestamp() throws Exception {
       mTable.insertOrUpdateFollower(1);
       String timestamp = mTable.getTimestamp(1);
       Thread.sleep(1000);
       mTable.insertOrUpdateFollower(1);
       String newTimestamp = mTable.getTimestamp(1);
       assertNotEquals(timestamp,newTimestamp);
    }

    @Test
    public void deletion() throws Exception {
        mTable.insertOrUpdateFollower(1);
        mTable.deleteFollower(1);
        assertEquals("",mTable.getTimestamp(1));
    }
    @Test
    public void testTimestamps() throws Exception{
        mTable.insertOrUpdateFollower(1);
        String timestamp = mTable.getTimestamp(1);
        Thread.sleep(1000);
        mTable.insertOrUpdateFollower(1);
        String timestamp1 = mTable.getTimestamp(1);
    }

    @Test
    public void getAll() throws Exception {
        mTable.insertOrUpdateFollower(20);
        mTable.insertOrUpdateFollower(22);
        mTable.insertOrUpdateFollower(23);
        mTable.insertOrUpdateFollower(24);
        mTable.insertOrUpdateFollower(25);
        Collection<Following> followers =  mTable.getAll();
        assertEquals(followers.size(),5);
        for (Following f :
                followers) {
            assertFalse(f.timestamp.isEmpty());
            assertTrue(f.id  != 0);
        }
    }
}