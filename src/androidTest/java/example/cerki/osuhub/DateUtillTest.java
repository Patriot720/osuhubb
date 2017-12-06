package example.cerki.osuhub;

import android.text.format.DateUtils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by cerki on 06-Dec-17.
 */

public class DateUtillTest {
    @Test
    public void testDateUtil() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date time = calendar.getTime();
        CharSequence relativeTimeSpanString = DateUtils.getRelativeTimeSpanString(time.getTime());
        assertEquals(relativeTimeSpanString,"Yesterday");
    }

}
