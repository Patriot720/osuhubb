package example.cerki.osuhub.Data.ApiDatabase;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by cerki on 10-Dec-17.
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

