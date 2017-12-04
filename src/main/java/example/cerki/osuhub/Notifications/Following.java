package example.cerki.osuhub.Notifications;

/**
 * Created by cerki on 03-Dec-17.
 */

public class Following {
    int id;
    String timestamp;
    String username;

    public Following(int id, String timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public Following(int id, String timestamp, String username) {
        this.id = id;
        this.timestamp = timestamp;
        this.username = username;
    }
}
