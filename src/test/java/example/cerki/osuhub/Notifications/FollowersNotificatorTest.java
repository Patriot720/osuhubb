package example.cerki.osuhub.Notifications;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;
import java.util.TimeZone;

import example.cerki.osuhub.Util;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class FollowersNotificatorTest {
    @Test
    public void testDateComparison() throws Exception {
        Date date = Util.parseTimestamp("2017-12-03 20:49:10", TimeZone.getTimeZone("GMT+8"));
        Date date1 = Util.parseTimestamp("2017-12-03 17:02:22", TimeZone.getDefault());
        assertTrue(date.compareTo(date1) < 0);
    }
}
