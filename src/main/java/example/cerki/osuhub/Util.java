package example.cerki.osuhub;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by cerki on 03-Dec-17.
 */

public class Util {
    public static Date parseTimestamp(String stamp,TimeZone timezone) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        format.setTimeZone(timezone);
        return format.parse(stamp);
    }

    public static Date parseTimestamp(String stamp) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.parse(stamp);
    }
}
