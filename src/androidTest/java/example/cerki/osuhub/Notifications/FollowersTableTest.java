package example.cerki.osuhub.Notifications;

import org.junit.Before;
import org.junit.Test;


import java.util.Collection;

import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.Following;import example.cerki.osuhub.OsuDb;

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
        mTable.insertOrUpdate(1);
        String timestamp = mTable.getTimestamp(1);
        assertTrue(timestamp.length() > 1);
    }

    @Test
    public void getDifferentFollower() throws Exception {
        mTable.insertOrUpdate(2);
        String timestamp = mTable.getTimestamp(4);
        assertEquals("",timestamp);
    }

    @Test
    public void updatingTimestamp() throws Exception {
       mTable.insertOrUpdate(1);
       String timestamp = mTable.getTimestamp(1);
       Thread.sleep(2000);
       mTable.insertOrUpdate(1);
       String newTimestamp = mTable.getTimestamp(1);
       assertNotEquals(timestamp,newTimestamp);
    }

    @Test
    public void deletion() throws Exception {
        mTable.insertOrUpdate(1);
        mTable.deleteFollower(1);
        assertEquals("",mTable.getTimestamp(1));
    }
    @Test
    public void getAll() throws Exception {
        mTable.insertOrUpdate(20);
        mTable.insertOrUpdate(22);
        mTable.insertOrUpdate(23);
        mTable.insertOrUpdate(24);
        mTable.insertOrUpdate(25);
        Collection<Following> followers =  mTable.getAll();
        assertEquals(followers.size(),5);
        for (Following f :
                followers) {
            assertFalse(f.timestamp.isEmpty());
            assertTrue(f.id  != 0);
        }
    }

    @Test
    public void insertFollowing() throws Exception {
        String dansGamu = "DansGamu";
        mTable.insertOrUpdate(1, dansGamu);
        Collection<Following> all = mTable.getAll();
        for (Following following : all) {
            assertEquals(following.username,dansGamu);
        }
    }

}