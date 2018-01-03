package example.cerki.osuhub.Data.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by cerki on 03-Dec-17.
 */

@Entity
public class Following {
    @PrimaryKey
    public int id;
    public Long realTimestamp;
    public String username;

    @Ignore
    public Following(int id, Long realTimestamp, String username) {
        this.id = id;
        this.realTimestamp = realTimestamp;
        this.username = username;
    }

    public Following(int id,String username){
        this.id = id;
        this.username = username;
        this.realTimestamp = new Date().getTime();
    }
    public void updateTimestamp(){
        realTimestamp = new Date().getTime();
    }
}
