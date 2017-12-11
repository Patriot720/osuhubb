package example.cerki.osuhub.API.POJO;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

/**
 * Created by cerki on 03-Dec-17.
 */

@Entity // TODO move ApiDatabase as core Database and rename;
public class Following {
    @PrimaryKey
    public int id;
    @Deprecated
    public String timestamp;
    public Long realTimestamp;
    public String username;

    @Ignore
    @Deprecated
    public Following(int id, String timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    @Ignore
    @Deprecated
    public Following(int id, String timestamp, String username) {
        this.id = id;
        this.timestamp = timestamp;
        this.username = username;
    }

    public Following(int id, Long realTimestamp, String username) {
        this.id = id;
        this.realTimestamp = realTimestamp;
        this.username = username;
    }
}
