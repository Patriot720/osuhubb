package example.cerki.osuhub;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

/**
 * Created by cerki on 03-Dec-17.
 */

public class Following {
    public int id;
    public String timestamp;
    public String username;

    public Following(int id, String timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public Following(int id, String timestamp, String username) {
        this.id = id;
        this.timestamp = timestamp;
        this.username = username;
    }
        // TODO change all followers to following

}
